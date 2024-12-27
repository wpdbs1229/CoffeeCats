package org.hidevelop.coffeecats.service;

import lombok.RequiredArgsConstructor;
import org.hidevelop.coffeecats.config.PasswordEncoder;
import org.hidevelop.coffeecats.exception.CustomException;
import org.hidevelop.coffeecats.model.dto.LoginReqDto;
import org.hidevelop.coffeecats.model.entity.MemberEntity;
import org.hidevelop.coffeecats.repository.MemberRepository;
import org.hidevelop.coffeecats.util.JwtUtil;
import org.springframework.stereotype.Service;

import static org.hidevelop.coffeecats.exception.error.impl.AuthenticationError.PASSWORD_NOT_MATCH;
import static org.hidevelop.coffeecats.exception.error.impl.MemberCustomError.*;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public String login(LoginReqDto loginReqDto) {

        MemberEntity member = memberRepository.findByEmail(loginReqDto.email())
                .orElseThrow(()-> new CustomException(MEMBER_NOT_FOUND));

        if(!passwordEncoder.check(member, loginReqDto.password())){
            throw new CustomException(PASSWORD_NOT_MATCH);
        }

        return jwtUtil.generateToken(member.getMemberId(), member.getEmail());
    }

}
