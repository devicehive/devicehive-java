package com.devicehive.client.model;


public interface DeviceNotificationCallback {

    void onSuccess(DeviceNotification notification);

    void onFail(FailureData failureData);
}
