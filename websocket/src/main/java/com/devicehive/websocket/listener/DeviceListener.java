package com.devicehive.websocket.listener;

import com.devicehive.websocket.model.repsonse.ResponseAction;
import com.devicehive.websocket.model.repsonse.data.DeviceVO;

import java.util.List;

public interface DeviceListener  extends ErrorListener{

    void onList(List<DeviceVO> response);

    void onGet(DeviceVO response);

    void onDelete(ResponseAction response);

    void onSave(ResponseAction response);

}
