package com.example.drugtrack.controller;

import com.example.drugtrack.service.HashListService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

@RestController
public class HashListController {

    private final HashListService hashListService;

    public HashListController(HashListService hashListService) {
        this.hashListService = hashListService;
    }

    @GetMapping("/getBlockchainData")
    public String getBlockchainData(@RequestParam String transactionHash) {
        try {
            // 블록체인에서 트랜잭션 영수증을 조회
            TransactionReceipt receipt = hashListService.getTransactionReceipt(transactionHash);

            // 영수증에서 이벤트 데이터를 추출
            String blockchainData = hashListService.extractDataFromReceipt(receipt);

            return blockchainData;
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving data from blockchain", e);
        }
    }
}
