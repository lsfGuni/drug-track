package com.example.drugtrack.search.dto;

import com.example.drugtrack.search.entity.ApiDrugList;

import java.util.List;

public class BarcodeWrapper {

    private String result;
    private List<ApiDrugList> data;

    public BarcodeWrapper(String result, List<ApiDrugList> data) {
        this.result = result;
        this.data = data;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public List<ApiDrugList> getData() {
        return data;
    }

    public void setData(List<ApiDrugList> data) {
        this.data = data;
    }
}
