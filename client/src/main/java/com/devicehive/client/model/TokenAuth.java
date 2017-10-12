package com.devicehive.client.model;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@RequiredArgsConstructor
public class TokenAuth {

    private String refreshToken;
    private String accessToken;

    public boolean canAccess() {
        return accessToken != null && accessToken.length() > 0;
    }

    public boolean canRefresh() {
        return refreshToken != null && refreshToken.length() > 0;
    }


    public TokenAuth setAccessToken(String accessToken) {
        this.accessToken = accessToken;
        return this;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public TokenAuth setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
        return this;
    }
}
