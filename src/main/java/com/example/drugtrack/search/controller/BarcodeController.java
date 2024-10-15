package com.example.drugtrack.search.controller;

import com.example.drugtrack.search.dto.BarcodeWrapper;
import com.example.drugtrack.search.entity.ApiDrugList;
import com.example.drugtrack.search.service.BarcodeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "의약 이력 API", description = "의약 관련 데이터를 조회하고 저장하는 API")
@RestController
@RequestMapping("/search")
public class BarcodeController {

    @Autowired
    private BarcodeService barcodeService;

    @Operation(summary = "바코드로 의약품 상세정보 조회(공공데이터)", description = "바코드 기준으로 공공데이터의 의약품 상세정보를 조회합니다.")
    @GetMapping("/getDrugInfo")
    public BarcodeWrapper getDrugsByBarcode(@RequestParam String barcode) {
        List<ApiDrugList> responses = barcodeService.getDrugsByBarcode(barcode);
        String result = (responses != null && !responses.isEmpty()) ? "Y" : "N";

        return new BarcodeWrapper(result, responses);
    }

}
