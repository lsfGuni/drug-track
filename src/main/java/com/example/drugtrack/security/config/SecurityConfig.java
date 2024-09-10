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

        http.csrf(csrf -> csrf.disable());
        http.formLogin(form -> form
                .loginPage("/view/login") // 로그인 페이지 경로 설정
                .defaultSuccessUrl("/view/main", true)  // 로그인 성공 후 리다이렉트
                .permitAll() // 로그인 페이지는 누구나 접근할 수 있게 허용
        );
        http.httpBasic(httpBasic -> httpBasic.disable());

        http.authorizeHttpRequests(auth -> {
            auth
                    .requestMatchers("/admin/**").hasRole("ADMIN")

                    .requestMatchers("/user/forgot-password").permitAll()
                    .requestMatchers("/api/files-upload", "/error").permitAll()
                    .requestMatchers("/user/details/**").permitAll() // Corrected typo
                    .requestMatchers("/user/update/**").permitAll()
                    .requestMatchers("/css/**", "/js/**", "/img/**", "/favicon.ico", "/fonts/**").permitAll()
                    .requestMatchers("/user/**", "/", "/view/**").permitAll()
                    .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-resources/**", "/webjars/**").permitAll()
                    .requestMatchers("/api/files-upload").permitAll()
                    .requestMatchers("/static/**", "/iframe/**").permitAll()
                    .requestMatchers("/traceability/**", "/search/**","/api/**").permitAll()
                    .anyRequest().authenticated();
        });

        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED));

        http.addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class);

        http.headers(headers -> headers
                .frameOptions().sameOrigin()  // Allows framing from the same origin
        );


        http.anonymous(auth -> auth.disable());


        return http.build();
    }

}
