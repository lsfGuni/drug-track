package com.example.drugtrack.search.repository;

import com.example.drugtrack.search.entity.ApiDrugList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * BarcodeRepository는 api_drug_list 테이블에 접근하는 JPA 리포지토리입니다.
 * 의약품 바코드를 기준으로 데이터를 조회하는 기능을 제공합니다.
 */
@Repository
public interface BarcodeRepository extends JpaRepository<ApiDrugList, Long> {

    /**
     * 특정 바코드 값을 포함하는 의약품 데이터를 조회하는 쿼리.
     * FIND_IN_SET 함수를 사용하여 BAR_CODE 필드에 포함된 바코드 데이터를 검색합니다.
     *
     * @param barcode 검색하려는 바코드 값
     * @return 바코드가 포함된 의약품 데이터 리스트
     */
    @Query(value = "SELECT * FROM api_drug_list WHERE FIND_IN_SET(:barcode, BAR_CODE) > 0", nativeQuery = true)
    List<ApiDrugList> findByBarcode(@Param("barcode") String barcode);


}
