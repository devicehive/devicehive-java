package com.devicehive.websocket.api;

import com.devicehive.websocket.model.request.data.JwtPayload;

import javax.annotation.Nullable;

interface TokenApi {

    void get( Long requestId, String login, String password);

    void create( Long requestId, JwtPayload payload);

    void refresh( Long requestId, String refreshToken);
}
