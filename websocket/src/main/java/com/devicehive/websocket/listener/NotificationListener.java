package com.devicehive.websocket.listener;

import com.devicehive.websocket.model.repsonse.*;

public class NotificationListener implements ErrorListener {

    public  void onList(NotificationListResponse response){}

    public void onGet(NotificationGetResponse response){}

    public void onInsert(NotificationInsertResponse response){}

    public void onSubscribe(NotificationSubscribeResponse response){}

    public void onUnsubscribe(ResponseAction response){}

    @Override
    public void onError(ErrorResponse error) {

    }
}
