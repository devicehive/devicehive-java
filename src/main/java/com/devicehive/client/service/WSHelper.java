package com.devicehive.client.service;

import com.devicehive.client.model.TokenAuth;
import com.devicehive.websocket.api.WebSocketClient;

class WSHelper {
    private final WebSocketClient webSocketClient;

    private WSHelper() {
        if (DeviceHive.getInstance().getWSUrl() == null || DeviceHive.getInstance().getWSUrl().length() <= 0) {
            throw new NullPointerException("Server URL cannot be null or empty");
        }
        TokenAuth tokenAuth = TokenHelper.getInstance().getTokenAuth();
        webSocketClient = new WebSocketClient
                .Builder()
                .url(DeviceHive.getInstance().getWSUrl())
                .token(tokenAuth.getAccessToken())
                .refreshToken(tokenAuth.getRefreshToken())
                .build();
    }

    private static class InstanceHolder {
        static final WSHelper INSTANCE = new WSHelper();
    }

    static WSHelper getInstance() {
        return WSHelper.InstanceHolder.INSTANCE;
    }

    WebSocketClient getWebSocketClient() {
        return webSocketClient;
    }
}

