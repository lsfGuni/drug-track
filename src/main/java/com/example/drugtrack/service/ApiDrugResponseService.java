package com.example.drugtrack.service;

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

    private void ensureNonNullFields(ApiDrugResponse response) {
        if (response.getBarcodeData() == null) {
            response.setBarcodeData("");
        }
        if (response.getCompanyRegNumber() == null) {
            response.setCompanyRegNumber("");
        }
        if (response.getCompanyName() == null) {
            response.setCompanyName("Unknown");
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
        txValueMap.put("companyRegNumber", response.getCompanyRegNumber());
        txValueMap.put("companyName", response.getCompanyName());
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
