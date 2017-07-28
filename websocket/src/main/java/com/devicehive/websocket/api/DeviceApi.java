package com.devicehive.websocket.api;

import com.devicehive.websocket.model.DeviceVO;
import lombok.NonNull;

public interface DeviceApi {


    //device/get
    void get(String deviceId);

    //device/list
    void list(String name, String namePattern, Long networkId,
              String networkName, String sortField, String sortOrder, int take, int skip);

    //{
    // "action": {string},
    //"status": {string},
    //"requestId": {object}
    // }
    //device/
    void save(DeviceVO device);

    //{
    // "action": {string},
    //"status": {string},
    //"requestId": {object}
    // }
    //device/delete
    void delete(@NonNull String deviceId);


}
