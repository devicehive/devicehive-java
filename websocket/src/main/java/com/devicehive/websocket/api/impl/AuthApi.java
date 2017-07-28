package com.devicehive.websocket.api.impl;

interface AuthApi {

    void getToken(String login, String password);

    void authenticate(String token);
}
