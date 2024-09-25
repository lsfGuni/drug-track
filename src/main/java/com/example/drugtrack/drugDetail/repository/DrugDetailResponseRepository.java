package com.example.drugtrack.drugDetail.repository;

import com.example.drugtrack.drugDetail.model.DrugDetailResponse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DrugDetailResponseRepository extends JpaRepository<DrugDetailResponse, Long> {
    Optional<DrugDetailResponse> findByItemSeq(String itemSeq);
}
