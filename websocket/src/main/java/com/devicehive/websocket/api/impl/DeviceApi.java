package com.devicehive.websocket.api.impl;

import com.devicehive.websocket.model.repsonse.data.DeviceVO;
import lombok.NonNull;

interface DeviceApi {


    void get(String deviceId);

    void list(String name, String namePattern, Long networkId,
              String networkName, String sortField, String sortOrder, int take, int skip);

    void save(DeviceVO device);

    void delete(@NonNull String deviceId);


}
