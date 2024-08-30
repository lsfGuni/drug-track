package com.example.drugtrack.security.repository;

import com.example.drugtrack.security.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findById(String id);
    Optional<User> findByIdAndCompanyType(String id, String companyType);
    Optional<User> findByCompanyRegNumberAndActive(String companyRegNumber, String active);
    Optional<User> findByIdAndActive(String id, String active);
    Optional<User> findBySeq(Long seq);
}

