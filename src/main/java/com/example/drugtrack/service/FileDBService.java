package com.example.drugtrack.service;

import com.example.drugtrack.entity.FileDB;
import com.example.drugtrack.repository.FileDBRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileDBService {

    @Autowired
    private FileDBRepository fileDBRepository;

    // CSV 데이터를 데이터베이스에 저장하는 메서드 (첫 번째 행은 건너뛰기)
    public void saveCsvDataToDB(List<String[]> csvData) {
        // 첫 번째 행은 헤더이므로 건너뜀
        for (int i = 1; i < csvData.size(); i++) {
            String[] rowData = csvData.get(i);
            FileDB fileDB = new FileDB();
            fileDB.setStartCompanyName(rowData.length > 0 ? rowData[0] : null);  // 거래처명
            fileDB.setStartCompanyRegNumber(rowData.length > 1 ? rowData[1] : null);  // 거래처사업장등록번호
            fileDB.setDeliveryDate(rowData.length > 2 ? rowData[2] : null);  // 출고일자
            fileDB.setProductName(rowData.length > 3 ? rowData[3] : null);  // 제품명
            fileDB.setGs1Code(rowData.length > 4 ? rowData[4] : null);  // GTIN14
            fileDB.setMfNumber(rowData.length > 5 ? rowData[5] : null);  // 제조번호
            fileDB.setExpDate(rowData.length > 6 ? rowData[6] : null);  // 유효일자
            fileDB.setSerialNumbers(rowData.length > 7 ? rowData[7] : null);  // 시리얼 번호
            fileDB.setBarcodeData(rowData.length > 8 ? rowData[8] : null);  // 바코드 데이터
            fileDB.setAggData(rowData.length > 9 ? rowData[9] : null);  // Aggregation 정보

            // 데이터베이스에 저장
            fileDBRepository.save(fileDB);
        }
    }

    // XLSX 데이터를 데이터베이스에 저장하는 메서드 (첫 번째 행은 건너뛰기)
    public void saveExcelDataToDB(List<List<String>> excelData) {
        // 첫 번째 행은 헤더이므로 건너뜀
        for (int i = 1; i < excelData.size(); i++) {
            List<String> rowData = excelData.get(i);
            FileDB fileDB = new FileDB();
            fileDB.setStartCompanyName(rowData.size() > 0 ? rowData.get(0) : null);  // 거래처명
            fileDB.setStartCompanyRegNumber(rowData.size() > 1 ? rowData.get(1) : null);  // 거래처사업장등록번호
            fileDB.setDeliveryDate(rowData.size() > 2 ? rowData.get(2) : null);  // 출고일자
            fileDB.setProductName(rowData.size() > 3 ? rowData.get(3) : null);  // 제품명
            fileDB.setGs1Code(rowData.size() > 4 ? rowData.get(4) : null);  // GTIN14
            fileDB.setMfNumber(rowData.size() > 5 ? rowData.get(5) : null);  // 제조번호
            fileDB.setExpDate(rowData.size() > 6 ? rowData.get(6) : null);  // 유효일자
            fileDB.setSerialNumbers(rowData.size() > 7 ? rowData.get(7) : null);  // 시리얼 번호
            fileDB.setBarcodeData(rowData.size() > 8 ? rowData.get(8) : null);  // 바코드 데이터
            fileDB.setAggData(rowData.size() > 9 ? rowData.get(9) : null);  // Aggregation 정보

            // 데이터베이스에 저장
            fileDBRepository.save(fileDB);
        }
    }
}
