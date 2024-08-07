package com.example.drugtrack.entity;

import jakarta.persistence.*;


@Entity
@Table(name = "api_drug_response")
public class ApiDrugResponse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ITEM_SEQ")
    private String itemSeq;

    @Column(name = "ITEM_NAME")
    private String itemName;

    @Column(name = "BAR_CODE")
    private String barCode;

    // Getters and Setters

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

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }
}
