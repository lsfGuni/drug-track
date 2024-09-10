package com.example.drugtrack.tracking.dto;

import com.example.drugtrack.tracking.entity.ApiDrugResponse;

import java.util.List;


public class DrugTrackingListRequest {

    private List<ApiDrugResponse> drugTrackingList;

    public List<ApiDrugResponse> getDrugTrackingList() {
        return drugTrackingList;
    }

    public void setDrugTrackingList(List<ApiDrugResponse> drugTrackingList) {
        this.drugTrackingList = drugTrackingList;
    }
}
