package com.example.drugtrack.security.config;

import com.example.drugtrack.security.jwt.JWTFilter;
import com.example.drugtrack.security.jwt.JWTUtil;
import com.example.drugtrack.security.jwt.LoginFilter;
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

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final AuthenticationConfiguration authenticationConfiguration;
    private final JWTUtil jwtUtil;


    public SecurityConfig(AuthenticationConfiguration authenticationConfiguration, JWTUtil jwtUtil) {
        this.authenticationConfiguration = authenticationConfiguration;

        this.jwtUtil = jwtUtil;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public JWTFilter jwtFilter() {
        return new JWTFilter(jwtUtil);
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf((auth) -> {
            auth.disable();
        });
        http.formLogin((auth) -> {
            auth.disable();
        });
        http.httpBasic((auth) -> {
            auth.disable();
        });
        http.authorizeHttpRequests((auth) -> {
            auth
                    .requestMatchers("/css/**", "/js/**", "/images/**", "/favicon.ico").permitAll()
                    .requestMatchers("/user/**", "/", "/view/**").permitAll()
                    .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-resources/**", "/webjars/**").permitAll()  // Swagger 관련 경로 허용
                    .requestMatchers("/admin/**").hasRole("ADMIN")
                    .anyRequest().authenticated();
        });
        http.sessionManagement((session) -> {
            session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED);
        });
        // LoginFilter는 인증 시 JWT를 발급하는 필터
        http.addFilterAt(new LoginFilter(this.authenticationManager(this.authenticationConfiguration), this.jwtUtil), UsernamePasswordAuthenticationFilter.class);

        // JWTFilter는 모든 요청에 대해 JWT 검증을 수행하는 필터
        http.addFilterBefore(new JWTFilter(this.jwtUtil), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }


}
