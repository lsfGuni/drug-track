package com.example.drugtrack.controller;

import com.example.drugtrack.entity.ApiDrugResponse;
import com.example.drugtrack.service.ApiDrugResponseService;
import com.example.drugtrack.service.BlockchainService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Tag(name = "의약 이력 API", description = "의약 관련 데이터를 조회하고 저장하는 API")

@RestController
@RequestMapping("/medicine-traceability")
public class ApiDrugResponseController {

    private static final Logger logger = LoggerFactory.getLogger(ApiDrugResponseController.class);

    @Autowired
    private ApiDrugResponseService apiDrugResponseService;

    @Autowired
    private BlockchainService blockchainIntegrationService;

    @Hidden
    @Operation(summary = "모든 약물 응답 조회", description = "데이터베이스에 저장된 모든 약물 응답을 조회합니다.")
    @GetMapping
    public List<ApiDrugResponse> getAllResponses() {
        return apiDrugResponseService.getAllResponses();
    }

    @Hidden
    @GetMapping("/{barcodeData}")
    public ApiDrugResponse getResponseById(@PathVariable String barcodeData) {
        return apiDrugResponseService.getResponseById(barcodeData);
    }

    @Operation(summary = "의약품 등록 post 요청", description = "post요청을 통해 의약품 정보를 등록합니다.")
    @PostMapping
    public ResponseEntity<Map<String, String>> createResponse(@RequestBody ApiDrugResponse response, HttpServletRequest request) {
        // 요청자의 IP 주소 및 User-Agent 등 로그 기록
        String clientIp = request.getRemoteAddr();
        String userAgent = request.getHeader("User-Agent");
        String requestUri = request.getRequestURI();

        logger.info("POST 요청 발생 - IP: {}, User-Agent: {}, URI: {}", clientIp, userAgent, requestUri);

        // 1. 데이터베이스에 데이터 저장
        ApiDrugResponse savedResponse = apiDrugResponseService.saveResponse(response);

        // 2. 블록체인에 데이터 저장
        String transactionHash = null;
        if (savedResponse != null) {
            transactionHash = blockchainIntegrationService.sendDataToBlockchain(savedResponse);
        }
        // 3.응답 객체 생성
        Map<String, String> responseMap = new HashMap<>();
        if (savedResponse != null && transactionHash != null) {
            responseMap.put("result", "Y");
            responseMap.put("transactionHash", transactionHash);
        } else {
            responseMap.put("result", "N");
        }

        return ResponseEntity.ok(responseMap);
    }


}
