package com.example.drugtrack.tracking.service;

import com.example.drugtrack.tracking.entity.ApiDrugResponse;
import com.example.drugtrack.tracking.repository.ApiDrugResponseRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * ApiDrugResponseService는 의약품 정보를 처리하는 서비스입니다.
 * 이 서비스는 데이터 저장, 중복 체크, 블록체인 연동을 위한 tx 생성 등의 작업을 수행합니다.
 */
@Service
public class ApiDrugResponseService {

    private final ApiDrugResponseRepository apiDrugResponseRepository;
    private final ObjectMapper objectMapper;
    /**
     * apiDrugResponseRepository 및 objectMapper 주입.
     *
     * @param apiDrugResponseRepository 데이터베이스와 상호작용하는 레포지토리
     * @param objectMapper JSON 처리에 사용되는 객체
     */
    public ApiDrugResponseService(ApiDrugResponseRepository apiDrugResponseRepository, ObjectMapper objectMapper) {
        this.apiDrugResponseRepository = apiDrugResponseRepository;
        this.objectMapper = objectMapper;
    }


    /**
     * 단일 의약품 정보를 저장하는 메서드.
     * 데이터 저장 전 중복을 확인하고, 블록체인 저장을 위한 tx 값을 생성합니다.
     * 중복 시 예외를 발생시키며, 데이터 저장에 실패한 경우 RuntimeException을 던집니다.
     *
     * @param response 저장할 의약품 정보
     * @param apiKey 클라이언트에서 전달된 API Key
     * @return 저장된 의약품 정보
     */
    public ApiDrugResponse saveResponse(ApiDrugResponse response,String apiKey) {

        response.setApiKey(apiKey); // API Key 설정

        // 바코드와 사업자등록번호를 통해 데이터 중복 검사
        String barcodeData = response.getBarcodeData();
        String startCompanyRegNumber = response.getStartCompanyRegNumber();
        String deliveryType = response.getDeliveryType();

        // 동일한 바코드 및 회사 등록번호로 저장된 최신 데이터를 가져옴
        List<ApiDrugResponse> latestResponseList = apiDrugResponseRepository.findByStartCompanyRegNumberAndBarcodeData(startCompanyRegNumber, barcodeData);

        // 최신 데이터가 있는지 확인하고 delivery_type이 동일한 경우 예외 발생
        if (!latestResponseList.isEmpty()) {
            ApiDrugResponse latestResponse = latestResponseList.stream()
                    .max(Comparator.comparing(ApiDrugResponse::getSeq))
                    .orElse(null); // seq 기준으로 가장 최신 데이터 추출

            // delivery_type이 동일한 경우 중복으로 간주하고 예외 발생
            if (latestResponse != null && deliveryType.equals(latestResponse.getDeliveryType())) {
                // delivery_type이 동일한 경우 예외 발생
                throw new IllegalStateException("이미 동일한 출고등록 기록이 있습니다.");
            }
        }


        try {
            // null 값 확인 및 기본값 할당
            ensureNonNullFields(response);

            // 블록체인에 저장할 tx 값 생성
            String txValueJson = generateTxValue(response);
            response.setTx(txValueJson);

            // 데이터의 고유 hash 값을 생성
            response.generateHashValue();
        } catch (JsonProcessingException e) {
            // JSON 처리 중 에러 발생 시 예외 처리
            throw new RuntimeException("Error processing JSON for tx", e);
        }

        // 데이터 저장 후 반환
        return apiDrugResponseRepository.save(response);
    }

