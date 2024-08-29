package com.example.drugtrack.service;

import com.example.drugtrack.dto.DrugTrackingRequestDTO;
import com.example.drugtrack.entity.ApiDrugResponse;
import com.example.drugtrack.repository.ApiDrugResponseRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ApiDrugResponseService {

    @Autowired
    private ApiDrugResponseRepository repository;

    @Autowired
    private ObjectMapper objectMapper;

    //모든 의약품정보 조회하는 서비스클래스
    public List<ApiDrugResponse> getAllResponses() {
        return repository.findAll();
    }

    // 단일 의약품 정보 저장
    public ApiDrugResponse saveResponse(ApiDrugResponse response) {
        try {
            ensureNonNullFields(response);

            String txValueJson = generateTxValue(response);
            response.setTx(txValueJson);

            response.generateHashValue();
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error processing JSON for tx", e);
        }
        return repository.save(response);
    }


    // 다중 의약품 정보 저장
    public void saveResponseBatch(List<DrugTrackingRequestDTO> trackingRequests) {
        for (DrugTrackingRequestDTO trackingRequest : trackingRequests) {
            for (var serialNumber : trackingRequest.getSerialNumbers()) {
                ApiDrugResponse response = new ApiDrugResponse();
                response.setStartCompanyRegNumber(trackingRequest.getStartCompanyRegNumber());
                response.setStartCompanyName(trackingRequest.getStartCompanyName());
                response.setEndCompanyRegNumber(trackingRequest.getEndCompanyRegNumber());
                response.setEndCompanyName(trackingRequest.getEndCompanyName());
                response.setDeliveryType(trackingRequest.getDeliveryType());
                response.setDeliveryDate(trackingRequest.getDeliveryDate());
                response.setProductName(trackingRequest.getProductName());
                response.setGs1Code(trackingRequest.getGs1Code());
                response.setMfNumber(trackingRequest.getMfNumber());
                response.setExpDate(trackingRequest.getExpDate());
                response.setBarcodeData(serialNumber.getBarcodeData());
                response.setSerialNumber(serialNumber.getSerialNumber());
                response.setAggData(serialNumber.getAggData());

                try {
                    String txValueJson = generateTxValue(response);
                    response.setTx(txValueJson);
                    response.generateHashValue();
                } catch (JsonProcessingException e) {
                    throw new RuntimeException("Error processing JSON for tx", e);
                }

                repository.save(response);
            }
        }
    }

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
        return objectMapper.writeValueAsString(txValueMap);
    }
}
