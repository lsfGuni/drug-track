package com.example.drugtrack.tracking.service;


import com.example.drugtrack.oauthclient.client.OAuth2Client;
import com.example.drugtrack.oauthclient.config.OAuth2Config;
import com.example.drugtrack.oauthclient.dto.TokenResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
@Service
public class BlockchainService {

    private static final Logger log = LoggerFactory.getLogger(BlockchainService.class);
    private String token;
    private Instant tokenExpiry;

    // Method to check if the token is still valid
    public boolean isTokenValid() {
        return token != null && Instant.now().isBefore(tokenExpiry);
    }

    // Method to ensure a valid token is available
    public synchronized void ensureValidToken() {
        if (!isTokenValid()) {
            refreshOAuth2Token();
        }
    }

    public synchronized void storeDataOnBlockchain(Long seq, String hashCode) {
        try {
            // Ensure token is issued only if it's null or expired
            ensureValidToken();

            // Prepare Blockchain API request
            RestTemplate restTemplate = new RestTemplate();
            String blockchainApiUrl = "http://192.168.0.51:3000/info/insertBlockDrug";
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + token);
            headers.set("Content-Type", "application/json");

            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("seq", seq);
            requestBody.put("hashcode", hashCode);

            HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);
            ResponseEntity<String> response = restTemplate.exchange(
                    blockchainApiUrl, HttpMethod.POST, requestEntity, String.class
            );

            if (response.getStatusCode().is2xxSuccessful()) {
                log.info("Blockchain upload successful: " + response.getBody());
            } else {
                log.error("Blockchain upload failed - Status: " + response.getStatusCode() + ", Response: " + response.getBody());
            }
        } catch (Exception e) {
            log.error("Error during blockchain API call: ", e);
        }
    }

    private void refreshOAuth2Token() {
        int retries = 3;
        while (retries > 0) {
            try {
                OAuth2Config config = new OAuth2Config(
                        "nipa.71a69b9e5dad0ddd",
                        "4275869d11639e98dd983639f42f7cf0a22df52c",
                        "nipa_company01@nipa.co",
                        "1234",
                        "http://192.168.0.51:3000/oauth/token",
                        "read write"
                );

                OAuth2Client client = new OAuth2Client(config);
                TokenResponse tokenResponse = client.getToken();

                if (tokenResponse == null || tokenResponse.getAccessToken() == null) {
                    throw new RuntimeException("Token response is null");
                }

                this.token = tokenResponse.getAccessToken();
                this.tokenExpiry = Instant.now().plusSeconds(tokenResponse.getExpiresIn());
                log.info("New OAuth2 token issued: " + token);
                return;  // Exit on success
            } catch (Exception e) {
                retries--;
                log.error("Failed to issue OAuth2 token. Retries left: " + retries, e);
                if (retries == 0) {
                    throw new RuntimeException("Token issuance failed after retries", e);
                }
            }
        }
    }

}
