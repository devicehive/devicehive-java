package com.devicehive.client.model;

public interface DeviceCommandCallback {

    void onSuccess(DeviceCommand command);

    void onFail(FailureData failureData);
}
