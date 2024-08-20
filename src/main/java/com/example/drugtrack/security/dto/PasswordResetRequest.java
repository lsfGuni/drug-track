package com.example.drugtrack.security.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "비밀번호 찾기 요청 DTO")
public class PasswordResetRequest {

    @Schema(description = "계정 ID", example = "testUser", required = true)
    private String id;

    @Schema(description = "사업자 구분 값", example = "testCompanyType", required = true)
    private String companyType;

    // Getters and Setters


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCompanyType() {
        return companyType;
    }

    public void setCompanyType(String companyType) {
        this.companyType = companyType;
    }
}
