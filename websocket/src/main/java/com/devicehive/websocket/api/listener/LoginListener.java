package com.devicehive.websocket.api.listener;

import com.devicehive.websocket.model.repsonse.ErrorAction;
import com.devicehive.websocket.model.repsonse.JwtTokenResponse;
import com.devicehive.websocket.model.repsonse.ResponseAction;

public interface LoginListener {

    void onResponse(JwtTokenResponse response);

    void onAuthenticate(ResponseAction response);

    void onError(ErrorAction error);

}
