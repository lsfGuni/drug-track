package com.example.drugtrack.security.config;

import com.example.drugtrack.security.jwt.JWTFilter;
import com.example.drugtrack.security.jwt.JWTUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.Customizer.withDefaults;
/**
 * SecurityConfig 클래스는 애플리케이션의 보안 설정을 구성하는 클래스입니다.
 * 현재는 JWT 기반 인증 사용중
 * JWT 관련 설정은 jwtFilter를 사용하여 요청을 처리하며, 필터 체인에 추가하여 인증 과정을 처리합니다.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final AuthenticationConfiguration authenticationConfiguration;
    private final JWTUtil jwtUtil;

    public SecurityConfig(AuthenticationConfiguration authenticationConfiguration, JWTUtil jwtUtil) {
        this.authenticationConfiguration = authenticationConfiguration;
        this.jwtUtil = jwtUtil;
    }


    /**
     * 비밀번호 인코더를 BCryptPasswordEncoder로 설정합니다.
     * 사용자 비밀번호는 이 인코더를 통해 암호화됩니다.
     *
     * @return PasswordEncoder 암호화된 비밀번호를 처리하는 BCryptPasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * AuthenticationManager를 Bean으로 등록합니다.
     * 이 매니저는 인증을 처리하며, SecurityConfig에서 사용됩니다.
     *
     * @return AuthenticationManager 인증 관리 객체
     * @throws Exception 인증 처리 과정에서 발생할 수 있는 예외
     */
    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    /**
     * JWT 필터를 Bean으로 등록합니다.
     * 요청이 들어올 때 JWT 토큰을 처리하는 역할을 합니다.
     *
     * @return JWTFilter JWT 토큰을 처리하는 필터
     */
    @Bean
    public JWTFilter jwtFilter() {
        return new JWTFilter(jwtUtil);
    }

    /**
     * SecurityFilterChain을 구성하여 애플리케이션의 보안 정책을 설정합니다.
     * 세션 정책, 요청 허용/차단 정책, JWT 필터 추가, CORS 설정 등을 포함합니다.
     *
     * @param http HttpSecurity 객체로 보안 설정을 처리합니다.
     * @return SecurityFilterChain 보안 필터 체인 객체
     * @throws Exception 설정 과정에서 발생할 수 있는 예외
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // CSRF 보호 비활성화
                .cors(withDefaults()) // 기본 CORS 설정 사용
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)) // 세션 정책 설정 (필요 시 세션 생성)
                .authorizeHttpRequests(auth -> auth
                        // 특정 엔드포인트들에 대한 접근 허용/차단 정책 설정
                        .requestMatchers("/batch/run").permitAll() // 배치 실행 엔드포인트는 모두 허용
                        .requestMatchers("/api/files-save").permitAll() // 파일 저장 API는 모두 허용
                        .requestMatchers("/upload-file").permitAll() // 파일 업로드 API는 모두 허용
                        .requestMatchers("/user/editInfo").permitAll() // 파일 업로드 API는 모두 허용
                        .requestMatchers("/admin/**").hasRole("ADMIN") // 관리자 페이지는 ADMIN 역할만 허용
                        .requestMatchers("/user/forgot-password", "/api/files-upload", "/error").permitAll() // 특정 사용자 관련 API는 모두 허용
                        .requestMatchers("/user/details/**", "/user/update/**").permitAll() // 사용자 상세 정보 및 업데이트는 모두 허용
                        .requestMatchers("/css/**", "/js/**", "/img/**", "/favicon.ico", "/fonts/**").permitAll()  // 정적 자원 접근 허용
                        .requestMatchers("/user/**", "/", "/view/**").permitAll() // 기본 페이지 및 사용자 관련 페이지 모두 허용
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-resources/**", "/webjars/**").permitAll() // Swagger 문서 및 API 리소스 허용
                        .requestMatchers("/static/**", "/iframe/**").permitAll() // 기타 리소스 허용
                        .requestMatchers("/traceability/**", "/search/**", "/api/**").permitAll() // 의약품 이력 및 API 엔드포인트 허용
                        .anyRequest().authenticated() // 그 외의 모든 요청은 인증 필요
                );

        // JWT 필터를 UsernamePasswordAuthenticationFilter 이전에 추가
        http.addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class);

        // Headers 설정: 동일 출처에서의 iframe 접근을 허용
        http.headers(headers -> headers.frameOptions().sameOrigin());

        return http.build();
    }
}
