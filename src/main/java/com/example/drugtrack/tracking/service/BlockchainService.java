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
import java.util.List;
import java.util.Map;

/**
 * BlockchainService는 의약품 데이터를 블록체인에 저장하는 서비스입니다.
 * OAuth2 토큰 발급을 관리하며, 유효한 토큰을 사용하여 블록체인 API에 데이터를 업로드합니다.
 */
@Service
public class BlockchainService {

    private static final Logger log = LoggerFactory.getLogger(BlockchainService.class);
    private String token; // OAuth2 액세스 토큰
    private Instant tokenExpiry; // 토큰 만료 시간

    /**
     * 토큰이 유효한지 확인하는 메서드입니다.
     * 토큰이 null이 아니고, 현재 시간이 토큰 만료 시간보다 이전인지 확인합니다.
     *
     * @return 토큰이 유효하면 true, 그렇지 않으면 false를 반환
     */
    public boolean isTokenValid() {
        return token != null && Instant.now().isBefore(tokenExpiry);
    }

    /**
     * 유효한 토큰이 있는지 확인하고, 만료되었거나 없을 경우 블록체인 서버에 API 호출 후 새로운 토큰을 발급받는 메서드입니다.
     * 이 메서드는 여러 스레드에서 동시 호출될 때 동기화됩니다.
     */
    public synchronized void ensureValidToken() {
        if (!isTokenValid()) {
            refreshOAuth2Token();
        }
    }

    /**
     * 블록체인에 데이터를 저장하는 메서드입니다.
     * 블록체인 API를 호출하여 seq 및 hashCode를 블록체인에 업로드합니다.
     * 유효한 토큰을 사용하여 요청을 인증합니다.
     *
     * @param dataList 블록체인에 저장할 데이터의 seq, hashcode
     */
    public synchronized void storeBulkDataOnBlockchain(List<Map<String, Object>> dataList) {
        try {
            // 유효한 토큰이 있는지 확인하고 필요시 갱신
            ensureValidToken();

            // 블록체인 API 요청을 준비.
            RestTemplate restTemplate = new RestTemplate();
            String blockchainApiUrl = "http://nipabaas.berith.co/info/insertBlockDrug";
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + token); // OAuth2 토큰을 헤더에 추가
            headers.set("Content-Type", "application/json"); // 요청의 콘텐츠 유형 설정

            int batchSize = 100;

            if (dataList.size() <= batchSize) {
                // 데이터가 500건 이하일 때, 한 번의 요청으로 전송
                log.info("블록체인에 전송하는 데이터 (전체): {}", dataList);
                HttpEntity<List<Map<String, Object>>> requestEntity = new HttpEntity<>(dataList, headers);

                // 블록체인 API에 POST 요청을 보내고 응답을 받음
                ResponseEntity<String> response = restTemplate.exchange(
                        blockchainApiUrl, HttpMethod.POST, requestEntity, String.class
                );

                // 요청 성공 여부를 확인하고 로그로 기록
                if (response.getStatusCode().is2xxSuccessful()) {
                    log.info("Blockchain upload successful for full batch: {}", response.getBody());
                } else {
                    log.error("Blockchain upload failed for full batch - Status: {}, Response: {}", response.getStatusCode(), response.getBody());
                }

            } else {
                // 데이터가 500건을 초과할 때, 500건씩 분할하여 요청
                for (int i = 0; i < dataList.size(); i += batchSize) {
                    List<Map<String, Object>> batchData = dataList.subList(i, Math.min(i + batchSize, dataList.size()));

                    // 로그로 데이터 확인
                    log.info("블록체인에 전송하는 데이터 ({} ~ {}): {}", i + 1, Math.min(i + batchSize, dataList.size()), batchData);

                    // 요청 본문 생성
                    HttpEntity<List<Map<String, Object>>> requestEntity = new HttpEntity<>(batchData, headers);

                    // 블록체인 API에 POST 요청을 보내고 응답을 받음
                    ResponseEntity<String> response = restTemplate.exchange(
                            blockchainApiUrl, HttpMethod.POST, requestEntity, String.class
                    );

                    // 요청 성공 여부를 확인하고 로그로 기록
                    if (response.getStatusCode().is2xxSuccessful()) {
                        log.info("Blockchain upload successful for batch {} to {}: {}", i + 1, Math.min(i + batchSize, dataList.size()), response.getBody());
                    } else {
                        log.error("Blockchain upload failed for batch {} to {} - Status: {}, Response: {}", i + 1, Math.min(i + batchSize, dataList.size()), response.getStatusCode(), response.getBody());
                    }
                }
            }
        } catch (Exception e) {
            log.error("Error during blockchain API call: ", e);
        }
    }



    /**
     * OAuth2 토큰을 갱신하는 메서드입니다.
     * 토큰 발급이 실패할 경우 재시도를 수행하며, 3회 시도 후에도 실패하면 예외를 던집니다.
     */
    private void refreshOAuth2Token() {
        int retries = 3; // 토큰 발급 실패 시 재시도 횟수
        while (retries > 0) {
            try {
                // OAuth2 설정 구성, API OAuth 설정 클래스에서 정보 받아야 함
                //TODO OAuth2 토큰 API
                /*토큰발급벙보 --개발*/
                OAuth2Config config = new OAuth2Config(
                        "nipa.71a69b9e5dad0ddd",    // 클라이언트 ID
                        "4275869d11639e98dd983639f42f7cf0a22df52c", // 클라이언트 시크릿
                        "nipa_company02@nipa.co",    // 사용자 이메일
                        "1234", // 비밀번호
                        "http://nipabaas.berith.co/oauth/token", // 토큰 발급 URL
                        "read write"    // 권한 범위
                );
                /* 토큰발급 정보 -- local
                OAuth2Config config = new OAuth2Config(
                        "nipa.71a69b9e5dad0ddd",    // 클라이언트 ID
                        "4275869d11639e98dd983639f42f7cf0a22df52c", // 클라이언트 시크릿
                        "nipa_company02@nipa.co",    // 사용자 이메일
                        "1234", // 비밀번호
                        "http://192.168.0.51:3000/oauth/token", // 토큰 발급 URL
                        "read write"    // 권한 범위
                );
                */
                // OAuth2 클라이언트를 생성하고 토큰 요청
                OAuth2Client client = new OAuth2Client(config);
                TokenResponse tokenResponse = client.getToken();

                // 토큰 응답을 검증
                if (tokenResponse == null || tokenResponse.getAccessToken() == null) {
                    throw new RuntimeException("토큰 응답이 null입니다.");
                }

                // 새 토큰 및 만료 시간 설정
                this.token = tokenResponse.getAccessToken();
                this.tokenExpiry = Instant.now().plusSeconds(tokenResponse.getExpiresIn());
                log.info("새 OAuth2 토큰 발급됨: " + token);
                return;  // 토큰 발급 성공 시 종료
            } catch (Exception e) {
                retries--;   // 실패 시 재시도 횟수 감소
                log.error("OAuth2 토큰 발급 실패. 남은 재시도 횟수: " + retries, e);
                if (retries == 0) {
                    throw new RuntimeException("재시도 후에도 토큰 발급에 실패했습니다.", e);
                }
            }
        }
    }

}
