package com.example.drugtrack.repository;

import com.example.drugtrack.entity.FileDB;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileDBRepository extends JpaRepository<FileDB, Long> {
}
