package com.devicehive.client.model;

import com.devicehive.client.service.DeviceCommand;

import java.util.List;

public interface DeviceCommandsCallback {

    void onSuccess(List<DeviceCommand> commands);

    void onFail(FailureData failureData);
}
