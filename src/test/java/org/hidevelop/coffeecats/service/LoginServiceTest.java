package org.hidevelop.coffeecats.service;

import org.hidevelop.coffeecats.config.PasswordEncoder;
import org.hidevelop.coffeecats.exception.CustomException;
import org.hidevelop.coffeecats.exception.error.impl.AuthenticationError;
import org.hidevelop.coffeecats.exception.error.impl.MemberCustomError;
import org.hidevelop.coffeecats.model.dto.LoginReqDto;
import org.hidevelop.coffeecats.model.entity.MemberEntity;
import org.hidevelop.coffeecats.model.entity.Password;
import org.hidevelop.coffeecats.repository.MemberRepository;
import org.hidevelop.coffeecats.util.JwtUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Objects;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoginServiceTest {

    @InjectMocks
    private LoginService loginService;

    @Mock
    private MemberRepository memberRepository;
    @Mock
    private JwtUtil jwtUtil;
    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("로그인 성공")
    void login(){

        //given
        LoginReqDto loginReqDto = new LoginReqDto(
                "test@email.com",
                "password");

        Password password = passwordEncoder.encrypt("test@email.com", "password");

        MemberEntity testMember = MemberEntity.builder()
                .memberId(1L)
                .email("test@email.com")
                .password(password)
                .nickname("test")
                .build();

        //stub-1 이메일 검색 시 testMember 반환
        when(memberRepository.findByEmail("test@email.com"))
                .thenReturn(Optional.ofNullable(testMember));

        //stub-2 passwordEncoder 비밀번호 검증 생성
        when(passwordEncoder.check(Objects.requireNonNull(testMember), "password"))
                .thenReturn(true);

        //stub-3 JwtUtil 가짜 토큰 생성
        when(jwtUtil.generateToken(testMember.getMemberId(), testMember.getEmail()))
        .thenReturn("mockToken");

        //when
        String token = loginService.login(loginReqDto);

        //Then
        assertNotNull(token);
        assertEquals("mockToken", token);

        //Verify
        verify(memberRepository, times(1)).findByEmail("test@email.com");
        verify(passwordEncoder, times(1)).check(testMember, "password");
        verify(jwtUtil, times(1)).generateToken(testMember.getMemberId(), testMember.getEmail());

    }

    @Test
    @DisplayName("비밀번호 불일치")
    void loginFail(){
        //given
        LoginReqDto loginReqDto = new LoginReqDto(
                "test@email.com",
                "wrongPassword");

        Password password = passwordEncoder.encrypt("test@email.com", "password");

        MemberEntity testMember = MemberEntity.builder()
                .memberId(1L)
                .email("test@email.com")
                .password(password)
                .nickname("test")
                .build();

        //stub-1 이메일 검색 시 testMember 반환
        when(memberRepository.findByEmail("test@email.com"))
                .thenReturn(Optional.ofNullable(testMember));

        //when, then
        assertThatThrownBy( () -> loginService.login(loginReqDto))
                .isInstanceOf(CustomException.class)
                .hasMessageContaining(AuthenticationError.PASSWORD_NOT_MATCH.getMessage());

        verify(memberRepository, times(1)).findByEmail("test@email.com");
        verify(passwordEncoder, times(1)).check(Objects.requireNonNull(testMember), loginReqDto.password());

        verify(jwtUtil, never()).generateToken(anyLong(), anyString());
    }

    @Test
    @DisplayName("없는 회원일 경우")
    void noMember(){
        //giver
        //given
        LoginReqDto loginReqDto = new LoginReqDto(
                "test@email.com",
                "wrongPassword");


        //stub - 1
        when(memberRepository.findByEmail("test@email.com")).thenReturn(Optional.empty());

        //when & then
        assertThatThrownBy( () -> loginService.login(loginReqDto))
                .isInstanceOf(CustomException.class)
                .hasMessage(MemberCustomError.MEMBER_NOT_FOUND.getMessage());

        verify(memberRepository, times(1)).findByEmail("test@email.com");

        verify(passwordEncoder, never()).check(any(),anyString());
        verify(jwtUtil, never()).generateToken(anyLong(), anyString());
    }
}