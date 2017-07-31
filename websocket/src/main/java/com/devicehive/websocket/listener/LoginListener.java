package com.devicehive.websocket.listener;

import com.devicehive.websocket.model.repsonse.JwtTokenResponse;

public interface LoginListener extends ErrorListener{

    void onResponse(JwtTokenResponse response);

}
