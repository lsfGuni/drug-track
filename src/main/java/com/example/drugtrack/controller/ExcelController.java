package com.example.drugtrack.controller;

import com.example.drugtrack.service.ExcelFileStorageService;
import jakarta.annotation.PostConstruct;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/excel-upload")
public class ExcelController {  // 새로운 컨트롤러 이름

    @PostConstruct
    public void init() {
        // 최대 배열 크기 제한을 500MB로 설정 (파일 크기 문제를 대비한 설정)
        IOUtils.setByteArrayMaxOverride(500 * 1024 * 1024); // 500MB
    }

    @Autowired
    private ExcelFileStorageService fileStorageService;

    @PostMapping
    public ResponseEntity<Map<String, String>> uploadExcelFile(@RequestParam("file") MultipartFile file) {
        Map<String, String> response = new HashMap<>();

        if (file.isEmpty()) {
            response.put("message", "파일이 비어 있습니다.");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        // 파일 저장 시도
        boolean isStored = fileStorageService.storeFile(file);
        if (!isStored) {
            response.put("message", "파일 저장에 실패했습니다.");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        try (Workbook workbook = WorkbookFactory.create(file.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0);

            // 엑셀 데이터를 처리할 로직 (필요에 따라 확장 가능)
            for (Row row : sheet) {
                for (Cell cell : row) {
                    System.out.println(getCellValueAsString(cell));
                }
            }

            // 파일 저장에 성공한 경우
            response.put("result", "Y");
            response.put("message", "파일이 성공적으로 업로드 및 처리되었습니다.");
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (IOException e) {
            e.printStackTrace();
            response.put("message", "파일을 처리하는 중 오류가 발생했습니다.");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 셀의 데이터를 문자열로 변환하는 메서드
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
