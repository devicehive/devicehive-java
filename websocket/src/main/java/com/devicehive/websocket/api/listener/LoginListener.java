package com.devicehive.websocket.api.listener;

import com.devicehive.websocket.model.ErrorAction;
import com.devicehive.websocket.model.JwtTokenVO;

public interface LoginListener {

    void onResponse(JwtTokenVO response);

    void onError(ErrorAction error);

}
