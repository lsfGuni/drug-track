package com.example.drugtrack.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ExcelDataService {

    private List<List<String>> excelData = new ArrayList<>();  // 메모리에 저장될 엑셀 데이터

    // 엑셀 데이터를 메모리에 저장하는 메서드 (타입 변경)
    public void storeExcelData(List<List<String>> data) {
        this.excelData = data;  // 데이터를 메모리에 저장
    }

    // 메모리에 저장된 엑셀 데이터를 반환하는 메서드
    public List<List<String>> getExcelData() {
        return this.excelData;
    }
}
