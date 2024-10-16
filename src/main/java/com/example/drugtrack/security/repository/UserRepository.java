package com.example.drugtrack.security.repository;

import com.example.drugtrack.security.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findById(String id);
    Optional<User> findByPhoneNumber(String phoneNumber);
    Optional<User> findByEmail(String email);
    Optional<User> findByCompanyRegNumberAndActive(String companyRegNumber, String active);
    Optional<User> findByIdAndActive(String id, String active);
    Optional<User> findBySeq(Long seq);

        // 기존 메서드 생략

        @Query("SELECT u FROM User u WHERE "
                + "( :companyRegNumber IS NULL OR :companyRegNumber = '' OR u.companyRegNumber LIKE %:companyRegNumber% ) "
                + "AND ( :id IS NULL OR :id = '' OR u.id LIKE %:id% ) "
                + "AND ( :companyName IS NULL OR :companyName = '' OR u.companyName LIKE %:companyName% ) "
                + "AND ( :companyType IS NULL OR :companyType = '' OR u.companyType = :companyType ) "
                + "AND ( :phoneNumber IS NULL OR :phoneNumber = '' OR REPLACE(u.phoneNumber, '-', '') LIKE %:phoneNumber% ) "
                + "AND ( (:startDate IS NULL OR :startDate = '') OR u.regDate >= :startDate ) "
                + "AND ( (:endDate IS NULL OR :endDate = '') OR u.regDate <= :endDate )")
        List<User> findByAdvancedCriteria(
                @Param("companyRegNumber") String companyRegNumber,
                @Param("id") String id,
                @Param("companyName") String companyName,
                @Param("companyType") String companyType,
                @Param("phoneNumber") String phoneNumber,
                @Param("startDate") String startDate,
                @Param("endDate") String endDate);

}



