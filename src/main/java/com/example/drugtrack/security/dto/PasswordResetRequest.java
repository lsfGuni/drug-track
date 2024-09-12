package com.example.drugtrack.security.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "비밀번호 찾기 요청 DTO")
public class PasswordResetRequest {


    @Schema(description = "계정 Email", example = "test@gtestmail.com", required = true)
    private String email;
    // Getters and Setters


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
