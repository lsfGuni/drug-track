package com.example.drugtrack.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/*
 * WebConfig는 Spring MVC 설정을 담당하는 클래스입니다.
 * 정적 리소스 핸들링 및 Swagger 설정을 포함하고 있습니다.
 */

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:8081")
                        .allowedOrigins("http://localhost:8082")
                        .allowedOriginPatterns("*")  // 모든 도메인 허용 (단, allowCredentials = true 시에는 명시적으로 도메인을 적어야 함)
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedMethods("Authorization", "Content-Type")
                        .allowedHeaders("*")
                        .allowCredentials(false)  //true일경우 특정 도메인만 허용, false 인 경우 쿠키, 세션 등의 데이터 정보는 응답안함.
                        .maxAge(3600);
            }
        };
    }


    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("NIPA API Documentation")
                        .version("1.0")
                        .description("API documentation"))
                .servers(List.of(
                        new Server().url("http://localhost:8080").description("Local server"),
                        new Server().url("http://13.209.251.75:8080").description("Production server")
                ));
    }
}