    /**
     * 의약품 데이터의 필수 필드가 null인 경우 기본값을 설정하는 메서드.
     *
     * @param response 필드 값이 null일 수 있는 의약품 데이터
     */
    private void ensureNonNullFields(ApiDrugResponse response) {
        if (response.getBarcodeData() == null) {
            response.setBarcodeData("");
        }
        if (response.getStartCompanyRegNumber() == null) {
            response.setStartCompanyRegNumber("");
        }
        if (response.getStartCompanyName() == null) {
            response.setStartCompanyName("Unknown");
        }
        if (response.getEndCompanyRegNumber() == null) {
            response.setEndCompanyRegNumber("");
        }
        if (response.getEndCompanyName() == null) {
            response.setEndCompanyName("Unknown");
        }
        if (response.getDeliveryType() == null) {
            response.setDeliveryType("N/A");
        }
        if (response.getDeliveryDate() == null) {
            response.setDeliveryDate("N/A");
        }
        if (response.getProductName() == null) {
            response.setProductName("Unknown Product");
        }
        if (response.getGs1Code() == null) {
            response.setGs1Code("00000000000000");
        }
        if (response.getMfNumber() == null) {
            response.setMfNumber("N/A");
        }
        if (response.getExpDate() == null) {
            response.setExpDate("N/A");
        }
        if (response.getSerialNumber() == null) {
            response.setSerialNumber("N/A");
        }
        if (response.getAggData() == null) {
            response.setAggData("N/A");
        }
        if (response.getHashCode() == null) {
            response.setHashCode("");
        }
    }

    /**
     * 의약품 데이터를 기반으로 블록체인에 저장할 tx 값을 생성하는 메서드.
     *
     * @param response tx 값을 생성할 의약품 데이터
     * @return JSON 형식의 tx 값
     * @throws JsonProcessingException JSON 처리 중 오류 발생 시 예외 발생
     */
    private String generateTxValue(ApiDrugResponse response) throws JsonProcessingException {
        Map<String, String> txValueMap = new HashMap<>();
        txValueMap.put("barcodeData", response.getBarcodeData());
        txValueMap.put("StartCompanyRegNumber", response.getStartCompanyRegNumber());
        txValueMap.put("StartCompanyName", response.getStartCompanyName());
        txValueMap.put("endCompanyRegNumber", response.getEndCompanyRegNumber());
        txValueMap.put("endCompanyName", response.getEndCompanyName());
        txValueMap.put("deliveryType", response.getDeliveryType());
        txValueMap.put("deliveryDate", response.getDeliveryDate());
        txValueMap.put("productName", response.getProductName());
        txValueMap.put("gs1Code", response.getGs1Code());
        txValueMap.put("mfNumber", response.getMfNumber());
        txValueMap.put("expDate", response.getExpDate());
        txValueMap.put("serialNumber", response.getSerialNumber());
        txValueMap.put("aggData", response.getAggData());
        txValueMap.put("hashCode", response.getHashCode());

        // Map 데이터를 JSON 형식으로 변환
        return objectMapper.writeValueAsString(txValueMap);
    }

    /**
     * 바코드 데이터를 기준으로 의약품 데이터를 조회하는 메서드.
     *
     * @param barcodeData 조회할 바코드 데이터
     * @return 바코드에 해당하는 의약품 데이터 리스트
     */
    public List<ApiDrugResponse> getDrugsByBarcodeData(String barcodeData) {
        List<ApiDrugResponse> results = apiDrugResponseRepository.findByBarcodeData(barcodeData);
        System.out.println("Fetched results: " + results.size());  // 조회된 데이터의 수를 로그로 출력
        return results;
    }


    /**
     * 바코드를 기반으로 의약품의 배송 상태를 업데이트하는 메서드.
     *
     * @param barcode 업데이트할 의약품의 바코드
     * @return 업데이트 성공 여부
     */
    public boolean updateDeliveryType(String barcode) {

        // Check if the latest entry for this barcode is already marked as '4' (sold)
        Integer currentDeliveryType = apiDrugResponseRepository.findCurrentDeliveryTypeByBarcode(barcode);

        if (currentDeliveryType != null && currentDeliveryType == 4) {
            return false;  // 이미 판매 완료 상태인 경우
        }

        int updatedRows = apiDrugResponseRepository.updateDeliveryTypeByBarcode(barcode);
        return updatedRows > 0; // 업데이트된 행의 수가 0보다 크면 성공으로 간주
    }
}
