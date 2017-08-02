package com.devicehive.websocket.api;

import com.devicehive.websocket.model.request.data.JwtPayload;

import javax.annotation.Nullable;

interface TokenApi {

    void get(@Nullable Long requestId, String login, String password);

    void create(@Nullable Long requestId, JwtPayload payload);

    void refresh(@Nullable Long requestId, String refreshToken);
}
