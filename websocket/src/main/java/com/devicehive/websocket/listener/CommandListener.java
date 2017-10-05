package com.devicehive.websocket.listener;

import com.devicehive.websocket.model.repsonse.*;

public class CommandListener implements ErrorListener {


    public void onInsert(CommandInsertResponse response) {

    }

    public void onUpdate(ResponseAction response) {
    }

    public void onList(CommandListResponse response) {
    }

    public void onGet(CommandGetResponse response) {
    }

    public void onSubscribe(CommandSubscribeResponse response) {
    }

    public void onUnsubscribe(ResponseAction response) {
    }

    @Override
    public void onError(ErrorResponse error) {

    }
}
