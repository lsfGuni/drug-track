package com.example.drugtrack.tracking.contorller;

import com.example.drugtrack.search.dto.UpdateResponse;
import com.example.drugtrack.search.service.BarcodeService;
import com.example.drugtrack.tracking.dto.ApiDrugResponseWrapper;
import com.example.drugtrack.tracking.entity.ApiDrugResponse;
import com.example.drugtrack.tracking.service.ApiDrugResponseService;
import com.example.drugtrack.tracking.service.BlockchainService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * ApiDrugResponseController는 의약품 관련 데이터를 처리하는 API를 제공합니다.
 * 데이터 등록, 조회, 업데이트 등의 기능을 포함하고 있으며, 블록체인과 연계된 저장 기능도 포함합니다.
 */
@Tag(name = "의약 이력 API", description = "의약 관련 데이터를 조회하고 저장하는 API")
@RestController
@RequestMapping("/traceability")
public class ApiDrugResponseController {

    private static final Logger logger = LoggerFactory.getLogger(ApiDrugResponseController.class);

    private final BlockchainService blockchainService;
    private final ApiDrugResponseService apiDrugResponseService;


    /**
     * 생성자에서 서비스 클래스를 주입받습니다.
     *
     * @param blockchainService 블록체인과 연동하는 서비스
     * @param apiDrugResponseService 의약품 데이터를 처리하는 서비스
     */
    public ApiDrugResponseController(BlockchainService blockchainService, ApiDrugResponseService apiDrugResponseService, BarcodeService barcodeService) {
        this.blockchainService = blockchainService;
        this.apiDrugResponseService = apiDrugResponseService;

    }

    /**
     * 의약품 정보를 등록하는 API.
     * 파라미터로 받은 의약품 정보를 DB 및 블록체인에 저장합니다.
     * 중복이 발생할 경우 예외 처리를 통해 에러를 반환합니다.
     *
     * @param apiKey 클라이언트에서 전달한 API Key
     * @param response 의약품 정보 (JSON 형식)
     * @param request HTTP 요청 정보
     * @return 저장 성공 여부와 에러 메시지를 포함한 Map
     */
    @Operation(summary = "의약품 단건등록 post요청", description = "파라미터를 통해 의약품 정보를 등록합니다.")
    @PostMapping("/regDrugTracking")
    public ResponseEntity<Map<String, String>> createResponse(@RequestHeader("API_KEY") String apiKey, @RequestBody ApiDrugResponse response, HttpServletRequest request) {

        // 받은 데이터의 구체적 필드 값을 로깅
        logger.info("클라이언트 파라미터: {}", response.toString());

        // 클라이언트의 IP 주소, User-Agent, 요청 URI를 로그로 기록
        String clientIp = request.getRemoteAddr();
        String userAgent = request.getHeader("User-Agent");
        String requestUri = request.getRequestURI();

        logger.info("POST 요청 발생 - IP: {}, User-Agent: {}, URI: {}, API_KEY: {}", clientIp, userAgent, requestUri, apiKey);


        // 응답 객체 생성
        Map<String, String> responseMap = new HashMap<>();
        try {
            // Save the single response and extract its seq and hashCode
            ApiDrugResponse savedResponse = apiDrugResponseService.saveResponse(response, apiKey);

            // Bundle seq and hashCode into a single map and add to the list for blockchain storage
            List<Map<String, Object>> dataForBlockchain = new ArrayList<>();
            Map<String, Object> entry = new HashMap<>();
            entry.put("seq", savedResponse.getSeq());
            entry.put("hashcode", savedResponse.getHashCode());
            dataForBlockchain.add(entry);

            // Send the bundled data to blockchain in a single API call
            blockchainService.storeBulkDataOnBlockchain(dataForBlockchain);

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

    /**
     * 바코드를 기준으로 의약품 추적 정보를 조회하는 API.
     *
     * @param barcodeData 조회할 의약품의 바코드 데이터
     * @return 조회 결과와 의약품 데이터 목록을 포함하는 ApiDrugResponseWrapper 객체
     */
    @Operation(summary = "바코드로 의약품 추적정보 조회", description = "바코드 기준으로 의약품 추적정보를 조회합니다.")
    @GetMapping("/getDrugTrackingInfo")
    public ApiDrugResponseWrapper getDrugsByBarcode(@RequestParam String barcodeData) {
        List<ApiDrugResponse> data = apiDrugResponseService.getDrugsByBarcodeData(barcodeData);
        String result = (data != null && !data.isEmpty()) ? "Y" : "N";

        return new ApiDrugResponseWrapper(result, data);
    }

    /**
     * 바코드를 사용하여 의약품의 판매 상태를 업데이트하는 API.
     * 해당 바코드의 delivery_type을 판매 완료 상태(4)로 업데이트합니다.
     *
     * @param barcode 의약품의 바코드
     * @return 업데이트 결과 (Y/N)를 포함한 UpdateResponse 객체
     */
    @Operation(summary = "의약품 판매 완료 업데이트", description = "바코드를 사용하여 DB의 delivery_type을 4(판매코드)로 업데이트합니다.")
    @PostMapping("/updateDeliveryType")
    public UpdateResponse updateDeliveryType(@RequestParam String barcode) {
        boolean isUpdated = apiDrugResponseService.updateDeliveryType(barcode);

        if (!isUpdated) {
            return new UpdateResponse("N");
        }

        // Use BarcodeService to fetch ApiDrugResponse data by barcode
        List<ApiDrugResponse> data = apiDrugResponseService.getDrugsByBarcodeData(barcode);

        if (data.isEmpty()) {
            return new UpdateResponse("N");  // If no records found, return failure response
        }

        // Prepare data for blockchain storage
        List<Map<String, Object>> dataForBlockchain = new ArrayList<>();

        // Loop through each ApiDrugResponse object and extract seq and hashcode
        for (ApiDrugResponse drug : data) {
            Map<String, Object> entry = new HashMap<>();
            entry.put("seq", drug.getSeq());  // `drug` is an ApiDrugResponse object
            entry.put("hashcode", drug.getHashCode());
            dataForBlockchain.add(entry);
        }

        // Store the data on the blockchain
        blockchainService.storeBulkDataOnBlockchain(dataForBlockchain);

        return new UpdateResponse("Y");  // Success response
    }




}
