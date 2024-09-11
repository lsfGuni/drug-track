package com.example.drugtrack.security.repository;

import com.example.drugtrack.security.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findById(String id);
    Optional<User> findByIdAndCompanyType(String id, String companyType);
    Optional<User> findByCompanyRegNumberAndActive(String companyRegNumber, String active);
    Optional<User> findByIdAndActive(String id, String active);
    Optional<User> findBySeq(Long seq);
    @Query("SELECT u FROM User u WHERE "
            + "( :companyRegNumber IS NULL OR :companyRegNumber = '' OR u.companyRegNumber = :companyRegNumber ) "
            + "AND ( :id IS NULL OR :id = '' OR u.id = :id ) "
            + "AND ( :companyName IS NULL OR :companyName = '' OR u.companyName LIKE %:companyName% )")
    List<User> findByCriteria(@Param("companyRegNumber") String companyRegNumber,
                              @Param("id") String id,
                              @Param("companyName") String companyName);

}

