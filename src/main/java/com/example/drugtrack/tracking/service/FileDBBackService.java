package com.example.drugtrack.tracking.service;


import com.example.drugtrack.tracking.entity.FileDBBack;
import com.example.drugtrack.tracking.repository.FileDBBackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileDBBackService {


    @Autowired
    private FileDBBackRepository fileDBBackRepository;

    public void saveData(FileDBBack fileDBBack) {
        fileDBBackRepository.save(fileDBBack);
    }
    // CSV 데이터를 데이터베이스에 저장하는 메서드 (첫 번째 행은 건너뛰기)
    public void saveCsvDataToDB(List<String[]> csvData) {
        // 첫 번째 행은 헤더이므로 건너뜀
        for (int i = 1; i < csvData.size(); i++) {
            String[] rowData = csvData.get(i);
            FileDBBack fileDBBack = new FileDBBack();
            fileDBBack.setStartCompanyName(rowData.length > 0 ? rowData[0] : null);  // 입력업체명
            fileDBBack.setStartCompanyRegNumber(rowData.length > 1 ? rowData[1] : null);  // 입력업체사업장등록번호
            fileDBBack.setEndCompanyName(rowData.length > 2 ? rowData[2] : null);   //대상업체명
            fileDBBack.setEndCompanyRegNumber(rowData.length > 3 ? rowData[3] : null);  //대상업체사업자등록번호
            fileDBBack.setDeliveryDate(rowData.length > 2 ? rowData[4] : null);  // 출고일자
            fileDBBack.setProductName(rowData.length > 3 ? rowData[5] : null);  // 제품명
            fileDBBack.setGs1Code(rowData.length > 4 ? rowData[6] : null);  // GTIN14
            fileDBBack.setMfNumber(rowData.length > 5 ? rowData[7] : null);  // 제조번호
            fileDBBack.setExpDate(rowData.length > 6 ? rowData[8] : null);  // 유효일자
            fileDBBack.setSerialNumbers(rowData.length > 7 ? rowData[9] : null);  // 시리얼 번호
            fileDBBack.setBarcodeData(rowData.length > 8 ? rowData[10] : null);  // 바코드 데이터
            fileDBBack.setAggData(rowData.length > 9 ? rowData[11] : null);  // Aggregation 정보

            // 데이터베이스에 저장
            fileDBBackRepository.save(fileDBBack);
        }
    }

    // XLSX 데이터를 데이터베이스에 저장하는 메서드 (첫 번째 행은 건너뛰기)
    public void saveExcelDataToDB(List<List<String>> excelData) {
        // 첫 번째 행은 헤더이므로 건너뜀
        for (int i = 1; i < excelData.size(); i++) {
            List<String> rowData = excelData.get(i);
            FileDBBack fileDBBack = new FileDBBack();
            fileDBBack.setStartCompanyName(rowData.size() > 0 ? rowData.get(0) : null);  // 입력업체명
            fileDBBack.setStartCompanyRegNumber(rowData.size() > 1 ? rowData.get(1) : null);  // 입력업체사업자등록번호
            fileDBBack.setEndCompanyName(rowData.size() > 2 ? rowData.get(2) : null); //대상업체명
            fileDBBack.setEndCompanyRegNumber(rowData.size() > 3 ? rowData.get(3) : null); //대상업체사업자등록번호
            fileDBBack.setDeliveryDate(rowData.size() > 2 ? rowData.get(4) : null);  // 출고일자
            fileDBBack.setProductName(rowData.size() > 3 ? rowData.get(5) : null);  // 제품명
            fileDBBack.setGs1Code(rowData.size() > 4 ? rowData.get(6) : null);  // GTIN14
            fileDBBack.setMfNumber(rowData.size() > 5 ? rowData.get(7) : null);  // 제조번호
            fileDBBack.setExpDate(rowData.size() > 6 ? rowData.get(8) : null);  // 유효일자
            fileDBBack.setSerialNumbers(rowData.size() > 7 ? rowData.get(9) : null);  // 시리얼 번호
            fileDBBack.setBarcodeData(rowData.size() > 8 ? rowData.get(10) : null);  // 바코드 데이터
            fileDBBack.setAggData(rowData.size() > 9 ? rowData.get(11) : null);  // Aggregation 정보

            // 데이터베이스에 저장
            fileDBBackRepository.save(fileDBBack);
        }
    }
}
