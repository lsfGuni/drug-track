package com.example.drugtrack.tracking.repository;

import com.example.drugtrack.tracking.entity.ApiDrugResponse;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ApiDrugResponseRepository extends JpaRepository<ApiDrugResponse, Long> {

    List<ApiDrugResponse> findByStartCompanyRegNumberAndBarcodeData(String startCompanyRegNumber, String barcodeData);

    @Query(value = "SELECT * FROM drug_tracking_data WHERE BARCODE_DATA LIKE CONCAT('%,', :barcodeData, ',%') " +
            "OR BARCODE_DATA LIKE CONCAT(:barcodeData, ',%') " +
            "OR BARCODE_DATA LIKE CONCAT('%,', :barcodeData) " +
            "OR BARCODE_DATA = :barcodeData", nativeQuery = true)
    List<ApiDrugResponse> findByBarcodeData(@Param("barcodeData") String barcodeData);



    @Transactional
    @Modifying
    @Query(value = "UPDATE drug_tracking_data d1 JOIN (SELECT MAX(seq) as maxSeq FROM drug_tracking_data WHERE barcode_data = :barcode) d2 ON d1.seq = d2.maxSeq SET d1.delivery_type = '4' WHERE d1.barcode_data = :barcode", nativeQuery = true)
    int updateDeliveryTypeByBarcode(@Param("barcode") String barcode);

}

