package com.example.drugtrack.oauthclient.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
/**
 * TokenResponse 클래스는 OAuth2 서버에서 반환되는 토큰 정보를 담는 DTO(Data Transfer Object)입니다.
 * 이 클래스는 액세스 토큰, 토큰 유형, 리프레시 토큰, 만료 시간, 스코프와 같은 정보를 포함합니다.
 * JSON 응답의 필드와 매핑되도록 Jackson 라이브러리를 사용합니다.
 */
public class TokenResponse {
    @JsonProperty("access_token")
    private String accessToken;  // OAuth2 액세스 토큰

    @JsonProperty("token_type")
    private String tokenType;   // 토큰 유형 (예: "Bearer")

    @JsonProperty("refresh_token")
    private String refreshToken;  // OAuth2 리프레시 토큰 (선택적)

    @JsonProperty("expires_in")
    private Long expiresIn; // 액세스 토큰의 만료 시간 (초 단위)

    @JsonProperty("scope")
    private String scope;  // 토큰의 권한 범위 (스코프)

    // Getters
    public String getAccessToken() { return accessToken; }
    public String getTokenType() { return tokenType; }
    public String getRefreshToken() { return refreshToken; }
    public Long getExpiresIn() { return expiresIn; }
    public String getScope() { return scope; }

    /**
     * 객체 정보를 문자열로 변환하여 반환합니다.
     *
     * @return 클래스의 필드 정보를 포함한 문자열
     */
    @Override
    public String toString() {
        return "TokenResponse{" +
                "accessToken='" + accessToken + '\'' +
                ", tokenType='" + tokenType + '\'' +
                ", refreshToken='" + refreshToken + '\'' +
                ", expiresIn=" + expiresIn +
                ", scope='" + scope + '\'' +
                '}';
    }
}
