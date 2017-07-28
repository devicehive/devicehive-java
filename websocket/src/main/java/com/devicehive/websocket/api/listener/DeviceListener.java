package com.devicehive.websocket.api.listener;

import com.devicehive.websocket.model.repsonse.data.DeviceVO;
import com.devicehive.websocket.model.repsonse.ErrorAction;

import java.util.List;

public interface DeviceListener {

    void onDeviceList(List<DeviceVO> response);

    void onDeviceGet(DeviceVO response);

    void onDeviceDelete(List<DeviceVO> response);

    void onError(ErrorAction error);

}
