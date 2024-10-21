package com.example.drugtrack.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
/**
 * JWTFilter는 매 요청마다 JWT 토큰을 검증하고 인증된 사용자의 정보를
 * SecurityContext에 설정하는 역할을 합니다.
 * Spring Security의 OncePerRequestFilter를 상속받아 각 요청당 한 번만 실행됩니다.
 */
public class JWTFilter extends OncePerRequestFilter {
    /**
     * JWT 토큰 검증 및 처리 유틸리티 클래스.
     */
    private final JWTUtil jwtUtil;
    /**
     * JWTFilter 생성자. JWTUtil을 주입받아 토큰 검증에 사용합니다.
     *
     * @param jwtUtil JWT 토큰 관련 유틸리티 클래스
     */
    public JWTFilter(JWTUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }
    /**
     * 필터링되지 않아도 되는 특정 엔드포인트를 정의합니다.
     * 예: 공개 엔드포인트, 정적 파일 등.
     *
     * @param request 현재 요청
     * @return 필터링이 필요 없는 경우 true
     * @throws ServletException 필터링 중 오류 발생 시
     */
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();
        return isPermitAllEndpoint(path);
    }

    /**
     * 필터링이 필요 없는 경로인지 확인하는 메서드.
     * 특정 경로에 대해 JWT 검증을 하지 않도록 설정.
     *
     * @param path 현재 요청 경로
     * @return 필터링이 필요 없는 경우 true
     */
    private boolean isPermitAllEndpoint(String path) {
        return path.equals("/user/get-user-list") ||
                path.startsWith("/css/") ||
                path.startsWith("/js/") ||
                path.startsWith("/images/") ||
                path.equals("/favicon.ico") ||
                path.startsWith("/swagger-ui/") ||
                path.startsWith("/v3/api-docs/") ||
                path.startsWith("/swagger-resources/") ||
                path.startsWith("/webjars/") ||
                path.startsWith("/user/update/") ||
                path.startsWith("/user/details/") ||
                path.startsWith("/api/files-upload") ||
                path.startsWith("/api/files-save"); // 필요한 엔드포인트 추가
    }

    /**
     * 실제로 JWT 토큰을 검증하고, 유효한 토큰일 경우 인증 정보를 설정하는 메서드.
     *
     * @param request  현재 요청
     * @param response 응답
     * @param filterChain 필터 체인
     * @throws ServletException 필터 처리 중 예외 발생 시
     * @throws IOException IO 예외 발생 시
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String token = jwtUtil.getJwtFromRequest(request);

        if (token != null && jwtUtil.validateToken(token)) {
            String username = jwtUtil.getUsername(token);
            List<GrantedAuthority> authorities = jwtUtil.getAuthorities(token)
                    .stream()
                    .collect(Collectors.toList());

            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(username, null, authorities);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        // 인증 실패 시에도 다음 필터로 넘깁니다.
        filterChain.doFilter(request, response);
    }
}
