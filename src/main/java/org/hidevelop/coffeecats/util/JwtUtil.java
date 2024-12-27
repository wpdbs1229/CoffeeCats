package org.hidevelop.coffeecats.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.hidevelop.coffeecats.exception.CustomException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

import static org.hidevelop.coffeecats.exception.error.impl.AuthenticationError.*;
@Component
public class JwtUtil {

    private final String SECRET_KET;
    private final long EXPIRATION_TIME;

    /*
    요렇게 생성자 주입을 하는 이유는 테스트 코드를 작성하기 용이해져
     */
    public JwtUtil(
            @Value("${jjwt.secret-key}") String secretKey,
            @Value("${jjwt.expiration-time}") long expirationTime) {

        this.SECRET_KET = secretKey;
        this.EXPIRATION_TIME = expirationTime;
    }

    public String generateToken(Long memberId, String email) {
        return Jwts.builder()
                .setSubject(email)
                .claim("memberId", memberId)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS256, SECRET_KET)
                .compact();
    }

    public String extractMemberEmail(String token) {
        return getClaims(token).getSubject();
    }

    public Long extractMemberId(String token) {
        return getClaims(token).get("memberId", Long.class);
    }

    public Date extractExpiration(String token) {
        return getClaims(token).getExpiration();
    }

    private Claims getClaims(String token) {
        try{
            return Jwts.parser()
                    .setSigningKey(SECRET_KET)
                    .parseClaimsJws(token)
                    .getBody();

        } catch (Exception e) {
            throw new CustomException(NOT_VALID_TOKEN);
        }

    }


}
