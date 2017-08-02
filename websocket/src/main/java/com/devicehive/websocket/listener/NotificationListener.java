package com.devicehive.websocket.listener;

import com.devicehive.websocket.model.repsonse.*;

public interface NotificationListener extends ErrorListener {

    void onList(NotificationListResponse response);

    void onGet(NotificationGetResponse response);

    void onInsert(NotificationInsertResponse response);

    void onSubscribe(NotificationSubscribeResponse response);

    void onUnsubscribe(ResponseAction response);
}
