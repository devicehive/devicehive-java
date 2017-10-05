package com.devicehive.client.model;

public interface DeviceCommandsCallback {

    void onSuccess(DeviceCommand command);

    void onFail(FailureData failureData);
}
