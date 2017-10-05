package com.devicehive.websocket.api;

import com.devicehive.websocket.model.repsonse.data.DeviceVO;

interface DeviceApi {


    void get( Long requestId, String deviceId);

    void list( Long requestId, String name, String namePattern, Long networkId,
              String networkName, String sortField, String sortOrder, int take, int skip);

    void save( Long requestId, DeviceVO device);

    void delete( Long requestId,  String deviceId);


}
