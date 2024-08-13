package com.example.drugtrack.service;

import org.springframework.stereotype.Service;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

@Service
public class HashListService {

    private final BlockchainService blockchainService;

    public HashListService(BlockchainService blockchainService) {
        this.blockchainService = blockchainService;
    }

    public TransactionReceipt getTransactionReceipt(String transactionHash) throws Exception {
        return blockchainService.getTransactionReceipt(transactionHash);
    }

    public String extractDataFromReceipt(TransactionReceipt receipt) {
        return blockchainService.extractDataFromReceipt(receipt);
    }
}
