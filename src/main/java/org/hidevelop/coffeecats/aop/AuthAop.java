package org.hidevelop.coffeecats.aop;

import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.hidevelop.coffeecats.exception.CustomException;
import org.hidevelop.coffeecats.util.JwtUtil;
import org.springframework.stereotype.Component;

import java.util.Date;

import static org.hidevelop.coffeecats.exception.error.impl.AuthenticationError.EXPIRED_TOKEN;
import static org.hidevelop.coffeecats.exception.error.impl.AuthenticationError.NOT_VALID_TOKEN;

@Aspect
@Component
public class AuthAop {

    private final JwtUtil jwtUtil;
    private final HttpServletRequest request;

    private static final String HEADER_STRING = "Authorization";
    private static final String TOKEN_PREFIX = "Bearer ";

    public AuthAop(JwtUtil jwtUtil, HttpServletRequest request) {
        this.jwtUtil = jwtUtil;
        this.request = request;
    }

    @Before("@annotation(org.hidevelop.coffeecats.annotation.JwtAuth)")
    public void authenticateJwt(){

        String header = request.getHeader(HEADER_STRING);

        if (header == null || !header.startsWith(TOKEN_PREFIX)) {
            throw new CustomException(NOT_VALID_TOKEN);
        }

        String token = header.substring(TOKEN_PREFIX.length());
        validateToken(token);


        request.setAttribute("memberEmail", jwtUtil.extractMemberEmail(token));
        request.setAttribute("memberId", jwtUtil.extractMemberId(token));
    }

    private void validateToken(String token) {

        //만료 여부 확인
        Date expiration = jwtUtil.extractExpiration(token);

        if(expiration == null || expiration.before(new Date())) {
            throw new CustomException(EXPIRED_TOKEN);
        }


    }
}
