package com.devicehive.client.service;

import com.devicehive.client.model.TokenAuth;

class TokenHelper {
    private final TokenAuth tokenAuth;

    TokenHelper() {
        tokenAuth = new TokenAuth();
    }

    private static class InstanceHolder {
        static final TokenHelper INSTANCE = new TokenHelper();
    }

    static TokenHelper getInstance() {
        return TokenHelper.InstanceHolder.INSTANCE;
    }

    TokenAuth getTokenAuth() {
        return tokenAuth;
    }
}

