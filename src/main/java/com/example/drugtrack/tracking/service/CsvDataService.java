package com.example.drugtrack.tracking.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CsvDataService {

    private List<String[]> csvData = new ArrayList<>();  // 메모리에 저장된 CSV 데이터

    // CSV 데이터를 메모리에 저장
    public void storeCsvData(List<String[]> data) {
        this.csvData = data;
    }

    // 메모리에 저장된 CSV 데이터를 반환
    public List<String[]> getCsvData() {
        return this.csvData;
    }

    // CSV 데이터를 초기화 (원하는 경우에 따라 호출 가능)
    public void clearCsvData() {
        this.csvData.clear();
    }
}
