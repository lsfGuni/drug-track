package com.example.drugtrack.controller;

import com.example.drugtrack.entity.ApiDrugResponse;
import com.example.drugtrack.service.ApiDrugResponseService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/medicine-traceability")
public class ApiDrugResponseController {

    private static final Logger logger = LoggerFactory.getLogger(ApiDrugResponseController.class);

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
