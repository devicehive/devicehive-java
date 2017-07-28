package com.devicehive.websocket.listener;

import com.devicehive.websocket.model.repsonse.ErrorResponse;

public interface ErrorListener {

    void onError(ErrorResponse error);
}
