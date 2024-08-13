package com.example.drugtrack.service;

import com.example.blockchain.DataStorage;
import com.example.drugtrack.entity.ApiDrugResponse;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.RawTransactionManager;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.StaticGasProvider;

import java.math.BigInteger;

@Service
public class BlockchainService {

    private final Web3j web3j;
    private final TransactionManager transactionManager;
    private final ContractGasProvider contractGasProvider;
    private final DataStorage dataStorageContract;

    public BlockchainService() {
        // Ganache에 연결
        this.web3j = Web3j.build(new HttpService("http://127.0.0.1:8545"));
        // 계정의 프라이빗 키를 사용하여 TransactionManager 초기화
        this.transactionManager = new RawTransactionManager(web3j, Credentials.create("0x5d45e15bf83affa5184fe4acb5f4e96f3eb6842b4f20be0d8a98ef6c698fe02c"));
        // 가스 가격과 가스 한도를 설정 (가스 한도를 크게 설정합니다)
        this.contractGasProvider = new StaticGasProvider(BigInteger.valueOf(20000000000L), BigInteger.valueOf(1000000L));
        // 배포된 스마트 계약 주소로 DataStorage 인스턴스 로드
        this.dataStorageContract = DataStorage.load("0xD37e7E30443D903A1e04e26076A81D520751a6B0", web3j, transactionManager, contractGasProvider);
    }


    public String sendDataToBlockchain(ApiDrugResponse response) {
        try {
            // 데이터를 블록체인에 저장
            TransactionReceipt receipt = dataStorageContract.send_storeData(response.getBarcodeData()).send();

            // 트랜잭션에서 소비된 가스를 출력
            System.out.println("Gas Used: " + receipt.getGasUsed());

            return receipt.getTransactionHash();
        } catch (Exception e) {
            throw new RuntimeException("Error storing data on blockchain", e);
        }
    }



}
