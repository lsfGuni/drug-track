package com.example.drugtrack.security.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * User 엔티티 클래스는 사용자 정보를 관리하기 위한 데이터베이스 테이블 매핑 클래스입니다.
 * 이 클래스는 사용자 ID, 비밀번호, 역할, 회사 정보, 연락처, 이메일, 가입 날짜, 계정 활성화 상태 등을 저장합니다.
 */
@Entity
@Table(name = "users")
@Data
public class User {

    @Id
    @Schema(hidden = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seq")
    private Long seq;  // 사용자 고유 식별자 (자동 증가).

    @Column(name = "id", nullable = false)
    private String id;  // 사용자 ID (클라이언트 ID와 동일).

    @Column(nullable = false)
    private String password; //사용자 비밀번호.

    @Column(name = "role", nullable = false)
    @Schema(hidden = true)
    private String role;  //"USER" (유저), "ADMIN" (관리자)

    @Column
    private String companyType; //회사 유형 (예: 법인, 개인).

    @Column
    private String companyName; //회사 이름.

    @Column
    private String companyRegNumber; // 회사 등록 번호 (사업자등록번호).

    @Column
    private String phoneNumber; // 회사 대표 연락처.

    @Column
    private String email; // 사용자 이메일.


    @Column
    @Schema(hidden = true)
    private String regDate; // 사용자 등록 날짜 (자동 설정).

    @Column(nullable = false)
    @Schema(hidden = true)
    private String active;  // "Y" (활성화), "N" (비활성화)

    @Column
    @Schema(hidden = true)
    private String username; //사용자 이름 (기본값은 ID와 동일하게 설정됨).

    /**
     * 사용자 등록 시 기본 필드 값을 설정하는 메서드.
     * 역할이 설정되지 않았을 경우 기본값을 "ROLE_USER"로 설정.
     * 계정 활성화 상태가 설정되지 않았을 경우 "Y"로 설정.
     * username이 설정되지 않았을 경우 ID와 동일하게 설정.
     * 등록 날짜가 설정되지 않았을 경우 현재 시간을 자동으로 설정.
     */
    @PrePersist
    protected void onCreate() {
        if (this.role == null) {
            this.role = "ROLE_USER";
        }
        if (this.active == null) {
            this.active = "Y";
        }
        if (this.username == null) {
            this.username = this.id; // id와 동일하게 설정하거나 적절한 기본값 설정
        }
        // regDate를 현재 시간으로 설정
        if (this.regDate == null) {
            this.regDate = getCurrentTime();
        }
    }

    /**
     * 현재 시간을 "yyyy-MM-dd HH:mm:ss" 형식의 문자열로 변환하여 반환하는 메서드.
     */
    private String getCurrentTime() {
        // 원하는 형식으로 현재 시간을 문자열로 변환
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.now().format(formatter);
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

    public Long getSeq() {
        return seq;
    }

    public void setSeq(Long seq) {
        this.seq = seq;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRegDate() {
        return regDate;
    }

    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }
}
