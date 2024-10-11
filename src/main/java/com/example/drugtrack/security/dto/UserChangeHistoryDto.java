package com.example.drugtrack.security.dto;

import java.util.Date;
//정보변경 이력 DTO
public class UserChangeHistoryDto {
    private String companyName;
    private String companyType;
    private String companyRegNumber;
    private String phoneNumber;
    private String email;
    private String password;  // 추가: 마스킹된 비밀번호
    private int changeCount;
    private Date changeDate;
    private String changedBy;

    public UserChangeHistoryDto(String companyName, String companyType, String companyRegNumber,
                                String phoneNumber, String email, String password,  // 추가: 비밀번호 필드
                                int changeCount, Date changeDate, String changedBy) {
        this.companyName = companyName;
        this.companyType = companyType;
        this.companyRegNumber = companyRegNumber;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.password = password;  // 비밀번호 마스킹 처리된 값 저장
        this.changeCount = changeCount;
        this.changeDate = changeDate;
        this.changedBy = changedBy;
    }

    // Getters and setters

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyType() {
        return companyType;
    }

    public void setCompanyType(String companyType) {
        this.companyType = companyType;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getChangeCount() {
        return changeCount;
    }

    public void setChangeCount(int changeCount) {
        this.changeCount = changeCount;
    }

    public Date getChangeDate() {
        return changeDate;
    }

    public void setChangeDate(Date changeDate) {
        this.changeDate = changeDate;
    }

    public String getChangedBy() {
        return changedBy;
    }

    public void setChangedBy(String changedBy) {
        this.changedBy = changedBy;
    }
}
