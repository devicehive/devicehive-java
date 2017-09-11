package com.devicehive.websocket.listener;

import com.devicehive.websocket.model.repsonse.TokenGetResponse;
import com.devicehive.websocket.model.repsonse.TokenRefreshResponse;

public interface TokenListener extends ErrorListener {

    void onGet(TokenGetResponse response);

    void onCreate(TokenGetResponse response);

    void onRefresh(TokenRefreshResponse response);

}
