package com.example.drugtrack.tracking.service;


import com.example.drugtrack.tracking.entity.FileDBBack;
import com.example.drugtrack.tracking.repository.FileDBBackRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class FileDBBackService {

    @Autowired
    private FileDBBackRepository fileDBBackRepository;

    @Transactional
    public void saveCsvDataToDB(List<String[]> csvData, String apiKey) {
        // 첫 번째 행은 헤더이므로 건너뜀
        for (int i = 1; i < csvData.size(); i++) {
            String[] rowData = csvData.get(i);
            FileDBBack fileDBBack = new FileDBBack();

            // 필드 설정
            fileDBBack.setBarcodeData(rowData.length > 0 ? rowData[0] : null);
            fileDBBack.setStartCompanyName(rowData.length > 1 ? rowData[1] : null);
            fileDBBack.setStartCompanyRegNumber(rowData.length > 2 ? rowData[2] : null);
            fileDBBack.setEndCompanyRegNumber(rowData.length > 3 ? rowData[3] : null);
            fileDBBack.setEndCompanyName(rowData.length > 4 ? rowData[4] : null);
            fileDBBack.setDeliveryType(rowData.length > 5 ? rowData[5] : null);
            fileDBBack.setDeliveryDate(rowData.length > 6 ? rowData[6] : null);
            fileDBBack.setProductName(rowData.length > 7 ? rowData[7] : null);
            fileDBBack.setGs1Code(rowData.length > 8 ? rowData[8] : null);
            fileDBBack.setMfNumber(rowData.length > 9 ? rowData[9] : null);
            fileDBBack.setExpDate(rowData.length > 10 ? rowData[10] : null);
            fileDBBack.setSerialNumbers(rowData.length > 11 ? rowData[11] : null);
            fileDBBack.setAggData(rowData.length > 12 ? rowData[12] : null);







            // 여기에서 apiKey를 설정
            fileDBBack.setApiKey(apiKey);

            // 데이터베이스에 저장
            fileDBBackRepository.save(fileDBBack);
        }
    }

    // XLSX 처리 메서드도 동일하게 수정
    public void saveExcelDataToDB(List<List<String>> excelData) {
        for (int i = 1; i < excelData.size(); i++) {
            List<String> rowData = excelData.get(i);
            FileDBBack fileDBBack = new FileDBBack();
            fileDBBack.setStartCompanyName(rowData.size() > 0 ? rowData.get(0) : null);
            fileDBBack.setStartCompanyRegNumber(rowData.size() > 1 ? rowData.get(1) : null);
            fileDBBack.setEndCompanyName(rowData.size() > 2 ? rowData.get(2) : null);
            fileDBBack.setEndCompanyRegNumber(rowData.size() > 3 ? rowData.get(3) : null);
            fileDBBack.setDeliveryDate(rowData.size() > 4 ? rowData.get(4) : null);
            fileDBBack.setProductName(rowData.size() > 5 ? rowData.get(5) : null);
            fileDBBack.setGs1Code(rowData.size() > 6 ? rowData.get(6) : null);
            fileDBBack.setMfNumber(rowData.size() > 7 ? rowData.get(7) : null);
            fileDBBack.setExpDate(rowData.size() > 8 ? rowData.get(8) : null);
            fileDBBack.setSerialNumbers(rowData.size() > 9 ? rowData.get(9) : null);
            fileDBBack.setBarcodeData(rowData.size() > 10 ? rowData.get(10) : null);
            fileDBBack.setAggData(rowData.size() > 11 ? rowData.get(11) : null);
            fileDBBack.setDeliveryType(rowData.size() > 12 ? rowData.get(12) : null);

            fileDBBackRepository.save(fileDBBack);
        }
    }
}
