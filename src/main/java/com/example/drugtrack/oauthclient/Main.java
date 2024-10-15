package com.example.drugtrack.oauthclient;

import com.example.drugtrack.oauthclient.client.OAuth2Client;
import com.example.drugtrack.oauthclient.config.OAuth2Config;
import com.example.drugtrack.oauthclient.dto.TokenResponse;

public class Main {
    public static void main(String[] args) {
        // Initialize OAuth2Config with the provided credentials
        OAuth2Config config = new OAuth2Config(
                "nipa.71a69b9e5dad0ddd",
                "4275869d11639e98dd983639f42f7cf0a22df52c",
                "nipa_company01@nipa.co",
                "1234",
                "http://192.168.0.51:3000/oauth/token",
                "read write"
        );

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
