package com.example.drugtrack.security.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "users")
@Data
public class User {

    @Id
    @Schema(hidden = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seq")
    private Long seq;  // 기존의 seq 필드

    @Column(name = "id", unique = true, nullable = false)
    private String id;  // 새로운 클라이언트 ID 필드

    @Column(nullable = false)
    private String password;

    @Column(name = "ROLE", nullable = false)
    @Schema(hidden = true)
    private String role;  //"USER" (유저), "ADMIN" (관리자)

    @Column
    private String companyType;

    @Column
    private String companyName;

    @Column(nullable = false, unique = true)
    private String companyRegNumber;

    @Column
    private String phoneNumber;

    @Column
    private String email;

    @Column(nullable = false)
    @Schema(hidden = true)
    private String active;  // "Y" (활성화), "N" (비활성화)

    @PrePersist
    protected void onCreate() {
        if (this.role == null) {
            this.role = "USER";
        }
        if (this.active == null) {
            this.active = "Y";
        }
    }


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
}
