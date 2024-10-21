package com.example.drugtrack.security.repository;

import com.example.drugtrack.security.dto.UserChangeHistoryDto;
import com.example.drugtrack.security.entity.UserInfoHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
/**
 * UserInfoHistoryRepository 인터페이스는 UserInfoHistory 엔티티에 대한 데이터베이스 작업을 처리합니다.
 * 사용자 정보 변경 이력과 관련된 데이터 검색을 지원하며 JpaRepository를 확장하여 기본 CRUD 작업을 제공합니다.
 */
@Repository
public interface UserInfoHistoryRepository extends JpaRepository<UserInfoHistory, Long> {

    /**
     * 사용자 변경 이력을 조회하는 쿼리.
     * 특정 사용자(seq)와 관련된 변경 이력을 DTO 형태로 반환하며,
     * 변경된 필드가 phone_number 또는 email일 때 그 값을 반환하고,
     * 비밀번호는 '****'로 마스킹하여 반환합니다.
     *
     * @param userSeq 변경 이력을 조회할 사용자의 seq 값
     * @return 사용자 변경 이력 목록
     */
    @Query("SELECT new com.example.drugtrack.security.dto.UserChangeHistoryDto( " +
            "u.companyName, u.companyType, u.companyRegNumber, " +
            "CASE WHEN h.changedField = 'phone_number' THEN COALESCE(h.newValue, '') ELSE '' END AS phoneNumber, " +
            "CASE WHEN h.changedField = 'email' THEN COALESCE(h.newValue, '') ELSE '' END AS email, " +
            "CASE WHEN h.changedField = 'password' THEN '****' ELSE '' END AS password, " +  // Mask password as '****'
            "h.changeCount, h.changeDate, h.changedBy) " +
            "FROM User u JOIN UserInfoHistory h ON u.seq = h.userSeq " +
            "WHERE h.changeCount > 0 AND u.seq = :userSeq " +
            "ORDER BY h.changeDate DESC")
    List<UserChangeHistoryDto> findUserChangeHistoryByUserSeq(@Param("userSeq") Long userSeq);


    /**
     * 특정 사용자의 변경 횟수 중 가장 큰 값(max change_count)을 조회하는 쿼리.
     *
     * @param userSeq 변경 횟수를 조회할 사용자의 seq 값
     * @return 해당 사용자의 최대 change_count 값
     */
    @Query("SELECT MAX(u.changeCount) FROM UserInfoHistory u WHERE u.userSeq = :userSeq")
    Integer findMaxChangeCountByUserSeq(@Param("userSeq") Long userSeq);
}
