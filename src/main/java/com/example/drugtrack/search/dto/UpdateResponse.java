package com.example.drugtrack.search.dto;
/**
 * UpdateResponse 클래스는 업데이트 요청에 대한 결과를 나타내는 DTO입니다.
 * API 요청의 성공 또는 실패 여부를 전달하는 간단한 응답 형식으로 사용됩니다.
 */
public class UpdateResponse {
    private String result; // 업데이트 요청의 결과 상태 (예: "Y" 또는 "N")

    public UpdateResponse(String result) {
        this.result = result;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
