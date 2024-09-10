package com.example.drugtrack.search.repository;

import com.example.drugtrack.search.entity.ApiDrugList;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BarcodeRepository extends JpaRepository<ApiDrugList, Long> {

    @Query(value = "SELECT * FROM api_drug_list WHERE FIND_IN_SET(:barcode, BAR_CODE) > 0", nativeQuery = true)
    List<ApiDrugList> findByBarcode(@Param("barcode") String barcode);


}
