package com.example.drugtrack.search.repository;

import com.example.drugtrack.tracking.entity.ApiDrugResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
/**
 * CompanyRegRepository는 ApiDrugResponse 엔티티에 대해 데이터베이스 작업을 수행하는 리포지토리입니다.
 * 회사의 사업자 등록번호(startCompanyRegNumber)를 기준으로 의약품 정보를 검색하는 메서드를 제공합니다.
 */
@Repository
public interface CompanyRegRepository extends JpaRepository<ApiDrugResponse, Long> {

    /**
     * startCompanyRegNumber 값을 기준으로 ApiDrugResponse 데이터를 조회하는 메서드.
     * 특정 회사의 사업자 등록번호로 의약품 정보를 가져옵니다.
     *
     * @param startCompanyRegNumber 검색하려는 회사의 사업자 등록번호
     * @return 해당 사업자 등록번호에 속한 ApiDrugResponse 객체 리스트
     */
    List<ApiDrugResponse> findByStartCompanyRegNumber(String startCompanyRegNumber);
}
