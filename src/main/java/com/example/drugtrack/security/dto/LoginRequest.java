package com.example.drugtrack.security.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public class LoginRequest {

    @Schema(description = "아이디", example = "ibiz", required = true)
    private String id;

    @Schema(description = "비밀번호", example = "password123", required = true)
    private String password;


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
