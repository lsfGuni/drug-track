package com.example.drugtrack.security.dto;

import java.util.Date;

/**
 * 정보 변경 이력에 대한 데이터를 담는 DTO 클래스.
 * 사용자의 회사 정보, 연락처, 이메일, 비밀번호 등의 변경 이력을 관리합니다.
 */
public class UserChangeHistoryDto {
    private String companyName; // 회사 이름
    private String companyType;  // 회사 유형
    private String companyRegNumber; // 사업자 등록번호
    private String phoneNumber; // 대표 연락처
    private String email; // 이메일 주소
    private String password;  // 비밀번호 (마스킹 처리된 값)
    private int changeCount;  // 변경 횟수
    private Date changeDate;   // 변경 일자
    private String changedBy; // 변경을 수행한 사용자 ID

    /**
     * 생성자 - UserChangeHistoryDto 객체 생성.
     *
     * @param companyName 회사 이름
     * @param companyType 회사 유형
     * @param companyRegNumber 회사 등록번호
     * @param phoneNumber 대표 연락처
     * @param email 이메일 주소
     * @param password 마스킹 처리된 비밀번호
     * @param changeCount 변경 횟수
     * @param changeDate 변경 일자
     * @param changedBy 변경을 수행한 사용자 ID
     */
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
