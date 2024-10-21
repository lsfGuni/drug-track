package com.example.drugtrack.search.dto;

import com.example.drugtrack.search.entity.ApiDrugList;

import java.util.List;
/**
 * BarcodeWrapper 클래스는 바코드를 기준으로 API에서 조회한 의약품 데이터와 결과 상태를 담는 DTO입니다.
 * 이 클래스는 API 호출 결과를 캡슐화하여 응답으로 사용됩니다.
 */
public class BarcodeWrapper {

    private String result; // API 요청 결과를 나타내는 값 (예: "Y" 또는 "N")
    private List<ApiDrugList> data; // 조회된 의약품 리스트를 담는 필드

    /**
     * BarcodeWrapper 생성자.
     * API 요청의 결과와 조회된 의약품 데이터를 초기화합니다.
     *
     * @param result API 요청의 결과 상태 (예: 성공 여부)
     * @param data 조회된 의약품 데이터 리스트
     */
    public BarcodeWrapper(String result, List<ApiDrugList> data) {
        this.result = result;
        this.data = data;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public List<ApiDrugList> getData() {
        return data;
    }

    public void setData(List<ApiDrugList> data) {
        this.data = data;
    }
}
