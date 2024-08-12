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

    public List<ApiDrugResponse> getAllResponses() {
        return repository.findAll();
    }

    public ApiDrugResponse getResponseById(String barcodeData) {
        return repository.findById(barcodeData).orElse(null);
    }

    public ApiDrugResponse saveResponse(ApiDrugResponse response) {
        try {

            String txValueJson = generateTxValue(response);
            response.setTx(txValueJson);

            response.generateHashValue();
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error processing JSON for tx", e);
        }
        return repository.save(response);
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
        txValueMap.put("mfnNumber", response.getMfnNumber());
        txValueMap.put("expDate", response.getExpDate());
        txValueMap.put("serialNumber", response.getSerialNumber());
        txValueMap.put("aggData", response.getAggData());
        txValueMap.put("hashCode", response.getHashCode());
        return objectMapper.writeValueAsString(txValueMap);
    }
}
