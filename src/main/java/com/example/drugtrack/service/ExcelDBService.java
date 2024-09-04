package com.example.drugtrack.service;

import com.example.drugtrack.entity.FileDB;
import com.example.drugtrack.repository.FileDBRepository;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Service
public class ExcelDBService {

    @Autowired
    private FileDBRepository fileDBRepository;

    public boolean processAndStoreFile(Path filePath) {
        try (FileInputStream fis = new FileInputStream(filePath.toFile());
             Workbook workbook = WorkbookFactory.create(fis)) {
            Sheet sheet = workbook.getSheetAt(0);
            List<FileDB> fileDBS = new ArrayList<>();

            for (Row row : sheet) {
                if (row.getRowNum() == 0) {
                    continue; // 첫 번째 행은 헤더이므로 건너뜀
                }

                FileDB fileDB = new FileDB();
                fileDB.setStartCompanyRegNumber(getCellValueAsString(row.getCell(1)));  // 거래처사업장등록번호
                fileDB.setStartCompanyName(getCellValueAsString(row.getCell(0)));  // 거래처명
                fileDB.setDeliveryType("0");  // 입고, 출고 구분값 (출고로 고정)
                fileDB.setDeliveryDate(getCellValueAsString(row.getCell(2)));  // 출고일자
                fileDB.setProductName(getCellValueAsString(row.getCell(3)));  // 제품명
                fileDB.setGs1Code(getCellValueAsString(row.getCell(4)));  // GTIN14
                fileDB.setMfNumber(getCellValueAsString(row.getCell(5)));  // 제조번호
                fileDB.setExpDate(getCellValueAsString(row.getCell(6)));  // 유효일자
                fileDB.setSerialNumbers(getCellValueAsString(row.getCell(7)));  // 일련번호
                fileDB.setBarcodeData(getCellValueAsString(row.getCell(8)));  // 바코드데이터
                fileDB.setAggData(getCellValueAsString(row.getCell(9)));  // Aggregation정보

                fileDBS.add(fileDB);
            }

            // 모든 행의 데이터를 데이터베이스에 저장
            fileDBRepository.saveAll(fileDBS);
            return true;

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private String getCellValueAsString(Cell cell) {
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue().toString();
                } else {
                    return String.valueOf(cell.getNumericCellValue());
                }
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            case BLANK:
                return "";
            default:
                return "";
        }
    }
}
