package com.example.drugtrack.security.repository;

import com.example.drugtrack.security.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
/**
 * UserRepository 인터페이스는 User 엔티티에 대한 데이터베이스 작업을 처리합니다.
 * JpaRepository를 확장하여 기본적인 CRUD 작업과 추가적인 사용자 검색 메서드를 제공합니다.
 */
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findById(String id);
    Optional<User> findByPhoneNumber(String phoneNumber);
    Optional<User> findByEmail(String email);
    Optional<User> findByCompanyRegNumberAndActive(String companyRegNumber, String active);
    Optional<User> findByIdAndActive(String id, String active);
    Optional<User> findBySeq(Long seq);

    /**
     * 여러 검색 조건을 기반으로 사용자를 검색하는 메서드.
     * 주어진 매개변수에 따라 사용자를 필터링합니다.
     *
     * @param companyRegNumber 사업자 등록번호
     * @param id 사용자 ID
     * @param companyName 회사 이름
     * @param companyType 회사 유형
     * @param phoneNumber 전화번호 (하이픈 제거하여 검색)
     * @param startDate 등록 시작일
     * @param endDate 등록 종료일
     * @return 조건에 맞는 사용자 목록
     */
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



