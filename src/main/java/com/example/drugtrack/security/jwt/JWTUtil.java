package com.example.drugtrack.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Date;
import java.util.List;
/**
 * JWTUtil은 JWT 토큰의 생성, 검증, 파싱 및 관련된 유틸리티 기능을 제공합니다.
 * 이 클래스는 JWT를 사용하여 사용자 인증 정보를 관리하는 데 사용됩니다.
 */
@Component
public class JWTUtil {
    // JWT 서명을 위한 secretKey를 정의
    private final SecretKey secretKey;

    /**
     * JWTUtil 생성자. 암호화된 비밀 키를 사용하여 SecretKey를 초기화합니다.
     *
     * @param secret JWT 서명에 사용될 비밀 키 (application.yml에서 주입됨)
     */
    public JWTUtil(@Value("${spring.jwt.secret}") String secret) {
        this.secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), SignatureAlgorithm.HS256.getJcaName());
    }

    /**
     * JWT 토큰을 생성합니다.
     *
     * @param username  JWT 토큰의 subject로 설정될 사용자 이름
     * @param role      JWT 토큰에 포함될 역할 (ROLE_USER, ROLE_ADMIN 등)
     * @param expiredMs 토큰의 만료 시간 (밀리초)
     * @return 생성된 JWT 토큰
     */
    public String createJwt(String username, String role, Long expiredMs) {
        return Jwts.builder()
                .setSubject(username)
                .claim("role", role)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiredMs))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * JWT 토큰의 유효성을 검사합니다.
     *
     * @param token 검증할 JWT 토큰
     * @return 토큰이 유효한 경우 true, 그렇지 않으면 false
     */
    public boolean validateToken(String token) {
        try {
            getClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * JWT 토큰에서 사용자 이름(주제)을 추출합니다.
     *
     * @param token JWT 토큰
     * @return JWT 토큰에 포함된 사용자 이름
     */
    public String getUsername(String token) {
        return getClaims(token).getSubject();
    }

    /**
     * JWT 토큰에서 사용자 권한을 추출합니다.
     *
     * @param token JWT 토큰
     * @return 사용자의 GrantedAuthority 컬렉션
     */
    public Collection<? extends GrantedAuthority> getAuthorities(String token) {
        Claims claims = getClaims(token);
        String role = claims.get("role", String.class);
        // 추출된 역할을 로그로 확인
        System.out.println("Extracted role from token: " + role);

        return List.of(new SimpleGrantedAuthority(role));
    }


    /**
     * JWT 토큰에서 claim을 파싱하고 반환합니다.
     *
     * @param token JWT 토큰
     * @return 파싱된 Claims 객체
     */
    private Claims getClaims(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * HttpServletRequest에서 JWT 토큰을 추출합니다.
     *
     * @param request HttpServletRequest 요청 객체
     * @return JWT 토큰 (Bearer 토큰 헤더에서 추출된 값)
     */
    public String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }


}
