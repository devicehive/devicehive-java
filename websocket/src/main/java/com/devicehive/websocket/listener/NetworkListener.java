package com.devicehive.websocket.listener;

import com.devicehive.websocket.model.repsonse.NetworkGetResponse;
import com.devicehive.websocket.model.repsonse.NetworkInsertResponse;
import com.devicehive.websocket.model.repsonse.NetworkListResponse;
import com.devicehive.websocket.model.repsonse.ResponseAction;

public interface NetworkListener extends ErrorListener {

    void onList(NetworkListResponse response);

    void onGet(NetworkGetResponse response);

    void onInsert(NetworkInsertResponse response);

    void onDelete(ResponseAction response);

    void onUpdate(ResponseAction response);
}
