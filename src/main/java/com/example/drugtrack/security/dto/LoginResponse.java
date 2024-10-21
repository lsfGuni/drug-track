package com.example.drugtrack.security.dto;

/**
 * 로그인 요청에 대한 응답을 담는 DTO 클래스.
 * 로그인 성공 시 발급되는 JWT 토큰을 포함합니다.
 */
public class LoginResponse {
    // JWT 토큰 필드
    private String token;

    public LoginResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
