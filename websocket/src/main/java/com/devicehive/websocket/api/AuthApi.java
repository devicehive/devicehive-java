package com.devicehive.websocket.api;

interface AuthApi {

    void getToken(String login, String password);

    void authenticate(String token);
}
