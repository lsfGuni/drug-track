package com.example.drugtrack.search.service;

import com.example.drugtrack.search.entity.ApiDrugList;
import com.example.drugtrack.search.repository.BarcodeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
/**
 * BarcodeService는 바코드를 기반으로 의약품 데이터를 조회하는 서비스 클래스입니다.
 * BarcodeRepository를 사용하여 데이터베이스에서 관련 데이터를 검색합니다.
 */
@Service
public class BarcodeService {
    // BarcodeRepository 의존성 주입
    private final BarcodeRepository barcodeRepository;

    /**
     * BarcodeService 생성자.
     * BarcodeRepository 객체를 주입받아 초기화합니다.
     *
     * @param barcodeRepository 의존성 주입을 위한 BarcodeRepository 객체
     */
    public BarcodeService(BarcodeRepository barcodeRepository) {
        this.barcodeRepository = barcodeRepository;
    }

    /**
     * 바코드를 기반으로 의약품 데이터를 조회하는 메서드.
     * 주어진 바코드를 포함하는 의약품 데이터를 BarcodeRepository에서 가져옵니다.
     *
     * @param barcode 검색하려는 바코드 값
     * @return 주어진 바코드를 포함하는 ApiDrugList 객체 리스트
     */
    public List<ApiDrugList> getDrugsByBarcode(String barcode) {
        List<ApiDrugList> results = barcodeRepository.findByBarcode(barcode);
        System.out.println("Fetched results: " + results.size()); // 로그 추가
        return results;
    }

}
