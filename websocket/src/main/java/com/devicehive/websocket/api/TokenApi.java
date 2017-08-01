package com.devicehive.websocket.api;

import com.devicehive.websocket.model.request.data.JwtPayload;

import javax.annotation.Nullable;

interface TokenApi {

    void get(String login, String password, @Nullable Long requestId);

    void create(@Nullable Long requestId, JwtPayload payload);

    void refresh(String refreshToken, @Nullable Long requestId);
}
