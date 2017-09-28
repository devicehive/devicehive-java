package com.devicehive.client.service;

import com.devicehive.client.model.DHResponse;
import com.devicehive.client.model.DeviceNotification;
import com.devicehive.client.model.Parameter;
import com.devicehive.rest.api.DeviceNotificationApi;
import com.devicehive.rest.model.DeviceNotificationWrapper;
import com.devicehive.rest.model.InsertNotification;
import org.joda.time.DateTime;

import java.io.IOException;
import java.util.List;

public class DeviceNotificationService extends BaseService {

    private DeviceNotificationApi notificationApi;

    public DHResponse<DeviceNotification> sendNotification(String deviceId, String notification, List<Parameter> parameters) throws IOException {
        notificationApi = createService(DeviceNotificationApi.class);

        DeviceNotificationWrapper notificationWrapper = createDeviceNotificationWrapper(notification, parameters);
        DHResponse<InsertNotification> result = execute(notificationApi.insert(deviceId, notificationWrapper));
        if (result.isSuccessful()) {
            return getNotification(deviceId, result.getData().getId());
        } else if (result.getFailureData().getCode() == 401) {
            authorize();
            result = execute(notificationApi.insert(deviceId, notificationWrapper));
            return getNotification(deviceId, result.getData().getId());
        } else {
            return new DHResponse<DeviceNotification>(null, result.getFailureData());
        }
    }

    private DeviceNotificationWrapper createDeviceNotificationWrapper(String notification, List<Parameter> parameters) {
        DeviceNotificationWrapper notificationWrapper = new DeviceNotificationWrapper();
        notificationWrapper.setNotification(notification);
        notificationWrapper.setTimestamp(DateTime.now());
        notificationWrapper.setParameters(wrapParameters(parameters));
        return notificationWrapper;

    }

    private DHResponse<DeviceNotification> getNotification(String deviceId, long notificationId) throws IOException {
        DHResponse<com.devicehive.rest.model.DeviceNotification> result = execute(notificationApi.get(deviceId,
                notificationId));
        if (result.isSuccessful()) {
            return new DHResponse<DeviceNotification>(DeviceNotification.create(result.getData()),
                    result.getFailureData());
        } else if (result.getFailureData().getCode() == 401) {
            authorize();
            result = execute(notificationApi.get(deviceId,
                    notificationId));
            return new DHResponse<DeviceNotification>(DeviceNotification.create(result.getData()),
                    result.getFailureData());
        } else {
            return new DHResponse<DeviceNotification>(null, result.getFailureData());
        }
    }
}
