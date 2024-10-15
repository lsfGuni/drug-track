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

    public void storeDataOnBlockchain(Long seq, String hashCode) {
        try {
            // OAuth2 인증을 통한 토큰 발급 (토큰이 없거나 만료된 경우에만 발급)
            if (token == null || Instant.now().isAfter(tokenExpiry)) {
                refreshOAuth2Token();
            }

            // 블록체인 API 호출 준비
            RestTemplate restTemplate = new RestTemplate();
            String blockchainApiUrl = "http://192.168.0.51:3000/info/insertBlockDrug";
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + token);
            headers.set("Content-Type", "application/json");

            // 데이터 설정
            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("seq", seq);
            requestBody.put("hashcode", hashCode);

            HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);

            // 블록체인 API 호출
            ResponseEntity<String> response = restTemplate.exchange(blockchainApiUrl, HttpMethod.POST, requestEntity, String.class);
            if (response.getStatusCode().is2xxSuccessful()) {
                log.info("블록체인 업로드 성공: " + response.getBody());
            } else {
                log.error("블록체인 업로드 실패 - 상태 코드: " + response.getStatusCode() + ", 응답 내용: " + response.getBody());
            }
        } catch (Exception e) {
            log.error("블록체인 API 호출 중 오류 발생: ", e);
        }
    }

    private void refreshOAuth2Token() {
        try {
            // OAuth2Config 초기화
            OAuth2Config config = new OAuth2Config(
                    "nipa.71a69b9e5dad0ddd",
                    "4275869d11639e98dd983639f42f7cf0a22df52c",
                    "nipa_company01@nipa.co",
                    "1234",
                    "http://192.168.0.51:3000/oauth/token",
                    "read write"
            );

            // OAuth2Client 생성 및 토큰 요청
            OAuth2Client client = new OAuth2Client(config);
            TokenResponse tokenResponse = client.getToken();

            if (tokenResponse == null || tokenResponse.getAccessToken() == null) {
                throw new RuntimeException("토큰 응답이 null입니다.");
            }

            this.token = tokenResponse.getAccessToken();
            this.tokenExpiry = Instant.now().plusSeconds(tokenResponse.getExpiresIn());
            log.info("새로운 OAuth2 토큰 발급 완료: " + token);
        } catch (Exception e) {
            log.error("OAuth2 토큰 발급 중 오류 발생 - 원인: {}", e.getMessage());
            throw new RuntimeException("토큰 발급 실패");
        }
    }
}
