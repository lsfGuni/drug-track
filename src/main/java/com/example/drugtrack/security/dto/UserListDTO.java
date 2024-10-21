package com.example.drugtrack.security.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
/**
 * UserListDTO 클래스는 사용자 목록을 조회할 때 필요한 사용자 정보를 담는 데이터 전송 객체입니다.
 * 주로 관리자 페이지에서 사용자 목록을 표시하거나 검색 시 활용됩니다.
 */
public class UserListDTO {
    private Long seq;    // 사용자의 고유 식별자 (SEQ)
    private String id; // 사용자의 ID
    private String username;  // 사용자의 이름 (Username)
    private String role; // 사용자의 권한 (Role)
    private String companyType; // 회사 유형
    private String companyName; // 회사 이름
    private String companyRegNumber; // 회사 등록번호
    private String phoneNumber; // 대표 연락처 (전화번호)
    private String email; // 이메일 주소
    private String active; // 활성 상태 (계정 활성화 여부)
    private LocalDateTime regDate; // 회원 등록 일자


    /**
     * 모든 필드를 초기화하는 생성자
     *
     * @param seq             사용자의 고유 식별자
     * @param id              사용자의 ID
     * @param username        사용자의 이름
     * @param role            사용자의 권한
     * @param companyType     회사 유형
     * @param companyName     회사 이름
     * @param companyRegNumber 회사 등록번호
     * @param phoneNumber     대표 연락처
     * @param email           이메일 주소
     * @param active          활성 상태
     * @param regDate         등록 일자
     */
    public UserListDTO(Long seq, String id, String username, String role, String companyType,
                       String companyName, String companyRegNumber, String phoneNumber,
                       String email, String active, LocalDateTime regDate) {
        this.seq = seq;
        this.id = id;
        this.username = username;
        this.role = role;
        this.companyType = companyType;
        this.companyName = companyName;
        this.companyRegNumber = companyRegNumber;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.active = active;
        this.regDate = regDate;
    }

    // Getter와 Setter
    public Long getSeq() { return seq; }
    public void setSeq(Long seq) { this.seq = seq; }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getCompanyType() { return companyType; }
    public void setCompanyType(String companyType) { this.companyType = companyType; }

    public String getCompanyName() { return companyName; }
    public void setCompanyName(String companyName) { this.companyName = companyName; }

    public String getCompanyRegNumber() { return companyRegNumber; }
    public void setCompanyRegNumber(String companyRegNumber) { this.companyRegNumber = companyRegNumber; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getActive() { return active; }
    public void setActive(String active) { this.active = active; }

    public LocalDateTime getRegDate() {
        return regDate;
    }

    public void setRegDate(LocalDateTime regDate) {
        this.regDate = regDate;
    }
}
