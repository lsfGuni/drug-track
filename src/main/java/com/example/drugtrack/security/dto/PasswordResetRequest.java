package com.example.drugtrack.security.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
/**
 * 비밀번호 재설정 요청을 처리하는 DTO 클래스.
 * 사용자 이메일을 통해 비밀번호 재설정을 요청합니다.
 */
@Schema(description = "비밀번호 찾기 요청 DTO")
public class PasswordResetRequest {

    @NotBlank(message = "이메일은 필수 입력 항목입니다.")
    @Email(message = "존재하지 않는 메일 형식입니다.")
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
