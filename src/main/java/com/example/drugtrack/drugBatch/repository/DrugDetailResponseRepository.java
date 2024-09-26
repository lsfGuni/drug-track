package com.example.drugtrack.drugBatch.repository;

import com.example.drugtrack.drugBatch.entity.DrugDetailResponse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DrugDetailResponseRepository extends JpaRepository<DrugDetailResponse, Long> {
    Optional<DrugDetailResponse> findByItemSeq(String itemSeq);
}
