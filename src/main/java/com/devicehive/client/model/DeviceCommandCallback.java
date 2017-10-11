package com.devicehive.client.model;

import com.devicehive.client.service.DeviceCommand;

public interface DeviceCommandCallback {

    void onSuccess(DeviceCommand command);

    void onFail(FailureData failureData);
}
