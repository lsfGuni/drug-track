package com.example.drugtrack.search.dto;

public class UpdateResponse {
    private String result;

    public UpdateResponse(String result) {
        this.result = result;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
