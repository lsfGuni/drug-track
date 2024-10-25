package com.example.drugtrack.oauthclient;

import com.example.drugtrack.oauthclient.client.OAuth2Client;
import com.example.drugtrack.oauthclient.config.OAuth2Config;
import com.example.drugtrack.oauthclient.dto.TokenResponse;

public class OAuth2ClientMain {
    public static void main(String[] args) {
        // Initialize OAuth2Config with the provided credentials
        //TODO OAuth2 토큰 API
        /*토큰클라이언트정보 -- 개발 */
        OAuth2Config config = new OAuth2Config(
                "nipa.71a69b9e5dad0ddd",
                "4275869d11639e98dd983639f42f7cf0a22df52c",
                "nipa_company01@nipa.co",
                "1234",
                "http://192.168.103.11:3000/oauth/token",
                "read write"
        );
        /*토큰클라이언트정보 -- 로컬
        OAuth2Config config = new OAuth2Config(
                "nipa.71a69b9e5dad0ddd",
                "4275869d11639e98dd983639f42f7cf0a22df52c",
                "nipa_company01@nipa.co",
                "1234",
                "http://192.168.0.51:3000/oauth/token",
                "read write"
        );
        */
        // Create OAuth2Client and request the token
        OAuth2Client client = new OAuth2Client(config);
        try {
            TokenResponse tokenResponse = client.getToken();
            System.out.println("Token Response: " + tokenResponse);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
