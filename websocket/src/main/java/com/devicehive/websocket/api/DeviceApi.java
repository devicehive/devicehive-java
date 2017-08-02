package com.devicehive.websocket.api;

import com.devicehive.websocket.model.repsonse.data.DeviceVO;
import lombok.NonNull;

import javax.annotation.Nullable;

interface DeviceApi {


    void get(@Nullable Long requestId, String deviceId);

    void list(@Nullable Long requestId, String name, String namePattern, Long networkId,
              String networkName, String sortField, String sortOrder, int take, int skip);

    void save(@Nullable Long requestId, DeviceVO device);

    void delete(@Nullable Long requestId, @NonNull String deviceId);


}
