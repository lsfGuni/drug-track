package com.example.drugtrack.security.repository;

import com.example.drugtrack.security.dto.UserChangeHistoryDto;
import com.example.drugtrack.security.entity.UserInfoHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
//정보변경 이력 레포지토리
@Repository
public interface UserInfoHistoryRepository extends JpaRepository<UserInfoHistory, Long> {


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






    // Find the max change_count for a specific user
    @Query("SELECT MAX(u.changeCount) FROM UserInfoHistory u WHERE u.userSeq = :userSeq")
    Integer findMaxChangeCountByUserSeq(@Param("userSeq") Long userSeq);
}
