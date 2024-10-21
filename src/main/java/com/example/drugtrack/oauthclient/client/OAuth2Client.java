package com.example.drugtrack.oauthclient.client;

import com.example.drugtrack.oauthclient.config.OAuth2Config;
import com.example.drugtrack.oauthclient.dto.TokenResponse;
import org.springframework.http.*;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * OAuth2Client는 OAuth2 인증을 위해 Access Token을 요청하는 클라이언트 클래스입니다.
 * 이 클래스는 OAuth2 서버와 통신하여, 지정된 자격 증명(clientId, clientSecret, username, password)을 통해 토큰을 받아옵니다.
 */
public class OAuth2Client {

    private final OAuth2Config config;        // OAuth2 설정 정보가 담긴 객체
    private final RestTemplate restTemplate;  // HTTP 요청을 보내기 위한 RestTemplate


    /**
     * OAuth2Client 생성자.
     * OAuth2Config 객체를 통해 필요한 인증 정보를 받아 RestTemplate을 생성합니다.
     *
     * @param config OAuth2 인증에 필요한 설정 정보를 담고 있는 객체
     */
    public OAuth2Client(OAuth2Config config) {
        this.config = config;
        this.restTemplate = createRestTemplateWithTimeout(); // 타임아웃 설정된 RestTemplate 생성
    }

    /**
     * 커스텀 타임아웃을 가진 RestTemplate 객체를 생성합니다.
     * 연결 및 읽기 타임아웃을 각각 5초로 설정합니다.
     *
     * @return 타임아웃 설정된 RestTemplate 객체
     */
    private RestTemplate createRestTemplateWithTimeout() {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(5000); // 5초 연결 타임아웃 설정
        requestFactory.setReadTimeout(5000);     // 5초 읽기 타임아웃 설정
        return new RestTemplate(requestFactory);
    }

    /**
     * OAuth2 서버에 요청하여 Access Token을 가져오는 메서드입니다.
     * 기본 인증(Basic Auth)을 사용하여 클라이언트 자격 증명(clientId, clientSecret)을 헤더에 추가하고,
     * 사용자 자격 증명(username, password) 및 기타 정보를 POST 요청으로 전송합니다.
     *
     * @return TokenResponse 객체로 Access Token 및 기타 토큰 정보가 반환됩니다.
     */
    public TokenResponse getToken() {
        // Authorization 헤더 설정 (Basic Auth)
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED); // POST 요청의 Content-Type 설정

        // 클라이언트 자격 증명을 Base64로 인코딩하여 Authorization 헤더에 추가
        String credentials = config.getClientId() + ":" + config.getClientSecret();
        String encodedCredentials = Base64.getEncoder()
                .encodeToString(credentials.getBytes(StandardCharsets.UTF_8));
        headers.set("Authorization", "Basic " + encodedCredentials);

        // 요청 본문 생성 (grant_type, username, password, scope)
        String body = String.format(
                "grant_type=password&username=%s&password=%s&scope=%s",
                config.getUsername(), config.getPassword(), config.getScope()
        );

        HttpEntity<String> requestEntity = new HttpEntity<>(body, headers); // 요청 엔터티 생성

        try {
            // OAuth2 서버에 POST 요청을 보내 Access Token을 요청
            ResponseEntity<TokenResponse> response = restTemplate.postForEntity(
                    config.getEndpoint(), requestEntity, TokenResponse.class
            );

            // 성공적으로 응답을 받으면 TokenResponse 객체를 반환
            if (response.getStatusCode() == HttpStatus.OK) {
                return response.getBody();
            } else {
                // 실패 시 예외 발생
                throw new RuntimeException("Failed to obtain token: " + response.getStatusCode());
            }
        } catch (Exception e) {
            // 오류 발생 시 예외를 던짐
            throw new RuntimeException("Error obtaining token: " + e.getMessage(), e);
        }
    }
}