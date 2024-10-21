package com.example.drugtrack.security.jwt;

import com.example.drugtrack.security.dto.CustomUserDetails;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
/**
 * LoginFilter는 사용자가 로그인할 때 인증을 처리하는 필터입니다.
 * Spring Security의 UsernamePasswordAuthenticationFilter를 확장하여
 * 사용자 인증 요청을 처리하고 성공 시 JWT 토큰을 생성하여 응답에 포함합니다.
 */
public class LoginFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;

    /**
     * LoginFilter 생성자.
     *
     * @param authenticationManager 인증을 처리하는 AuthenticationManager
     * @param jwtUtil JWT 생성 및 검증을 위한 유틸리티 클래스
     */
    public LoginFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    /**
     * 사용자가 로그인 요청 시 실행되는 메서드.
     * 로그인 요청에서 사용자 이름과 비밀번호를 추출하고, 인증을 시도합니다.
     *
     * @param request  HttpServletRequest 객체
     * @param response HttpServletResponse 객체
     * @return 인증 객체 (Authentication)
     * @throws AuthenticationException 인증 실패 시 발생하는 예외
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String username = this.obtainUsername(request);
        String password = this.obtainPassword(request);
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password, null);
        return this.authenticationManager.authenticate(authToken);
    }

    /**
     * 인증 성공 시 실행되는 메서드.
     * 인증이 성공하면 JWT 토큰을 생성하여 응답 헤더와 바디에 포함합니다.
     *
     * @param request        HttpServletRequest 객체
     * @param response       HttpServletResponse 객체
     * @param chain          필터 체인
     * @param authentication 인증 객체 (성공한 사용자 정보 포함)
     * @throws IOException 입출력 예외
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException {
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        String username = customUserDetails.getUsername();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();
        String role = auth.getAuthority();
        String token = this.jwtUtil.createJwt(username, role, 3600000L); // 1시간 만료

        // JWT 토큰을 응답 헤더에 추가
        response.addHeader("Authorization", "Bearer " + token);
        response.setContentType("application/json");
        response.getWriter().write("{\"token\": \"" + token + "\"}");
    }

    /**
     * 인증 실패 시 실행되는 메서드.
     * 인증이 실패하면 401 Unauthorized 상태 코드를 응답합니다.
     *
     * @param request  HttpServletRequest 객체
     * @param response HttpServletResponse 객체
     * @param failed   인증 실패 예외
     */
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }
}
