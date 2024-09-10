package com.example.drugtrack.search.service;

import com.example.drugtrack.search.entity.ApiDrugList;
import com.example.drugtrack.search.repository.BarcodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BarcodeService {
    @Autowired
    private BarcodeRepository repository;

    public List<ApiDrugList> getDrugsByBarcode(String barcode) {
        List<ApiDrugList> results = repository.findByBarcode(barcode);
        System.out.println("Fetched results: " + results.size()); // 로그 추가
        return results;
    }

}
