package com.devicehive.websocket.listener;

import com.devicehive.websocket.model.repsonse.*;

public interface CommandListener extends ErrorListener {


    void onInsert(CommandInsertResponse response);

    void onUpdate(ResponseAction response);

    void onList(CommandListResponse response);

    void onGet(CommandGetResponse response);

    void onSubscribe(CommandSubscribeResponse response);

    void onUnsubscribe(ResponseAction response);
}
