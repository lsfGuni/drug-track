package com.example.drugtrack.tracking.entity;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
/**
 * FilesSave 엔티티는 의약품의 입출고 데이터를 관리하는 클래스입니다.
 * 데이터베이스의 `drug_tracking_data` 테이블과 매핑됩니다.
 */
@Entity
@Data
@Table(name = "drug_tracking_data")
public class FilesSave {


    @Id
    @Schema(hidden = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seq")
    private Long seq;   // 고유 식별자

    @Column(name = "start_company_name")
    private String startCompanyName;  // 출고업체명

    @Column(name = "start_company_reg_number")
    private String startCompanyRegNumber;  // 출고업체 사업장등록번호

    @Column(name = "end_company_reg_number")
    private String endCompanyRegNumber;  // 대상업체 사업자등록번호

    @Column(name = "end_company_name")
    private String endCompanyName;  // 대상업체명

    @Column(name = "delivery_type")
    private String deliveryType;  // 입고, 출고 구분값

    @Column(name = "delivery_date")
    private String deliveryDate; // 입출고일자

    @Column(name = "product_name")
    private String productName;   // 제품명

    @Column(name = "gs1_code")
    private String gs1Code;   // GTIN14

    @Column(name = "mf_number")
    private String mfNumber;  // 제조번호

    @Column(name = "exp_date")
    private String expDate;  // 유효일자

    @Column(name = "barcode_data")
    private String barcodeData;   // 의약품 바코드 데이터

    @Column(name = "agg_data")
    private String aggData;   // Aggregation 정보

    @Column(name = "serial_number")
    private String serialNumber;   // 시리얼 번호

    @Column(name = "api_key")
    private String apiKey;  // API 키

    @Schema(hidden = true)
    @Column(name = "hash_code", nullable = false)
    private String hashCode;    // 트랜잭션 해시 값

    @Schema(hidden = true)
    @Column(name = "tx", length = 500, nullable = false)
    private String tx;  // 블록체인 트랜잭션 데이터

    @Schema(hidden = true)
    @Column(name = "auth", nullable = false)
    private String auth;     // 데이터 생성자 (자동 설정)

    /**
     * 데이터가 새로 생성될 때 호출되는 메서드.
     * auth 값을 'sys'로 설정합니다.
     */
    @PrePersist
    protected void omCreate(){
        this.auth = "sys";
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

    public Long getSeq() {
        return seq;
    }

    public void setSeq(Long seq) {
        this.seq = seq;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
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

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }


    /**
     * 트랜잭션 데이터를 기반으로 해시 값을 생성하는 메서드.
     * SHA-256 알고리즘을 사용하여 트랜잭션 데이터를 해시화합니다.
     */
    public void generateHashValue() {
        String combinedData = this.tx;

        if (combinedData == null) {
            throw new RuntimeException("tx 값이 null입니다. 해시 값을 생성할 수 없습니다.");
        }

        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(combinedData.getBytes(StandardCharsets.UTF_8));

            // 해시 값을 16진수 문자열로 변환
            StringBuilder sb = new StringBuilder();
            for (byte b : hashBytes) {
                sb.append(String.format("%02x", b));
            }
            this.hashCode = sb.toString();  // 해시 값을 저장
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("해시값 생성이 실패했습니다.", e);
        }
    }

    @Override
    public String toString() {
        return "FilesSave{" +
                "barcodeData='" + barcodeData + '\'' +
                ", startCompanyRegNumber='" + startCompanyRegNumber + '\'' +
                ", startCompanyName='" + startCompanyName + '\'' +
                ", endCompanyRegNumber='" + endCompanyRegNumber + '\'' +
                ", endCompanyName='" + endCompanyName + '\'' +
                ", deliveryType='" + deliveryType + '\'' +
                ", deliveryDate='" + deliveryDate + '\'' +
                ", productName='" + productName + '\'' +
                ", gs1Code='" + gs1Code + '\'' +
                ", mfNumber='" + mfNumber + '\'' +
                ", expDate='" + expDate + '\'' +
                ", serialNumber='" + serialNumber + '\'' +
                ", aggData='" + aggData + '\'' +
                ", apiKey='" + apiKey + '\'' +
                ", tx='" + tx + '\'' +
                ", hashCode='" + hashCode + '\'' +
                '}';
    }
}
