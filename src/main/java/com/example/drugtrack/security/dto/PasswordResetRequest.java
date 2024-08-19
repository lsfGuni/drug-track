package com.example.drugtrack.security.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "비밀번호 찾기 요청 DTO")
public class PasswordResetRequest {

    @Schema(description = "계정 ID", example = "testUser", required = true)
    private String companyRegNumber;

    @Schema(description = "사업자 구분 값", example = "testCompanyType", required = true)
    private String companyType;

    // Getters and Setters


    public String getCompanyRegNumber() {
        return companyRegNumber;
    }

    public void setCompanyRegNumber(String companyRegNumber) {
        this.companyRegNumber = companyRegNumber;
    }

    public String getCompanyType() {
        return companyType;
    }

    public void setCompanyType(String companyType) {
        this.companyType = companyType;
    }
}
