package com.example.drugtrack.tracking.service;


import com.example.drugtrack.tracking.entity.FilesSave;
import com.example.drugtrack.tracking.repository.FilesSaveRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * FilesSaveService는 CSV 데이터를 데이터베이스에 저장하는 로직을 담당하는 서비스 클래스입니다.
 * 각 CSV 행을 FilesSave 엔티티로 변환하여 데이터베이스에 저장하고, 트랜잭션을 관리합니다.
 */
@Service
public class FilesSaveService {


    private final FilesSaveRepository filesSaveRepository;
    private final ObjectMapper objectMapper;
    /**
     * filesSaveRepository 및 objectMapper 주입.
     *
     * @param filesSaveRepository 데이터베이스와 상호작용하는 레포지토리
     * @param objectMapper JSON 처리에 사용되는 객체
     */
    public FilesSaveService(FilesSaveRepository filesSaveRepository, ObjectMapper objectMapper) {
        this.filesSaveRepository = filesSaveRepository;
        this.objectMapper = objectMapper;
    }

    /**
     * CSV 데이터를 데이터베이스에 저장하는 메서드입니다.
     * 각 CSV 행을 FilesSave 엔티티로 변환하여 저장하며, API 키와 트랜잭션 값을 설정합니다.
     *
     * @param csvData CSV 데이터 목록. 각 배열은 CSV의 한 행을 나타냅니다.
     * @param apiKey API 요청에 사용된 키. 각 엔티티에 저장됩니다.
     * @return 저장된 FilesSave 엔티티 목록
     */
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



            // API 키 설정
            filesSave.setApiKey(apiKey);

            // 트랜잭션 값을 생성하고 설정
            try {
                String txValue = generateTxValue(filesSave);
                filesSave.setTx(txValue);
            } catch (JsonProcessingException e) {
                throw new RuntimeException("Failed to generate tx value", e);
            }

            // 해시 값 생성
            filesSave.generateHashValue();

            // 데이터베이스에 엔티티 저장
            FilesSave savedEntity = filesSaveRepository.save(filesSave);
            savedEntities.add(savedEntity);

        }
        return savedEntities;

    }



    /**
     * 트랜잭션 값을 생성하는 메서드입니다.
     * FilesSave 엔티티의 데이터를 기반으로 JSON 형식의 트랜잭션 값을 생성합니다.
     *
     * @param filesSave 트랜잭션 값을 생성할 FilesSave 엔티티
     * @return 트랜잭션 값 (JSON 형식)
     * @throws JsonProcessingException JSON 처리 중 발생하는 예외
     */

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

        // JSON 형식으로 변환하여 반환
        return objectMapper.writeValueAsString(txValueMap);
    }

}
