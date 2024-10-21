package com.example.drugtrack.security.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * UserDetailDTO 클래스는 사용자에 대한 상세 정보를 담는 DTO(Data Transfer Object)입니다.
 * 이 클래스는 주로 사용자 정보 조회 시 반환되는 데이터를 표현합니다.
 */
@Getter
@Setter
public class UserDetailDTO {
    private Long seq; // 사용자의 고유 식별자 (SEQ)
    private String id; // 사용자의 ID
    private String role; // 사용자의 권한 (ROLE)
    private String companyType; // 회사 유형
    private String companyName; // 회사 이름
    private String companyRegNumber; // 회사 등록번호
    private String phoneNumber; // 대표 연락처 (전화번호)
    private String email; // 이메일 주소
    private String active; // 사용자 활성 상태 (활성화 여부)
    private String username; // 사용자 이름 (Username)

    /**
     * 매개변수를 받는 생성자 - 사용자 세부 정보를 초기화합니다.
     *
     * @param seq             사용자의 고유 식별자
     * @param id              사용자의 ID
     * @param role            사용자의 권한
     * @param companyType     회사 유형
     * @param companyName     회사 이름
     * @param companyRegNumber 회사 등록번호
     * @param phoneNumber     대표 연락처
     * @param email           이메일 주소
     * @param active          활성 상태
     * @param username        사용자 이름
     */
    public UserDetailDTO(Long seq, String id, String role, String companyType, String companyName,
                         String companyRegNumber, String phoneNumber, String email, String active, String username) {
        this.seq = seq;
        this.id = id;
        this.role = role;
        this.companyType = companyType;
        this.companyName = companyName;
        this.companyRegNumber = companyRegNumber;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.active = active;
        this.username = username;
    }

    public Long getSeq() {
        return seq;
    }

    public void setSeq(Long seq) {
        this.seq = seq;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

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
