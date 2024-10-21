package com.example.drugtrack.security.dto;

import lombok.Data;
/**
 * 사용자 정보 업데이트를 위한 DTO 클래스.
 * 사용자의 회사 정보, 연락처, 이메일 등 여러 필드를 업데이트할 수 있습니다.
 */
@Data
public class UpdateUserDTO {
    private String companyType;  // 회사 유형
    private String companyName;  // 회사 이름
    private String companyRegNumber; // 사업자등록번호
    private String phoneNumber;  // 대표 연락처
    private String email;  // 이메일
    private String active; // 활성화 여부
    private String username;  // 사용자 이름

    public String getCompanyType() {
        return companyType;
    }

    public void setCompanyType(String companyType) {
        this.companyType = companyType;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyRegNumber() {
        return companyRegNumber;
    }

    public void setCompanyRegNumber(String companyRegNumber) {
        this.companyRegNumber = companyRegNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
