package com.example.drugtrack.oauthclient.config;


public class OAuth2Config {
    private String clientId;
    private String clientSecret;
    private String username;
    private String password;
    private String endpoint;
    private String scope;

    public OAuth2Config(String clientId, String clientSecret, String username, String password, String endpoint, String scope) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.username = username;
        this.password = password;
        this.endpoint = endpoint;
        this.scope = scope;
    }

    public String getClientId() { return clientId; }
    public String getClientSecret() { return clientSecret; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getEndpoint() { return endpoint; }
    public String getScope() { return scope; }
}
