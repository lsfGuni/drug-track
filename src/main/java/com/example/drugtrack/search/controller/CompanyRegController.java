package com.example.drugtrack.search.controller;

import com.example.drugtrack.dto.ApiDrugResponseWrapper;
import com.example.drugtrack.entity.ApiDrugResponse;
import com.example.drugtrack.search.service.CompanyRegService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "의약 이력 API", description = "의약 관련 데이터를 조회하고 저장하는 API")
@RestController
@RequestMapping("/search")
public class CompanyRegController {

    @Autowired
    private CompanyRegService service;

    @Operation(summary = "사업자번호로 의약품 이력 조회", description = "사업자번호 기준으로 저장된 의약품 이력정보를 조회합니다.")
    @GetMapping("/getDrugTrackingInfo")
    public ApiDrugResponseWrapper getResponseByCompanyRegNumber(@RequestParam String startCompanyRegNumber) {
        List<ApiDrugResponse> responses = service.getResponseByCompanyRegNumber(startCompanyRegNumber);
        String result = (responses != null && !responses.isEmpty()) ? "Y" : "N";
        return new ApiDrugResponseWrapper(result, responses);
    }
}
