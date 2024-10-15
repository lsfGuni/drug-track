package com.example.drugtrack.tracking.service;


import com.example.drugtrack.tracking.entity.FilesSave;
import com.example.drugtrack.tracking.repository.FilesSaveRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FilesSaveService {

    @Autowired
    private FilesSaveRepository filesSaveRepository;

    @Transactional
    public List<FilesSave> saveCsvDataToDB(List<String[]> csvData, String apiKey) {
        List<FilesSave> savedEntities = new ArrayList<>();
        // 첫 번째 행은 헤더이므로 건너뜀
        for (int i = 1; i < csvData.size(); i++) {
            String[] rowData = csvData.get(i);
            FilesSave filesSave = new FilesSave();

            // 필드 설정
            filesSave.setBarcodeData(rowData.length > 0 ? rowData[0] : null);
            filesSave.setStartCompanyRegNumber(rowData.length > 1 ? rowData[1] : null);
            filesSave.setStartCompanyName(rowData.length > 2 ? rowData[2] : null);
            filesSave.setEndCompanyRegNumber(rowData.length > 3 ? rowData[3] : null);
            filesSave.setEndCompanyName(rowData.length > 4 ? rowData[4] : null);
            filesSave.setDeliveryType(rowData.length > 5 ? rowData[5] : null);
            filesSave.setDeliveryDate(rowData.length > 6 ? rowData[6] : null);
            filesSave.setProductName(rowData.length > 7 ? rowData[7] : null);
            filesSave.setGs1Code(rowData.length > 8 ? rowData[8] : null);
            filesSave.setMfNumber(rowData.length > 9 ? rowData[9] : null);
            filesSave.setExpDate(rowData.length > 10 ? rowData[10] : null);
            filesSave.setSerialNumber(rowData.length > 11 ? rowData[11] : null);
            filesSave.setAggData(rowData.length > 12 ? rowData[12] : null);



            // 여기에서 apiKey를 설정
            filesSave.setApiKey(apiKey);

            // Generate tx value and set it
            try {
                String txValue = generateTxValue(filesSave);
                filesSave.setTx(txValue);
            } catch (JsonProcessingException e) {
                throw new RuntimeException("Failed to generate tx value", e);
            }

            // Generate hash code
            filesSave.generateHashValue();

            // 데이터베이스에 저장
            filesSaveRepository.save(filesSave);

            FilesSave savedEntity = filesSaveRepository.save(filesSave);
            savedEntities.add(savedEntity);

        }
        return savedEntities;

    }



    /*
    * 해시값 생성 메소드
    * */
    @Autowired
    private ObjectMapper objectMapper;

    private String generateTxValue(FilesSave filesSave) throws JsonProcessingException {
        Map<String, String> txValueMap = new HashMap<>();
        txValueMap.put("barcodeData", filesSave.getBarcodeData());
        txValueMap.put("StartCompanyRegNumber", filesSave.getStartCompanyRegNumber());
        txValueMap.put("StartCompanyName", filesSave.getStartCompanyName());
        txValueMap.put("endCompanyRegNumber", filesSave.getEndCompanyRegNumber());
        txValueMap.put("endCompanyName", filesSave.getEndCompanyName());
        txValueMap.put("deliveryType", filesSave.getDeliveryType());
        txValueMap.put("deliveryDate", filesSave.getDeliveryDate());
        txValueMap.put("productName", filesSave.getProductName());
        txValueMap.put("gs1Code", filesSave.getGs1Code());
        txValueMap.put("mfNumber", filesSave.getMfNumber());
        txValueMap.put("expDate", filesSave.getExpDate());
        txValueMap.put("serialNumber", filesSave.getSerialNumber());
        txValueMap.put("aggData", filesSave.getAggData());
        return objectMapper.writeValueAsString(txValueMap);
    }

}
