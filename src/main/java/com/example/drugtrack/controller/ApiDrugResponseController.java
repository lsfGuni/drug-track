package com.example.drugtrack.controller;

import com.example.drugtrack.entity.ApiDrugResponse;
import com.example.drugtrack.service.ApiDrugResponseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Tag(name = "약물 추적 API", description = "약물 관련 데이터를 조회하고 저장하는 API")
@RestController
@RequestMapping("/medicine-traceability")
public class ApiDrugResponseController {

    private static final Logger logger = LoggerFactory.getLogger(ApiDrugResponseController.class);

    @Autowired
    private ApiDrugResponseService service;

    @Operation(summary = "모든 약물 응답 조회", description = "데이터베이스에 저장된 모든 약물 응답을 조회합니다.")
    @GetMapping
    public List<ApiDrugResponse> getAllResponses() {
        return service.getAllResponses();
    }

    @Operation(summary = "바코드 데이터로 약물 응답 조회", description = "주어진 바코드 데이터에 해당하는 약물 응답을 조회합니다.")
    @GetMapping("/{barcodeData}")
    public ApiDrugResponse getResponseById(@PathVariable String barcodeData) {
        return service.getResponseById(barcodeData);
    }

    @Operation(summary = "약물 응답 생성", description = "새로운 약물 응답을 생성합니다.")
    @PostMapping
    public ApiDrugResponse createResponse(@RequestBody ApiDrugResponse response, HttpServletRequest request) {
        // 요청자의 IP 주소 및 User-Agent 등 로그 기록
        String clientIp = request.getRemoteAddr();
        String userAgent = request.getHeader("User-Agent");
        String requestUri = request.getRequestURI();

        logger.info("POST 요청 발생 - IP: {}, User-Agent: {}, URI: {}", clientIp, userAgent, requestUri);

        // 서비스 단에서 데이터 저장 및 반환
        return service.saveResponse(response);
    }


}
