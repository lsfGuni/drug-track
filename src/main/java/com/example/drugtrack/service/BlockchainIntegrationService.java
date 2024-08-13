package com.example.drugtrack.service;

import com.example.drugtrack.entity.ApiDrugResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BlockchainIntegrationService {

    @Autowired
    private BlockchainService blockchainService;

    public String saveToBlockchain(ApiDrugResponse response) {
        // 블록체인에 데이터 저장
        return blockchainService.sendDataToBlockchain(response);
    }
}
