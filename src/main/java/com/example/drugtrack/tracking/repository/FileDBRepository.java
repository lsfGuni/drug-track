package com.example.drugtrack.tracking.repository;

import com.example.drugtrack.tracking.entity.FileDB;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileDBRepository extends JpaRepository<FileDB, Long> {
}
