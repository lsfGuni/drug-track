package com.example.drugtrack.dto;

import com.example.drugtrack.entity.ApiDrugResponse;

import java.util.List;

public class ApiDrugResponseWrapper {

    private String result;
    private List<ApiDrugResponse> data;

    public ApiDrugResponseWrapper(String result, List<ApiDrugResponse> data) {
        this.result = result;
        this.data = data;
    }

    // getters and setters
    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public List<ApiDrugResponse> getData() {
        return data;
    }

    public void setData(List<ApiDrugResponse> data) {
        this.data = data;
    }
}
