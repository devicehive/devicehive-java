package com.devicehive.websocket.listener;

import com.devicehive.websocket.model.repsonse.ConfigurationGetResponse;
import com.devicehive.websocket.model.repsonse.ConfigurationInsertResponse;
import com.devicehive.websocket.model.repsonse.ResponseAction;

public interface ConfigurationListener extends ErrorListener {

    void onGet(ConfigurationGetResponse response);

    void onInsert(ConfigurationInsertResponse response);

    void onDelete(ResponseAction response);

}
