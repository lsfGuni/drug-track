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

public class JWTFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;

    public JWTFilter(JWTUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getRequestURI();

        System.out.println("Request path: " + path); // 요청 경로 로그

        // Skip JWT validation for permitAll endpoints
        if (isPermitAllEndpoint(path)) {
            System.out.println("Bypassing JWT validation for path: " + path);
            filterChain.doFilter(request, response);
            return;
        }

        String token = jwtUtil.getJwtFromRequest(request);
        System.out.println("JWT Token extracted: " + token);

        // 관리자 페이지에 대한 인증
        if (token != null && jwtUtil.validateToken(token)) {
            String username = jwtUtil.getUsername(token);
            List<GrantedAuthority> authorities = jwtUtil.getAuthorities(token)
                    .stream()
                    .collect(Collectors.toList());

            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(username, null, authorities);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } else if (path.startsWith("/admin")) {
            // 관리자 페이지 접근 시 인증 실패 로그
            System.out.println("Admin page access denied. Invalid or missing JWT token.");
        }
        filterChain.doFilter(request, response);
    }


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
                path.startsWith("/user/update/") || // Corrected path for update
                path.startsWith("/user/details/"); // Corrected path for details
    }

}
