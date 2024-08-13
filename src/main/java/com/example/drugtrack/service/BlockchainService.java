package com.example.drugtrack.service;

import com.example.blockchain.DataStorage;
import com.example.drugtrack.entity.ApiDrugResponse;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
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
        this.transactionManager = new RawTransactionManager(web3j, Credentials.create("0x29c8e93817331009579ac9acb5e3d4de4bd4259ee6763d8684b160ca781b3b69"));
        // 가스 가격과 가스 한도를 설정 (가스 한도를 크게 설정합니다)
        this.contractGasProvider = new StaticGasProvider(BigInteger.valueOf(20000000000L), BigInteger.valueOf(1000000L));
        // 배포된 스마트 계약 주소로 DataStorage 인스턴스 로드
        this.dataStorageContract = DataStorage.load("0x3E777518ae795415251796e2aCf3513e540Ab4cE", web3j, transactionManager, contractGasProvider);
    }


    public String sendDataToBlockchain(ApiDrugResponse response) {
        try {
            // 계정 잔액 확인
            BigInteger balance = web3j.ethGetBalance(transactionManager.getFromAddress(), DefaultBlockParameterName.LATEST).send().getBalance();
            BigInteger requiredBalance = BigInteger.valueOf(20000000000000000L); // 트랜잭션을 위한 최소 필요 금액

            if (balance.compareTo(requiredBalance) < 0) {
                throw new RuntimeException("Not enough funds in the account");
            }

            // 데이터를 블록체인에 저장
            TransactionReceipt receipt = dataStorageContract.send_storeData(response.getTx()).send();

            // 트랜잭션에서 소비된 가스를 출력
            System.out.println("Gas Used: " + receipt.getGasUsed());

            // 블록체인에 저장된 데이터를 확인
            String storedData = dataStorageContract.call_data().send();
            System.out.println("Stored Data: " + storedData);
            // 데이터베이스에 저장된 데이터와 블록체인 데이터를 비교
            if (response.getTx().equals(storedData)) {
                System.out.println("Data matches between DB and Blockchain");
            } else {
                System.out.println("Data mismatch between DB and Blockchain");
            }

            return receipt.getTransactionHash();
        } catch (Exception e) {
            throw new RuntimeException("Error storing data on blockchain", e);
        }
    }

    public TransactionReceipt getTransactionReceipt(String transactionHash) throws Exception {
        return web3j.ethGetTransactionReceipt(transactionHash).send().getTransactionReceipt().orElseThrow(() -> new RuntimeException("Transaction receipt not found"));
    }

    public String extractDataFromReceipt(TransactionReceipt receipt) {
        return dataStorageContract.getDataStoredEvents(receipt).stream()
                .map(event -> event.data)
                .findFirst()
                .orElse("No data found in the specified transaction");
    }

}
