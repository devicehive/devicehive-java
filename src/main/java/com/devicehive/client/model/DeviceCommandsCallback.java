package com.devicehive.client.model;

import java.util.List;

public interface DeviceCommandsCallback {

    void onSuccess(List<DeviceCommand> command);

    void onFail(FailureData failureData);
}
