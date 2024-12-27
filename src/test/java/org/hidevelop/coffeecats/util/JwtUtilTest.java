package org.hidevelop.coffeecats.util;

import org.hidevelop.coffeecats.exception.CustomException;
import org.hidevelop.coffeecats.exception.error.impl.AuthenticationError;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;


class JwtUtilTest {

    private JwtUtil jwtUtil;

    private final String SECRET_KEY = "JAd+JdstcCOubnFGZi8KuJf4chPSB8diBOf2F7gUkAY=";
    private final long EXPIRATION_TIME = 1000L * 60 * 60;

    @BeforeEach
    void setUp(){
        jwtUtil = new JwtUtil(SECRET_KEY, EXPIRATION_TIME);
    }


    @Test
    @DisplayName("토큰 생성 및 데이터 추출 성공")
    void generateAndExtractToken(){

        //given
        long memberId = 1L;
        String email = "test@test.com";

        //when
        String token = jwtUtil.generateToken(memberId, email);

        //then
        assertThat(token).isNotNull();
        assertThat(jwtUtil.extractMemberEmail(token)).isEqualTo(email);
        assertThat(jwtUtil.extractMemberId(token)).isEqualTo(memberId);
    }

    @Test
    @DisplayName("유효하지 않은 토큰 검증")
    void invalidToken() {
        // given
        String invalidToken = "invalidToken";

        // when & then
        assertThatThrownBy(() -> jwtUtil.extractMemberEmail(invalidToken))
                .isInstanceOf(CustomException.class)
                .hasMessage(AuthenticationError.NOT_VALID_TOKEN.getMessage());

    }
}