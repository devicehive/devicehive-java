package com.devicehive.client.model;

import java.util.List;

public interface DeviceNotificationsCallback {

    void onSuccess(List<DeviceNotification> command);

    void onFail(FailureData failureData);
}
