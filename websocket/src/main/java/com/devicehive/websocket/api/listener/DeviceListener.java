package com.devicehive.websocket.api.listener;

import com.devicehive.websocket.model.DeviceVO;
import com.devicehive.websocket.model.ErrorAction;

import java.util.List;

public interface DeviceListener {

    void onDeviceList(List<DeviceVO> response);

    void onDeviceGet(DeviceVO response);

    void onDeviceDelete(List<DeviceVO> response);

    void onError(ErrorAction error);

}
