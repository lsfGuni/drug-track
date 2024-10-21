package com.example.drugtrack.tracking.dto;

import com.example.drugtrack.tracking.entity.ApiDrugResponse;

import java.util.List;
/**
 * ApiDrugResponseWrapper는 API 응답 데이터를 감싸는 DTO(Data Transfer Object)입니다.
 * 의약품 추적 정보 조회 시 결과와 데이터를 함께 반환하기 위해 사용됩니다.
 */
public class ApiDrugResponseWrapper {

    private String result;   // 조회 결과 상태 ('Y' 또는 'N')
    private List<ApiDrugResponse> data;  // 의약품 데이터 리스트

    /**
     * 생성자: 결과 상태와 데이터를 받아서 ApiDrugResponseWrapper 객체를 생성합니다.
     *
     * @param result 조회 결과 상태 ('Y'는 성공, 'N'은 실패)
     * @param data 조회된 의약품 데이터 리스트
     */
    public ApiDrugResponseWrapper(String result, List<ApiDrugResponse> data) {
        this.result = result;
        this.data = data;
    }

    // getters and setters
    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public List<ApiDrugResponse> getData() {
        return data;
    }

    public void setData(List<ApiDrugResponse> data) {
        this.data = data;
    }
}
