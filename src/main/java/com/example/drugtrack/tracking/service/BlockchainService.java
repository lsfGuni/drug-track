package com.example.drugtrack.tracking.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

/**
 * BlockchainService sends pharmaceutical data to the blockchain server.
 */
@Service
public class BlockchainService {

    private static final Logger log = LoggerFactory.getLogger(BlockchainService.class);
    private final String GO_SERVER_URL = "http://localhost:8081/storeData";  // Go server URL

    /**
     * Sends data to the Go blockchain server to store on the blockchain.
     *
     * @param dataList List of data with seq and hashcode to be stored on the blockchain.
     */
    public synchronized void storeBulkDataOnBlockchain(List<Map<String, Object>> dataList) {
        try {
            // Prepare the request
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-Type", "application/json");  // Set content type

            // Log the data being sent
            log.info("Sending data to Go server: {}", dataList);

            // Send dataList directly as the request body
            HttpEntity<List<Map<String, Object>>> requestEntity = new HttpEntity<>(dataList, headers);

            // Make a POST request to the Go server
            ResponseEntity<String> response = restTemplate.exchange(
                    GO_SERVER_URL, HttpMethod.POST, requestEntity, String.class
            );

            // Log the response status
            if (response.getStatusCode().is2xxSuccessful()) {
                log.info("Data successfully stored on blockchain via Go server: " + response.getBody());
            } else {
                log.error("Failed to store data on blockchain - Status: " + response.getStatusCode() + ", Response: " + response.getBody());
            }
        } catch (Exception e) {
            log.error("Error during blockchain API call to Go server: ", e);
        }
    }
}
