package com.example.drugtrack.search.controller;

import com.example.drugtrack.search.dto.BarcodeWrapper;
import com.example.drugtrack.search.entity.ApiDrugList;
import com.example.drugtrack.search.service.BarcodeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * BarcodeController는 바코드를 기반으로 의약품 데이터를 조회하는 API를 제공하는 컨트롤러 클래스입니다.
 * 이 클래스는 BarcodeService를 사용하여 데이터베이스에서 데이터를 조회한 후 클라이언트에게 반환합니다.
 */
@Tag(name = "의약 이력 API", description = "의약 관련 데이터를 조회하고 저장하는 API")
@RestController
@RequestMapping("/search")
public class BarcodeController {

    // BarcodeService 의존성 주입
    private final  BarcodeService barcodeService;

    /**
     * BarcodeController 생성자.
     * BarcodeService 객체를 주입받아 초기화합니다.
     *
     * @param barcodeService 의존성 주입을 위한 BarcodeService 객체
     */
    public BarcodeController(BarcodeService barcodeService) {
        this.barcodeService = barcodeService;
    }

    /**
     * 바코드를 기반으로 의약품 상세 정보를 조회하는 API 엔드포인트.
     * 공공데이터에서 바코드를 기준으로 의약품 데이터를 조회하여 클라이언트에 응답합니다.
     *
     * @param barcode 조회하려는 의약품의 바코드 값
     * @return 조회된 의약품 정보를 담은 BarcodeWrapper 객체
     */
    @Operation(summary = "바코드로 의약품 상세정보 조회(공공데이터)", description = "바코드 기준으로 공공데이터의 의약품 상세정보를 조회합니다.")
    @GetMapping("/getDrugInfo")
    public BarcodeWrapper getDrugsByBarcode(@RequestParam String barcode) {
        List<ApiDrugList> responses = barcodeService.getDrugsByBarcode(barcode);
        String result = (responses != null && !responses.isEmpty()) ? "Y" : "N";

        return new BarcodeWrapper(result, responses);
    }

}
