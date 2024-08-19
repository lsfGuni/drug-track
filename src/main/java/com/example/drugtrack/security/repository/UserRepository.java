package com.example.drugtrack.security.repository;

import com.example.drugtrack.security.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByUsernameAndCompanyType(String username, String companyType);
}

