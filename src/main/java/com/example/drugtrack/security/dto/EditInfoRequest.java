package com.example.drugtrack.security.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * EditInfoRequest DTO는 사용자의 비밀번호, 이메일, 연락처 정보를 수정하는데 필요한 데이터를 포함합니다.
 */
@Data
public class EditInfoRequest {
    @Schema(description = "사용자 ID", example = "id")
    private String id;         // 사용자 ID
    @Schema(description = "기존 비밀번호", example = "password")
    private String password; // 기존 비밀번호
    @Schema(description = "변경할 비밀번호", example = "afterPassword")
    private String afterPassword;  // 변경할 비밀번호
    @Schema(description = "이메일", example = "newEmail@example.com")
    private String email;          // 이메일
    @Schema(description = "전화번호", example = "010-9876-5432")
    private String phoneNumber;    // 전화번호

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

    public String getAfterPassword() {
        return afterPassword;
    }

    public void setAfterPassword(String afterPassword) {
        this.afterPassword = afterPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}

