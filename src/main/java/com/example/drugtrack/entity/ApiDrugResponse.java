package com.example.drugtrack.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


@Entity
@Table(name = "drug_tracking_data")
public class ApiDrugResponse {

    @Id
    @Schema(hidden = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seq")
    private Long seq;

    @Column(name = "barcode_data", nullable = false)
    private String barcodeData;

    @Column(name = "start_company_reg_number" , nullable = false)
    private String startCompanyRegNumber;

    @Column(name = "start_company_name", nullable = false)
    private String startCompanyName = "DEFAULT_VALUE"; // 기본값 설정


    @Column(name = "end_company_reg_number" , nullable = false)
    private String endCompanyRegNumber;

    @Column(name = "end_company_name", nullable = false)
    private String endCompanyName = "DEFAULT_VALUE"; // 기본값 설정


    @Column(name = "delivery_type" , nullable = false)
    private String deliveryType;

    @Column(name = "delivery_date", nullable = false)
    private String deliveryDate;

    @Column(name = "product_name", nullable = false)
    private String productName;

    @Column(name = "gs1_code", nullable = false)
    private String gs1Code;

    @Column(name = "mf_number", nullable = false)
    private String mfNumber;

    @Column(name = "exp_date", nullable = false)
    private String expDate;

    @Column(name = "serial_number", nullable = false)
    private String serialNumber;

    @Column(name = "agg_data", nullable = false)
    private String aggData;

    @Schema(hidden = true)
    @Column(name = "tx", length = 500, nullable = false)
    private String tx;

    @Schema(hidden = true)
    @Column(name = "hash_code", nullable = false)
    private String hashCode;

    @Schema(hidden = true)
    @Column(name = "auth", nullable = false)
    private String auth;


    @PrePersist
    protected void omCreate(){
        this.auth = "sys";
    }
    // Getters and Setters


    public Long getSeq() {
        return seq;
    }

    public void setSeq(Long seq) {
        this.seq = seq;
    }

    public String getBarcodeData() {
        return barcodeData;
    }

    public void setBarcodeData(String barcodeData) {
        this.barcodeData = barcodeData;
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

    public String getTx() {
        return tx;
    }

    public void setTx(String tx) {
        this.tx = tx;
    }

    public String getHashCode() {
        return hashCode;
    }

    public void setHashCode(String hashCode) {
        this.hashCode = hashCode;
    }

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
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
