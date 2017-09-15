package com.devicehive.client;

import com.devicehive.client.model.TokenAuth;

public class TokenHelper {
    private final TokenAuth tokenAuth;

    private TokenHelper() {
        tokenAuth = new TokenAuth();
    }

    private static class InstanceHolder {
        static final TokenHelper INSTANCE = new TokenHelper();
    }

    public static TokenHelper getInstance() {
        return TokenHelper.InstanceHolder.INSTANCE;
    }

    public TokenAuth getTokenAuth() {
        return tokenAuth;
    }
}

