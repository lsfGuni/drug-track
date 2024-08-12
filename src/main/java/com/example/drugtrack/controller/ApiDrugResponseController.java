package com.example.drugtrack.controller;

import com.example.drugtrack.entity.ApiDrugResponse;
import com.example.drugtrack.service.ApiDrugResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/medicine-traceability")
public class ApiDrugResponseController {

    @Autowired
    private ApiDrugResponseService service;

    @GetMapping
    public List<ApiDrugResponse> getAllResponses() {
        return service.getAllResponses();
    }

    @GetMapping("/{barcodeData}")
    public ApiDrugResponse getResponseById(@PathVariable String barcodeData) {
        return service.getResponseById(barcodeData);
    }
    @PostMapping
    public ApiDrugResponse createResponse(@RequestBody ApiDrugResponse response) {
        // 서비스 단에서 데이터 저장 및 반환
        return service.saveResponse(response);
    }


}
