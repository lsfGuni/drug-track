package com.example.drugtrack.tracking.dto;
/**
 * SerialNumberInfoDTO는 의약품 추적에 필요한 시리얼 번호 관련 정보를 저장하는 DTO 클래스입니다.
 * API 호출 시 바코드 데이터, 시리얼 번호, Aggregation 데이터를 전송하는 데 사용됩니다.
 */
public class SerialNumberInfoDTO {
    private String barcodeData;  // 바코드 데이터
    private String serialNumber; // 시리얼 번호
    private String aggData; // Aggregation 정보

    public String getBarcodeData() {
        return barcodeData;
    }

    public void setBarcodeData(String barcodeData) {
        this.barcodeData = barcodeData;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getAggData() {
        return aggData;
    }

    public void setAggData(String aggData) {
        this.aggData = aggData;
    }
}
