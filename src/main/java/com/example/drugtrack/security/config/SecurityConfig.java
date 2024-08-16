package com.example.drugtrack.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // CSRF 보호 비활성화 (REST API 용도)
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                // Swagger 관련 경로는 인증 없이 접근 허용
                                .requestMatchers("/register", "/login", "/api/auth/**").permitAll() // 회원가입, 로그인 페이지 접근 허용
                                .requestMatchers(
                                        "/v3/api-docs/**",
                                        "/swagger-ui/**",
                                        "/swagger-ui.html",
                                        "/api/auth/register"
                                ).permitAll()
                                // 나머지 요청은 인증 필요
                                .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login") // 로그인 페이지 설정
                        .defaultSuccessUrl("/main", true) // 로그인 성공 후 리디렉션
                        .failureUrl("/login?error=true") // 로그인 실패 시 리디렉션
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout") // 로그아웃 후 리디렉션
                        .permitAll()
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
