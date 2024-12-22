package org.hidevelop.coffeecats.service;

import lombok.RequiredArgsConstructor;
import org.hidevelop.coffeecats.config.PasswordEncoder;
import org.hidevelop.coffeecats.exception.CustomException;
import org.hidevelop.coffeecats.exception.error.impl.MemberCustomError;
import org.hidevelop.coffeecats.model.dto.SignUpReqDto;
import org.hidevelop.coffeecats.model.dto.SignUpResDto;
import org.hidevelop.coffeecats.model.entity.MemberEntity;
import org.hidevelop.coffeecats.model.entity.Password;
import org.hidevelop.coffeecats.repository.MemberRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private static final Logger log = LoggerFactory.getLogger(MemberService.class);

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;


    public SignUpResDto signUp(SignUpReqDto signUpReqDto) {

        boolean exists = memberRepository.existsByEmail(signUpReqDto.email());

        if (exists) {
            throw new CustomException(MemberCustomError.MEMBER_ALREADY_EXISTS);
        }

        Password password = passwordEncoder.encrypt(signUpReqDto.email(), signUpReqDto.password());
        MemberEntity savedMember = memberRepository.save(MemberEntity.of(signUpReqDto, password));

        return new SignUpResDto(savedMember.getEmail());
    }
}
