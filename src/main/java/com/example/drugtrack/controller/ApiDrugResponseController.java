package com.example.drugtrack.controller;

import com.example.drugtrack.entity.ApiDrugResponse;
import com.example.drugtrack.service.ApiDrugResponseService;
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
@RequestMapping("/traceability")
public class ApiDrugResponseController {

    private static final Logger logger = LoggerFactory.getLogger(ApiDrugResponseController.class);

    @Autowired
    private ApiDrugResponseService service;

    @Hidden
    @Operation(summary = "모든 의약품 목록 조회", description = "데이터베이스에 저장된 모든 약물 응답을 조회합니다.")
    @GetMapping("/list")

    public List<ApiDrugResponse> getAllResponses() {
        return service.getAllResponses();
    }

    @Operation(summary = "의약품 등록 post요청", description = "파라미터를 통해 의약품 정보를 등록합니다.")
    @PostMapping("/regDrugTracking")

    public ResponseEntity<Map<String, String>> createResponse(@RequestBody ApiDrugResponse response, HttpServletRequest request) {
        // 요청자의 IP 주소 및 User-Agent 등 로그 기록
        String clientIp = request.getRemoteAddr();
        String userAgent = request.getHeader("User-Agent");
        String requestUri = request.getRequestURI();

        logger.info("POST 요청 발생 - IP: {}, User-Agent: {}, URI: {}", clientIp, userAgent, requestUri);

        // 서비스 단에서 데이터 저장 및 반환
        ApiDrugResponse savedResponse = service.saveResponse(response);

        // 응답 객체 생성
        Map<String, String> responseMap = new HashMap<>();
        if (savedResponse != null) {
            responseMap.put("result", "Y");
            responseMap.put("error", null);
        } else {
            responseMap.put("result", "N");
            responseMap.put("error", "등록이 실패했습니다.");
        }

        return ResponseEntity.ok(responseMap);
    }


}
