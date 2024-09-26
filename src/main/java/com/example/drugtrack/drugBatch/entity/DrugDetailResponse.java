package com.example.drugtrack.drugBatch.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "api_drug_batch_list")
public class DrugDetailResponse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ITEM_SEQ", columnDefinition = "TEXT")
    private String itemSeq;

    @Column(name = "ITEM_NAME", columnDefinition = "TEXT")
    private String itemName;

    @Column(name = "ENTP_NAME", columnDefinition = "TEXT")
    private String entpName;

    @Column(name = "ITEM_PERMIT_DATE", columnDefinition = "TEXT")
    private String itemPermitDate;

    @Column(name = "CNSGN_MANUF", columnDefinition = "TEXT")
    private String cnsngManuf;

    @Column(name = "ETC_OTC_CODE", columnDefinition = "TEXT")
    private String etcOtcCode;

    @Column(name = "CHART", columnDefinition = "TEXT")
    private String chart;

    @Column(name = "BAR_CODE", columnDefinition = "TEXT")
    private String barCode;

    @Column(name = "MATERIAL_NAME", columnDefinition = "TEXT")
    private String materialName;

    @Column(name = "EE_DOC_ID", columnDefinition = "TEXT")
    private String eeDocId;

    @Column(name = "UD_DOC_ID", columnDefinition = "TEXT")
    private String udDocId;

    @Column(name = "NB_DOC_ID", columnDefinition = "TEXT")
    private String nbDocId;

    @Column(name = "INSERT_FILE", columnDefinition = "TEXT")
    private String insertFile;

    @Column(name = "STORAGE_METHOD", columnDefinition = "TEXT")
    private String storageMethod;

    @Column(name = "VALID_TERM", columnDefinition = "TEXT")
    private String validTerm;

    @Column(name = "REEXAM_TARGET", columnDefinition = "TEXT")
    private String reexamTarget;

    @Column(name = "REEXAM_DATE", columnDefinition = "TEXT")
    private String reexamDate;

    @Column(name = "PACK_UNIT", columnDefinition = "TEXT")
    private String packUnit;

    @Column(name = "EDI_CODE", columnDefinition = "TEXT")
    private String ediCode;

    @Column(name = "DOC_TEXT", columnDefinition = "TEXT")
    private String docText;

    @Column(name = "PERMIT_KIND_NAME", columnDefinition = "TEXT")
    private String permitKindName;

    @Column(name = "ENTP_NO", columnDefinition = "TEXT")
    private String entpNo;

    @Column(name = "MAKE_MATERIAL_FLAG", columnDefinition = "TEXT")
    private String makeMaterialFlag;

    @Column(name = "NEWDRUG_CLASS_NAME", columnDefinition = "TEXT")
    private String newdrugClassName;

    @Column(name = "INDUTY_TYPE", columnDefinition = "TEXT")
    private String indutyType;

    @Column(name = "CANCEL_DATE", columnDefinition = "TEXT")
    private String cancelDate;

    @Column(name = "CANCEL_NAME", columnDefinition = "TEXT")
    private String cancelName;

    @Column(name = "CHANGE_DATE", columnDefinition = "TEXT")
    private String changeDate;

    @Column(name = "NARCOTIC_KIND_CODE", columnDefinition = "TEXT")
    private String narcoticKindCode;

    @Column(name = "GBN_NAME", columnDefinition = "TEXT")
    private String gbnName;

    @Column(name = "TOTAL_CONTENT", columnDefinition = "TEXT")
    private String totalContent;

    @Column(name = "EE_DOC_DATA", columnDefinition = "TEXT")
    private String eeDocData;

    @Column(name = "UD_DOC_DATA", columnDefinition = "TEXT")
    private String udDocData;

    @Column(name = "NB_DOC_DATA", columnDefinition = "TEXT")
    private String nbDocData;

    @Column(name = "PN_DOC_DATA", columnDefinition = "TEXT")
    private String pnDocData;

    @Column(name = "MAIN_ITEM_INGR", columnDefinition = "TEXT")
    private String mainItemIngr;

    @Column(name = "INGR_NAME", columnDefinition = "TEXT")
    private String ingrName;

    @Column(name = "ATC_CODE", columnDefinition = "TEXT")
    private String atcCode;

    @Column(name = "ITEM_ENG_NAME", columnDefinition = "TEXT")
    private String itemEngName;

    @Column(name = "ENTP_ENG_NAME", columnDefinition = "TEXT")
    private String entpEngName;

    @Column(name = "MAIN_INGR_ENG", columnDefinition = "TEXT")
    private String mainIngrEng;

    @Column(name = "BIZRNO", columnDefinition = "TEXT")
    private String bizrno;

    // Getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getItemSeq() {
        return this.itemSeq;
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

    public String getCnsngManuf() {
        return cnsngManuf;
    }

    public void setCnsngManuf(String cnsngManuf) {
        this.cnsngManuf = cnsngManuf;
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
}
