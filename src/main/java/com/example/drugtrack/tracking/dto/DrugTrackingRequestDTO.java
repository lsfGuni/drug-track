// 파일: DrugTrackingRequestDTO.java
package com.example.drugtrack.tracking.dto;

import java.util.List;

public class DrugTrackingRequestDTO {
    private String startCompanyRegNumber;
    private String startCompanyName;
    private String endCompanyRegNumber;
    private String endCompanyName;
    private String deliveryType;
    private String deliveryDate;
    private String productName;
    private String gs1Code;
    private String mfNumber;
    private String expDate;
    private String aggData;

    private List<SerialNumberInfoDTO> serialNumbers;

    // Getters and Setters


    public String getStartCompanyRegNumber() {
        return startCompanyRegNumber;
    }

    public void setStartCompanyRegNumber(String startCompanyRegNumber) {
        this.startCompanyRegNumber = startCompanyRegNumber;
    }

    public String getStartCompanyName() {
        return startCompanyName;
    }

    public void setStartCompanyName(String startCompanyName) {
        this.startCompanyName = startCompanyName;
    }

    public String getEndCompanyRegNumber() {
        return endCompanyRegNumber;
    }

    public void setEndCompanyRegNumber(String endCompanyRegNumber) {
        this.endCompanyRegNumber = endCompanyRegNumber;
    }

    public String getEndCompanyName() {
        return endCompanyName;
    }

    public void setEndCompanyName(String endCompanyName) {
        this.endCompanyName = endCompanyName;
    }

    public String getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(String deliveryType) {
        this.deliveryType = deliveryType;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getGs1Code() {
        return gs1Code;
    }

    public void setGs1Code(String gs1Code) {
        this.gs1Code = gs1Code;
    }

    public String getMfNumber() {
        return mfNumber;
    }

    public void setMfNumber(String mfNumber) {
        this.mfNumber = mfNumber;
    }

    public String getExpDate() {
        return expDate;
    }

    public void setExpDate(String expDate) {
        this.expDate = expDate;
    }
    
    public String getAggData() {
        return aggData;
    }

    public void setAggData(String aggData) {
        this.aggData = aggData;
    }

    public List<SerialNumberInfoDTO> getSerialNumbers() {
        return serialNumbers;
    }

    public void setSerialNumbers(List<SerialNumberInfoDTO> serialNumbers) {
        this.serialNumbers = serialNumbers;
    }
}
