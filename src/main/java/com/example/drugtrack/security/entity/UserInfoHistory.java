package com.example.drugtrack.security.entity;

import jakarta.persistence.*;
import java.util.Date;
/**
 * UserInfoHistory 엔티티는 사용자 정보 변경 이력을 기록하는 클래스입니다.
 * 이 클래스는 사용자 정보 변경 시 각 변경 사항을 데이터베이스에 저장하는 역할을 합니다.
 * 예를 들어, 이메일, 전화번호, 비밀번호 등의 변경 내역을 저장할 수 있습니다.
 */
@Entity
@Table(name = "user_info_history")
public class UserInfoHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 변경 이력의 고유 식별자 (자동 증가).

    @Column(name = "user_seq")
    private Long userSeq; //사용자 고유 식별자. User 엔티티의 seq 필드를 참조합니다.

    @Column(name = "changed_field")
    private String changedField; // 변경된 필드의 이름 (예: 이메일, 비밀번호 등).

    @Column(name = "old_value")
    private String oldValue; // 변경 전 값.

    @Column(name = "new_value")
    private String newValue; // 변경 후 값.

    @Column(name = "change_count")
    private Integer changeCount; // 변경 횟수.

    @Column(name = "change_date")
    private Date changeDate; // 변경이 발생한 날짜.

    @Column(name = "changed_by")
    private String changedBy;// 변경을 수행한 사용자 (ID).

    // Getters and Setters


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserSeq() {
        return userSeq;
    }

    public void setUserSeq(Long userSeq) {
        this.userSeq = userSeq;
    }

    public String getChangedField() {
        return changedField;
    }

    public void setChangedField(String changedField) {
        this.changedField = changedField;
    }

    public String getOldValue() {
        return oldValue;
    }

    public void setOldValue(String oldValue) {
        this.oldValue = oldValue;
    }

    public String getNewValue() {
        return newValue;
    }

    public void setNewValue(String newValue) {
        this.newValue = newValue;
    }

    public Integer getChangeCount() {
        return changeCount;
    }

    public void setChangeCount(Integer changeCount) {
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

