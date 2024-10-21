package com.example.drugtrack.oauthclient.config;

/**
 * OAuth2Config 클래스는 OAuth2 인증을 위해 필요한 설정 정보를 담는 설정 클래스입니다.
 * 이 클래스는 OAuth2 서버에 접근할 때 사용되는 클라이언트 ID, 비밀 키, 사용자 자격 증명 등의 정보를 캡슐화합니다.
 */
public class OAuth2Config {
    private String clientId; // OAuth2 클라이언트 ID
    private String clientSecret; // OAuth2 클라이언트 비밀 키
    private String username;  // 사용자 이름 (사용자 자격 증명)
    private String password; // 사용자 비밀번호 (사용자 자격 증명)
    private String endpoint; // OAuth2 토큰을 요청하는 엔드포인트 URL
    private String scope; // OAuth2 요청에 필요한 스코프 정보
    /**
     * OAuth2Config 생성자.
     * OAuth2 서버와 통신하기 위한 필수 설정 정보를 초기화합니다.
     *
     * @param clientId 클라이언트 ID
     * @param clientSecret 클라이언트 비밀 키
     * @param username 사용자 이름
     * @param password 사용자 비밀번호
     * @param endpoint 토큰 요청을 위한 OAuth2 엔드포인트 URL
     * @param scope 인증에 필요한 스코프 정보
     */
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
