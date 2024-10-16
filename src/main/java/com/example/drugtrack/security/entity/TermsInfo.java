package com.example.drugtrack.security.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "terms_info")
@Data
public class TermsInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "agree_yn")
    private String agreeYn;

    @Column(name = "necessary")
    private String necessary;

    @Column(name = "terms_no")
    private int termsNo;

    @Column(name = "terms_type")
    private String termsType;

    @Column(name = "version")
    private String version;

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
