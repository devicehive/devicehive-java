package com.devicehive.websocket.listener;

import com.devicehive.websocket.model.repsonse.*;

public interface UserListener extends ErrorListener {

    void onList(UserListResponse response);

    void onGet(UserGetResponse response);

    void onInsert(UserInsertResponse response);

    void onUpdate(ResponseAction response);

    void onDelete(ResponseAction response);

    void onGetCurrent(UserGetCurrentResponse response);

    void onUpdateCurrent(ResponseAction response);

    void onGetNetwork(UserGetNetworkResponse response);

    void onAssignNetwork(ResponseAction response);

    void onUnassignNetwork(ResponseAction response);
}
