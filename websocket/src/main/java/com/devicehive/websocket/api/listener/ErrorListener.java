package com.devicehive.websocket.api.listener;

import com.devicehive.websocket.model.repsonse.ErrorResponse;

public interface ErrorListener {

    void onError(ErrorResponse error);
}
