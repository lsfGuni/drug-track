package com.example.drugtrack.repository;

import com.example.drugtrack.entity.ApiDrugResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApiDrugResponseRepository extends JpaRepository<ApiDrugResponse, Long> {
}
