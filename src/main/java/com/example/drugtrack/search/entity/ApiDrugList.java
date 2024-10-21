package com.example.drugtrack.search.entity;

import jakarta.persistence.*;
/**
 * ApiDrugList 엔티티 클래스는 의약품 정보를 담는 데이터베이스 테이블 'api_drug_list'와 매핑됩니다.
 * 이 클래스는 의약품 관련 다양한 속성을 포함하며, JPA를 통해 자동으로 데이터베이스에 매핑됩니다.
 */
@Entity
@Table(name = "api_drug_list")
public class ApiDrugList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 기본 키, 자동 생성되는 id 값


    @Column(name = "ITEM_SEQ", length = 255)
    private String itemSeq; // 의약품의 고유 식별 번호

    @Column(name = "ITEM_NAME", columnDefinition = "TEXT")
    private String itemName; // 의약품 이름

    @Column(name = "ENTP_NAME", length = 255)
    private String entpName; // 제조사 이름

    @Column(name = "ITEM_PERMIT_DATE", length = 255)
    private String itemPermitDate;  // 허가 날짜

    @Column(name = "CNSGN_MANUF", length = 255)
    private String cnsignManuf; // 수탁 제조사

    @Column(name = "ETC_OTC_CODE", length = 255)
    private String etcOtcCode; // 일반/전문의약품 구분 코드

    @Column(name = "CHART", columnDefinition = "TEXT")
    private String chart; // 의약품 모양 정보

    @Column(name = "BAR_CODE", columnDefinition = "TEXT")
    private String barCode; // 바코드 정보

    @Column(name = "MATERIAL_NAME", columnDefinition = "TEXT")
    private String materialName; // 주요 성분명

    @Column(name = "EE_DOC_ID", columnDefinition = "TEXT")
    private String eeDocId; // 효능효과 문서 ID

    @Column(name = "UD_DOC_ID", columnDefinition = "TEXT")
    private String udDocId; // 용법용량 문서 ID

    @Column(name = "NB_DOC_ID", columnDefinition = "TEXT")
    private String nbDocId; // 주의사항 문서 ID

    @Column(name = "INSERT_FILE", columnDefinition = "TEXT")
    private String insertFile; // 첨부 파일

    @Column(name = "STORAGE_METHOD", columnDefinition = "TEXT")
    private String storageMethod; // 보관 방법

    @Column(name = "VALID_TERM", columnDefinition = "TEXT")
    private String validTerm; // 유효기간

    @Column(name = "REEXAM_TARGET", columnDefinition = "TEXT")
    private String reexamTarget;  // 재심사 대상 여부

    @Column(name = "REEXAM_DATE", columnDefinition = "TEXT")
    private String reexamDate;  // 재심사 날짜

    @Column(name = "PACK_UNIT", columnDefinition = "TEXT")
    private String packUnit; // 포장 단위

    @Column(name = "EDI_CODE", columnDefinition = "TEXT")
    private String ediCode; // EDI 코드

    @Column(name = "DOC_TEXT", columnDefinition = "TEXT")
    private String docText; // 문서 텍스트

    @Column(name = "PERMIT_KIND_NAME", columnDefinition = "TEXT")
    private String permitKindName; // 허가 종류명

    @Column(name = "ENTP_NO", columnDefinition = "TEXT")
    private String entpNo; // 제조사 번호

    @Column(name = "MAKE_MATERIAL_FLAG", columnDefinition = "TEXT")
    private String makeMaterialFlag; // 제조 재료 플래그

    @Column(name = "NEWDRUG_CLASS_NAME", columnDefinition = "TEXT")
    private String newdrugClassName; // 신약 분류명

    @Column(name = "INDUTY_TYPE", columnDefinition = "TEXT")
    private String indutyType; // 산업 유형

    @Column(name = "CANCEL_DATE", columnDefinition = "TEXT")
    private String cancelDate; // 취소 날짜

    @Column(name = "CANCEL_NAME", columnDefinition = "TEXT")
    private String cancelName; // 취소 이유

    @Column(name = "CHANGE_DATE", columnDefinition = "TEXT")
    private String changeDate; // 변경 날짜

    @Column(name = "NARCOTIC_KIND_CODE", columnDefinition = "TEXT")
    private String narcoticKindCode; // 마약류 구분 코드

    @Column(name = "GBN_NAME", columnDefinition = "TEXT")
    private String gbnName; // 분류명

    @Column(name = "TOTAL_CONTENT", columnDefinition = "TEXT")
    private String totalContent; // 총 용량

    @Column(name = "EE_DOC_DATA", columnDefinition = "TEXT")
    private String eeDocData;  // 효능효과 문서 데이터

    @Column(name = "UD_DOC_DATA", columnDefinition = "TEXT")
    private String udDocData; // 용법용량 문서 데이터

    @Column(name = "NB_DOC_DATA", columnDefinition = "TEXT")
    private String nbDocData; // 주의사항 문서 데이터

    @Column(name = "PN_DOC_DATA", columnDefinition = "TEXT")
    private String pnDocData; // 제품 설명 문서 데이터

    @Column(name = "MAIN_ITEM_INGR", columnDefinition = "TEXT")
    private String mainItemIngr; // 주요 성분

    @Column(name = "INGR_NAME", columnDefinition = "TEXT")
    private String ingrName; // 성분명

    @Column(name = "ATC_CODE", columnDefinition = "TEXT")
    private String atcCode; // ATC 코드

    @Column(name = "ITEM_ENG_NAME", columnDefinition = "TEXT")
    private String itemEngName; // 의약품 영어 이름

    @Column(name = "ENTP_ENG_NAME", columnDefinition = "TEXT")
    private String entpEngName; // 제조사 영어 이름

    @Column(name = "MAIN_INGR_ENG", columnDefinition = "TEXT")
    private String mainIngrEng; // 주요 성분 영어명

    @Column(name = "BIZRNO", length = 255)
    private String bizrno; // 사업자 등록 번호

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getItemSeq() {
        return itemSeq;
    }

    public void setItemSeq(String itemSeq) {
        this.itemSeq = itemSeq;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getEntpName() {
        return entpName;
    }

    public void setEntpName(String entpName) {
        this.entpName = entpName;
    }

    public String getItemPermitDate() {
        return itemPermitDate;
    }

    public void setItemPermitDate(String itemPermitDate) {
        this.itemPermitDate = itemPermitDate;
    }

    public String getCnsignManuf() {
        return cnsignManuf;
    }

    public void setCnsignManuf(String cnsignManuf) {
        this.cnsignManuf = cnsignManuf;
    }

    public String getEtcOtcCode() {
        return etcOtcCode;
    }

    public void setEtcOtcCode(String etcOtcCode) {
        this.etcOtcCode = etcOtcCode;
    }

    public String getChart() {
        return chart;
    }

    public void setChart(String chart) {
        this.chart = chart;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public String getEeDocId() {
        return eeDocId;
    }

    public void setEeDocId(String eeDocId) {
        this.eeDocId = eeDocId;
    }

    public String getUdDocId() {
        return udDocId;
    }

    public void setUdDocId(String udDocId) {
        this.udDocId = udDocId;
    }

    public String getNbDocId() {
        return nbDocId;
    }

    public void setNbDocId(String nbDocId) {
        this.nbDocId = nbDocId;
    }

    public String getInsertFile() {
        return insertFile;
    }

    public void setInsertFile(String insertFile) {
        this.insertFile = insertFile;
    }

    public String getStorageMethod() {
        return storageMethod;
    }

    public void setStorageMethod(String storageMethod) {
        this.storageMethod = storageMethod;
    }

    public String getValidTerm() {
        return validTerm;
    }

    public void setValidTerm(String validTerm) {
        this.validTerm = validTerm;
    }

    public String getReexamTarget() {
        return reexamTarget;
    }

    public void setReexamTarget(String reexamTarget) {
        this.reexamTarget = reexamTarget;
    }

    public String getReexamDate() {
        return reexamDate;
    }

    public void setReexamDate(String reexamDate) {
        this.reexamDate = reexamDate;
    }

    public String getPackUnit() {
        return packUnit;
    }

    public void setPackUnit(String packUnit) {
        this.packUnit = packUnit;
    }

    public String getEdiCode() {
        return ediCode;
    }

    public void setEdiCode(String ediCode) {
        this.ediCode = ediCode;
    }

    public String getDocText() {
        return docText;
    }

    public void setDocText(String docText) {
        this.docText = docText;
    }

    public String getPermitKindName() {
        return permitKindName;
    }

    public void setPermitKindName(String permitKindName) {
        this.permitKindName = permitKindName;
    }

    public String getEntpNo() {
        return entpNo;
    }

    public void setEntpNo(String entpNo) {
        this.entpNo = entpNo;
    }

    public String getMakeMaterialFlag() {
        return makeMaterialFlag;
    }

    public void setMakeMaterialFlag(String makeMaterialFlag) {
        this.makeMaterialFlag = makeMaterialFlag;
    }

    public String getNewdrugClassName() {
        return newdrugClassName;
    }

    public void setNewdrugClassName(String newdrugClassName) {
        this.newdrugClassName = newdrugClassName;
    }

    public String getIndutyType() {
        return indutyType;
    }

    public void setIndutyType(String indutyType) {
        this.indutyType = indutyType;
    }

    public String getCancelDate() {
        return cancelDate;
    }

    public void setCancelDate(String cancelDate) {
        this.cancelDate = cancelDate;
    }

    public String getCancelName() {
        return cancelName;
    }

    public void setCancelName(String cancelName) {
        this.cancelName = cancelName;
    }

    public String getChangeDate() {
        return changeDate;
    }

    public void setChangeDate(String changeDate) {
        this.changeDate = changeDate;
    }

    public String getNarcoticKindCode() {
        return narcoticKindCode;
    }

    public void setNarcoticKindCode(String narcoticKindCode) {
        this.narcoticKindCode = narcoticKindCode;
    }

    public String getGbnName() {
        return gbnName;
    }

    public void setGbnName(String gbnName) {
        this.gbnName = gbnName;
    }

    public String getTotalContent() {
        return totalContent;
    }

    public void setTotalContent(String totalContent) {
        this.totalContent = totalContent;
    }

    public String getEeDocData() {
        return eeDocData;
    }

    public void setEeDocData(String eeDocData) {
        this.eeDocData = eeDocData;
    }

    public String getUdDocData() {
        return udDocData;
    }

    public void setUdDocData(String udDocData) {
        this.udDocData = udDocData;
    }

    public String getNbDocData() {
        return nbDocData;
    }

    public void setNbDocData(String nbDocData) {
        this.nbDocData = nbDocData;
    }

    public String getPnDocData() {
        return pnDocData;
    }

    public void setPnDocData(String pnDocData) {
        this.pnDocData = pnDocData;
    }

    public String getMainItemIngr() {
        return mainItemIngr;
    }

    public void setMainItemIngr(String mainItemIngr) {
        this.mainItemIngr = mainItemIngr;
    }

    public String getIngrName() {
        return ingrName;
    }

    public void setIngrName(String ingrName) {
        this.ingrName = ingrName;
    }

    public String getAtcCode() {
        return atcCode;
    }

    public void setAtcCode(String atcCode) {
        this.atcCode = atcCode;
    }

    public String getItemEngName() {
        return itemEngName;
    }

    public void setItemEngName(String itemEngName) {
        this.itemEngName = itemEngName;
    }

    public String getEntpEngName() {
        return entpEngName;
    }

    public void setEntpEngName(String entpEngName) {
        this.entpEngName = entpEngName;
    }

    public String getMainIngrEng() {
        return mainIngrEng;
    }

    public void setMainIngrEng(String mainIngrEng) {
        this.mainIngrEng = mainIngrEng;
    }

    public String getBizrno() {
        return bizrno;
    }

    public void setBizrno(String bizrno) {
        this.bizrno = bizrno;
    }

    // Getters and Setters 생략
}
