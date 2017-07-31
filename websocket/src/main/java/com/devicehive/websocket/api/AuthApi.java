package com.devicehive.websocket.api;

import javax.annotation.Nullable;

interface AuthApi {

    void getToken(String login, String password,@Nullable Long requestId);
}
