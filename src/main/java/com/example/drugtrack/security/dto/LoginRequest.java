package com.example.drugtrack.security.dto;

import io.swagger.v3.oas.annotations.media.Schema;
/**
 * LoginRequest 클래스는 사용자 로그인 요청 시 필요한
 * 아이디와 비밀번호 정보를 담는 DTO입니다.
 */
public class LoginRequest {

    @Schema(description = "아이디", example = "ibiz", required = true)
    private String id; // 사용자 아이디 (필수 정보)

    @Schema(description = "비밀번호", example = "password123", required = true)
    private String password;  // 사용자 비밀번호 (필수 정보)


    // Getters and Setters


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
