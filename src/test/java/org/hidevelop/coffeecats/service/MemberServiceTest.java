package org.hidevelop.coffeecats.service;

import org.hidevelop.coffeecats.config.PasswordEncoder;
import org.hidevelop.coffeecats.exception.CustomException;
import org.hidevelop.coffeecats.exception.error.impl.MemberCustomError;
import org.hidevelop.coffeecats.model.dto.*;
import org.hidevelop.coffeecats.model.entity.MemberEntity;
import org.hidevelop.coffeecats.repository.MemberRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;

import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class) // Mock 클래스를 사용함을 알림
class MemberServiceTest {

    @InjectMocks // Mock 환경에 주입
    private MemberService memberService;


    @Mock // 가짜 객체를 만들어 @InjectMocks 대상에 주입
    private MemberRepository memberRepository;

    @Mock // 진짜 객체를 만들어 @InjectMocks 대상에 주입
    private PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("회원 가입 성공")
    void signUpSuccess(){

        //given
        SignUpReqDto signUpReqDto = new SignUpReqDto(
                "test@email.com",
                "testNickname",
                "testPassword");

        //stub-1
        when(memberRepository.existsByEmail(any())).thenReturn(false);

        //stub-2
        MemberEntity testMember = MemberEntity.builder()
                .memberId(1L)
                .email("test@email.com")
                .nickname("testNickname")
                .password(passwordEncoder.encrypt("test@email.com", "testPassword"))
                .build();

        when(memberRepository.save(any())).thenReturn(testMember);

        //when
        SignUpResDto signUpResDto = memberService.signUp(signUpReqDto);

        assertThat(signUpResDto.email()).isEqualTo("test@email.com");

    }

    @Test
    @DisplayName("회원 가입 실패")
    void signUpFail(){
        //given
        SignUpReqDto signUpReqDto = new SignUpReqDto(
                "test@email.com",
                "testNickname",
                "testPassword");

        //stub-1
        when(memberRepository.existsByEmail(any())).thenReturn(true);

        //when, then
        assertThatThrownBy( () -> memberService.signUp(signUpReqDto))
                .isInstanceOf(CustomException.class)
                .hasMessage(MemberCustomError.MEMBER_ALREADY_EXISTS.getMessage());

        // existsByEmail 1회 호출 했는지 확인
        verify(memberRepository, times(1)).existsByEmail(signUpReqDto.email());

        // passwordEncoder 호출되지 않아야 함
        verify(passwordEncoder, never()).encrypt(anyString(), anyString());
    }
}