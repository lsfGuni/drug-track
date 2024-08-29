package com.example.drugtrack.dto;

import com.example.drugtrack.entity.ApiDrugResponse;
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
