package com.devicehive.websocket.api;

import com.devicehive.websocket.model.repsonse.data.DeviceVO;
import lombok.NonNull;

import javax.annotation.Nullable;

interface DeviceApi {


    void get(String deviceId,@Nullable  Long requestId);

    void list(String name, String namePattern, Long networkId,
              String networkName,@Nullable  Long requestId, String sortField, String sortOrder, int take, int skip);

    void save(DeviceVO device, @Nullable Long requestId);

    void delete(@NonNull String deviceId, @Nullable Long requestId);


}
