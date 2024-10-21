package com.example.drugtrack.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;
/*
 * CorsConfig는 API 서버에서의 CORS 설정을 관리하는 클래스입니다.
 * 특정 Origin에서의 요청을 허용하며, 허용된 메서드와 헤더를 정의하여 CORS 정책을 처리합니다.
 * 이 설정은 전체 API 서버에 적용되며, 필터 레벨에서 CORS 요청을 처리합니다.
 */
@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.setAllowedOrigins(Arrays.asList("http://13.209.251.75:8080", "http://localhost:8081"));
        config.setAllowedHeaders(Arrays.asList("Origin", "Content-Type", "Accept", "Authorization"));
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}
