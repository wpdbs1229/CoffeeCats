package org.hidevelop.coffeecats.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

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

    public boolean validateToken(String token) {
        try {
            Date expiration = getClaims(token).getExpiration();
            return expiration.before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    private Claims getClaims(String token) {

        return Jwts.parser()
                .setSigningKey(SECRET_KET)
                .parseClaimsJws(token)
                .getBody();

    }


}
