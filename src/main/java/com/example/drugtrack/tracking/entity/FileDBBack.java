package com.example.drugtrack.tracking.entity;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Entity
@Data
public class FileDBBack {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String startCompanyRegNumber;  // 출고업체 사업장등록번호
    private String startCompanyName;  // 출고업체명
    private String endCompanyRegNumber;  // 대상업체 사업자등록번호 (엑셀에 없음, 다른 API에서 사용)
    private String endCompanyName;  // 대상업체명 (엑셀에 없음, 다른 API에서 사용)
    private String deliveryType;  // 입고, 출고 구분값
    private String deliveryDate; // 입출고일자
    private String productName;   // 제품명
    private String gs1Code;   // GTIN14
    private String mfNumber;  // 제조번호
    private String expDate;  // 유효일자
    private String barcodeData;   // 바코드 데이터 추가
    private String aggData;   // Aggregation 정보
    private String serialNumbers;   // 시리얼 번호
    private String apiKey;

    @Schema(hidden = true)
    @Column(name = "hash_code", nullable = false)
    private String hashCode;

    @Schema(hidden = true)
    @Column(name = "tx", length = 500, nullable = false)
    private String tx;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getBarcodeData() {
        return barcodeData;
    }

    public void setBarcodeData(String barcodeData) {
        this.barcodeData = barcodeData;
    }

    public String getAggData() {
        return aggData;
    }

    public void setAggData(String aggData) {
        this.aggData = aggData;
    }

    public String getSerialNumbers() {
        return serialNumbers;
    }

    public void setSerialNumbers(String serialNumbers) {
        this.serialNumbers = serialNumbers;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getHashCode() {
        return hashCode;
    }

    public void setHashCode(String hashCode) {
        this.hashCode = hashCode;
    }

    public String getTx() {
        return tx;
    }

    public void setTx(String tx) {
        this.tx = tx;
    }

    public void generateHashValue() {
        String combinedData = this.tx;

        if (combinedData == null) {
            throw new RuntimeException("tx 값이 null입니다. 해시 값을 생성할 수 없습니다.");
        }

        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(combinedData.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : hashBytes) {
                sb.append(String.format("%02x", b));
            }
            this.hashCode = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("해시값 생성이 실패했습니다.", e);
        }
    }
}
