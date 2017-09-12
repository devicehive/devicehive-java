package com.devicehive.client.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
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
}
