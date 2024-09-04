package com.example.drugtrack.controller;

import com.example.drugtrack.service.ExcelDBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ExcelDBController {

    @Autowired
    private ExcelDBService excelDBService;

    @PostMapping("/excelDB")
    public ResponseEntity<Map<String, String>> triggerExcelProcessing() {
        Map<String, String> response = new HashMap<>();

        // 미리 정의된 파일 경로
        String filePathString = "C:/Users/ibiz/Documents/일련번호출하정보_Sample_20240723.xlsx";
        Path filePath = Paths.get(filePathString);

        // 파일이 존재하는지 확인
        File file = filePath.toFile();
        if (!file.exists() || !file.isFile() || !file.getName().endsWith(".xlsx")) {
            response.put("result", "N");
            response.put("message", "엑셀 파일이 존재하지 않거나 올바른 파일이 아닙니다.");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        // 파일 내용을 파싱하고 DB에 저장
        boolean isStored = excelDBService.processAndStoreFile(filePath);
        if (!isStored) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        // 성공 응답
        response.put("result", "Y");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
