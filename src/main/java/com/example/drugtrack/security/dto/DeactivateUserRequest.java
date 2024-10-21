package com.example.drugtrack.security.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
/**
 * DeactivateUserRequest 클래스는 사용자 계정을 비활성화(회원 탈퇴)할 때
 * 필요한 데이터를 담는 DTO입니다. 이 클래스는 사업자 등록번호와 비밀번호를
 * 포함하며, 요청 시 필요한 필수 정보를 제공합니다.
 */
@Data
public class DeactivateUserRequest {

    @Schema(description = "사업자등록번호", example = "1234567890", required = true)
    private String companyRegNumber; // 사업자등록번호 (필수 정보)

    @Schema(description = "비밀번호", example = "password123", required = true)
    private String password; // 비밀번호 (필수 정보)

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
