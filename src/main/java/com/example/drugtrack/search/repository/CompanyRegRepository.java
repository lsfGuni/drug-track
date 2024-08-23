package com.example.drugtrack.search.repository;

import com.example.drugtrack.entity.ApiDrugResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanyRegRepository extends JpaRepository<ApiDrugResponse, Long> {
    List<ApiDrugResponse> findByStartCompanyRegNumber(String startCompanyRegNumber);
}
