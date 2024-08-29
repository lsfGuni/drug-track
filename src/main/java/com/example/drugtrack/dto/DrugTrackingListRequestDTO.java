// 파일: DrugTrackingListRequestDTO.java
package com.example.drugtrack.dto;

import java.util.List;

public class DrugTrackingListRequestDTO {
    private List<DrugTrackingRequestDTO> drugTrackingList;

    // Getters and Setters

    public List<DrugTrackingRequestDTO> getDrugTrackingList() {
        return drugTrackingList;
    }

    public void setDrugTrackingList(List<DrugTrackingRequestDTO> drugTrackingList) {
        this.drugTrackingList = drugTrackingList;
    }
}
