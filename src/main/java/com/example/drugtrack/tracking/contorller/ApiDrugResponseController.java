package com.example.drugtrack.tracking.contorller;

import com.example.drugtrack.search.dto.UpdateResponse;
import com.example.drugtrack.tracking.dto.ApiDrugResponseWrapper;
import com.example.drugtrack.tracking.dto.DrugTrackingListRequestDTO;
import com.example.drugtrack.tracking.entity.ApiDrugResponse;
import com.example.drugtrack.tracking.service.ApiDrugResponseService;
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

    @Operation(summary = "의약품 단건등록 post요청", description = "파라미터를 통해 의약품 정보를 등록합니다.")
    @PostMapping("/regDrugTracking")
    public ResponseEntity<Map<String, String>> createResponse(@RequestHeader("API_KEY") String apiKey, @RequestBody ApiDrugResponse response, HttpServletRequest request) {
        // 요청자의 IP 주소 및 User-Agent 등 로그 기록
        String clientIp = request.getRemoteAddr();
        String userAgent = request.getHeader("User-Agent");
        String requestUri = request.getRequestURI();

        logger.info("POST 요청 발생 - IP: {}, User-Agent: {}, URI: {}, API_KEY: {}", clientIp, userAgent, requestUri, apiKey);


        // 응답 객체 생성
        Map<String, String> responseMap = new HashMap<>();
        try {
            // 서비스 단에서 데이터 저장 및 중복 검사
            ApiDrugResponse savedResponse = service.saveResponse(response, apiKey);

            // 성공적인 저장
            responseMap.put("result", "Y");
            responseMap.put("error", null);
        } catch (IllegalStateException e) {
            // 중복 발생 시 에러 응답
            responseMap.put("result", "N");
            responseMap.put("error", e.getMessage());
        }

        return ResponseEntity.ok(responseMap);
    }


    @Operation(summary = "다중 의약품 등록", description = "다수의 파라미터를 통해 의약품 정보를 한 번에 등록합니다.")
    @PostMapping("/regDrugTracking-list")
    public ResponseEntity<Map<String, String>> createResponseBatch(@RequestBody DrugTrackingListRequestDTO request, HttpServletRequest httpServletRequest) {
        String clientIp = httpServletRequest.getRemoteAddr();
        String userAgent = httpServletRequest.getHeader("User-Agent");
        String requestUri = httpServletRequest.getRequestURI();

        logger.info("다중 의약품 등록 요청 발생 - IP: {}, User-Agent: {}, URI: {}", clientIp, userAgent, requestUri);

        // 응답 객체 생성
        Map<String, String> responseMap = new HashMap<>();

        try {
            if (request.getDrugTrackingList() == null || request.getDrugTrackingList().isEmpty()) {
                logger.error("drugTrackingList가 null 또는 비어 있습니다.");
                responseMap.put("result", "N");
                responseMap.put("error", "drugTrackingList가 null 또는 비어 있습니다.");
                return ResponseEntity.badRequest().body(responseMap);
            }

            // 서비스 레이어에서 데이터 저장
            service.saveResponseBatch(request.getDrugTrackingList());

            responseMap.put("result", "Y");
            responseMap.put("error", "");
        } catch (Exception e) {
            logger.error("데이터 처리 중 오류 발생", e);
            responseMap.put("result", "N");
            responseMap.put("error", "데이터 처리 중 오류 발생: " + e.getMessage());
        }

        return ResponseEntity.ok(responseMap);
    }


    @Operation(summary = "바코드로 의약품 추적정보 조회", description = "바코드 기준으로 의약품 추적정보를 조회합니다.")
    @GetMapping("/getDrugTrackingInfo")
    public ApiDrugResponseWrapper getDrugsByBarcode(@RequestParam String barcodeData) {
        List<ApiDrugResponse> data = service.getDrugsByBarcodeData(barcodeData);
        String result = (data != null && !data.isEmpty()) ? "Y" : "N";

        return new ApiDrugResponseWrapper(result, data);
    }


    @Operation(summary = "의약품 판매 완료 업데이트", description = "바코드를 사용하여 DB의 delivery_type을 4(판매코드)로 업데이트합니다.")
    @PostMapping("/updateDeliveryType")
    public UpdateResponse updateDeliveryType(@RequestParam String barcode) {
        boolean isUpdated = service.updateDeliveryType(barcode);
        String result = isUpdated ? "Y" : "N";
        return new UpdateResponse(result);
    }
}
