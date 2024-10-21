package com.example.drugtrack.security.entity;

import jakarta.persistence.*;
import lombok.Data;

/**
 * TermsInfo 엔티티 클래스는 사용자가 동의한 약관 정보를 저장하기 위한 데이터베이스 테이블 매핑 클래스입니다.
 * 이 클래스는 약관 동의 여부, 필수 여부, 약관 번호, 약관 유형, 약관 버전 등의 정보를 저장합니다.
 */
@Entity
@Table(name = "terms_info")
@Data
public class TermsInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; //약관 정보의 고유 ID (자동 증가).

    @Column(name = "agree_yn")
    private String agreeYn; // 약관 동의 여부 ("Y" 또는 "N").

    @Column(name = "necessary")
    private String necessary; // 약관의 필수 여부 ("Y" 또는 "N").

    @Column(name = "terms_no")
    private int termsNo; // 약관 번호 (고유 식별자).

    @Column(name = "terms_type")
    private String termsType; // 약관의 유형 (예: 개인정보 처리방침, 이용약관 등).

    @Column(name = "version")
    private String version; // 약관의 버전 (예: "1.0", "2.0" 등).

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAgreeYn() {
        return agreeYn;
    }

    public void setAgreeYn(String agreeYn) {
        this.agreeYn = agreeYn;
    }

    public String getNecessary() {
        return necessary;
    }

    public void setNecessary(String necessary) {
        this.necessary = necessary;
    }

    public int getTermsNo() {
        return termsNo;
    }

    public void setTermsNo(int termsNo) {
        this.termsNo = termsNo;
    }

    public String getTermsType() {
        return termsType;
    }

    public void setTermsType(String termsType) {
        this.termsType = termsType;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
