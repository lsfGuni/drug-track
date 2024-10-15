package com.example.drugtrack.oauthclient.client;

import com.example.drugtrack.oauthclient.config.OAuth2Config;
import com.example.drugtrack.oauthclient.dto.TokenResponse;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class OAuth2Client {

    private final OAuth2Config config;
    private final RestTemplate restTemplate;

    public OAuth2Client(OAuth2Config config) {
        this.config = config;
        this.restTemplate = new RestTemplate();
    }

    public TokenResponse getToken() {
        // Set Authorization Header (Basic Auth)
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        String credentials = config.getClientId() + ":" + config.getClientSecret();
        String encodedCredentials = Base64.getEncoder().encodeToString(credentials.getBytes(StandardCharsets.UTF_8));
        headers.set("Authorization", "Basic " + encodedCredentials);

        // Create request body
        String body = String.format(
                "grant_type=password&username=%s&password=%s&scope=%s",
                config.getUsername(), config.getPassword(), config.getScope()
        );

        HttpEntity<String> requestEntity = new HttpEntity<>(body, headers);

        // Make the API call
        ResponseEntity<TokenResponse> response = restTemplate.postForEntity(
                config.getEndpoint(), requestEntity, TokenResponse.class
        );

        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        } else {
            throw new RuntimeException("Failed to obtain token: " + response.getStatusCode());
        }
    }
}
