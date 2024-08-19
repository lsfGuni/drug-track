package com.example.drugtrack.security.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class DeactivateUserRequest {

    @Schema(description = "거래처 사업장 등록 번호", example = "1234567890", required = true)
    private String companyRegNumber;

    @Schema(description = "비밀번호", example = "password123", required = true)
    private String password;

    public String getCompanyRegNumber() {
        return companyRegNumber;
    }

    public void setCompanyRegNumber(String companyRegNumber) {
        this.companyRegNumber = companyRegNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
