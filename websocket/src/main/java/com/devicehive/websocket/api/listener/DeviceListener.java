package com.devicehive.websocket.api.listener;

import com.devicehive.websocket.model.repsonse.data.DeviceVO;

import java.util.List;

public interface DeviceListener  extends ErrorListener{

    void onDeviceList(List<DeviceVO> response);

    void onDeviceGet(DeviceVO response);

    void onDeviceDelete(List<DeviceVO> response);

}
