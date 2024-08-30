package com.example.drugtrack.security.dto;

public class UserDetailDTO {
    private Long seq;
    private String id;
    private String role;
    private String companyType;
    private String companyName;
    private String companyRegNumber;
    private String phoneNumber;
    private String email;
    private String active;
    private String username;

    // 생성자, getter, setter 등 추가
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

    // 기본 생성자 및 getter, setter


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
