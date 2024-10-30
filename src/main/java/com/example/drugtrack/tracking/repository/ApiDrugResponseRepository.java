package com.example.drugtrack.tracking.repository;

import com.example.drugtrack.tracking.entity.ApiDrugResponse;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * ApiDrugResponseRepository는 의약품 데이터를 관리하는 데이터베이스 레포지토리입니다.
 * JPA를 사용하여 데이터베이스와 상호작용하며, 바코드 및 회사 등록번호에 기반한 데이터 조회와
 * 배송 상태 업데이트 기능을 제공합니다.
 */
@Repository
public interface ApiDrugResponseRepository extends JpaRepository<ApiDrugResponse, Long> {
    /**
     * 시작 회사 등록번호와 바코드 데이터를 기준으로 의약품 데이터를 조회하는 메서드.
     *
     * @param startCompanyRegNumber 시작 회사 등록번호
     * @param barcodeData 바코드 데이터
     * @return 조회된 의약품 데이터 리스트
     */
    List<ApiDrugResponse> findByStartCompanyRegNumberAndBarcodeData(String startCompanyRegNumber, String barcodeData);
    /**
     * 바코드 데이터를 기준으로 의약품 데이터를 조회하는 메서드.
     * 바코드 데이터가 문자열로 저장되어 있을 때, FIND_IN_SET 함수를 사용하여 조회합니다.
     *
     * @param barcodeData 조회할 바코드 데이터
     * @return 바코드에 해당하는 의약품 데이터 리스트
     */
    @Query(value = "SELECT * FROM drug_tracking_data WHERE FIND_IN_SET(:barcodeData, BARCODE_DATA) > 0", nativeQuery = true)
    List<ApiDrugResponse> findByBarcodeData(@Param("barcodeData") String barcodeData);

    /**
     * 바코드의 가장 최신 delivery_type 값을 조회하는 메서드.
     *
     * @param barcode 조회할 바코드
     * @return 가장 최신 delivery_type 값
     */
    @Query(value = "SELECT delivery_type FROM drug_tracking_data WHERE barcode_data = :barcode ORDER BY seq DESC LIMIT 1", nativeQuery = true)
    Integer findCurrentDeliveryTypeByBarcode(@Param("barcode") String barcode);

    /**
     * 바코드를 기준으로 의약품의 배송 상태(delivery_type)를 '4'(판매 완료)로 업데이트하는 메서드.
     * 최신 데이터를 기준으로 업데이트를 수행합니다.
     *
     * @param barcode 업데이트할 바코드 데이터
     * @return 업데이트된 행의 수 (성공적으로 업데이트된 경우 1 이상)
     */
    @Transactional
    @Modifying
    @Query(value = "UPDATE drug_tracking_data d1 JOIN (SELECT MAX(seq) as maxSeq FROM drug_tracking_data WHERE barcode_data = :barcode) d2 ON d1.seq = d2.maxSeq SET d1.delivery_type = '4' WHERE d1.barcode_data = :barcode", nativeQuery = true)
    int updateDeliveryTypeByBarcode(@Param("barcode") String barcode);

}

