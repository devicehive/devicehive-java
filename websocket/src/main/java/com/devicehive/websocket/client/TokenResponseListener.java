package com.devicehive.websocket.client;

import com.devicehive.websocket.client.model.ErrorAction;
import com.devicehive.websocket.client.model.JwtTokenVO;

public interface TokenResponseListener {

    void onResult(JwtTokenVO result);

    void onError(ErrorAction error);
}
