package com.example.drugtrack.repository;

import com.example.drugtrack.entity.ApiDrugResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ApiDrugResponseRepository extends JpaRepository<ApiDrugResponse, Long> {

    List<ApiDrugResponse> findByStartCompanyRegNumberAndBarcodeData(String startCompanyRegNumber, String barcodeData);

    @Query(value = "SELECT * FROM drug_tracking_data WHERE FIND_IN_SET(:barcodeData, BARCODE_DATA) > 0", nativeQuery = true)
    List<ApiDrugResponse> findByBarcodeData(@Param("barcodeData") String barcodeData);

}

