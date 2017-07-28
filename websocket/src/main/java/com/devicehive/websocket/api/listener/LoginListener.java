package com.devicehive.websocket.api.listener;

import com.devicehive.websocket.model.repsonse.JwtTokenResponse;
import com.devicehive.websocket.model.repsonse.ResponseAction;

public interface LoginListener extends ErrorListener{

    void onResponse(JwtTokenResponse response);

    void onAuthenticate(ResponseAction response);

}
