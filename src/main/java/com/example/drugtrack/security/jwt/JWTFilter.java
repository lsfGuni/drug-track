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
                path.startsWith("/user/details/") || // Corrected path for update
                path.startsWith("/api/**") || // Corrected path for update
                path.startsWith("/api/files-upload"); // Corrected path for details

    }

}
