package com.devicehive.client.service;

import com.devicehive.client.model.*;
import com.devicehive.rest.api.DeviceNotificationApi;
import com.devicehive.rest.model.DeviceNotificationWrapper;
import com.devicehive.rest.model.InsertNotification;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.Period;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.List;

public class DeviceNotificationService extends BaseService {

    private static final String CANCELED = "Canceled";
    private DeviceNotificationApi notificationApi;
    private boolean isSubscribed;
    private Callback<List<com.devicehive.rest.model.DeviceNotification>> pollNotificationsCallback;
    private Call<List<com.devicehive.rest.model.DeviceNotification>> pollCall;
    private Call<List<com.devicehive.rest.model.DeviceNotification>> pollManyCall;

    public DHResponse<DeviceNotification> sendNotification(String deviceId, String notification, List<Parameter> parameters) {
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

    private DHResponse<DeviceNotification> getNotification(String deviceId, long notificationId) {
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

    public DHResponse<List<DeviceNotification>> getDeviceNotifications(String deviceId, DateTime startTimestamp,
                                                                       DateTime endTimestamp) {
        notificationApi = createService(DeviceNotificationApi.class);

        Period period = new Period(startTimestamp, endTimestamp);

        DHResponse<List<DeviceNotification>> response;

        DHResponse<List<com.devicehive.rest.model.DeviceNotification>> result =
                execute(notificationApi.poll(deviceId, null, startTimestamp.toString(),
                        (long) period.toStandardSeconds().getSeconds()));

        response = new DHResponse<List<DeviceNotification>>(
                DeviceNotification.createList(result.getData()),
                result.getFailureData());
        if (response.isSuccessful()) {
            return response;
        } else if (response.getFailureData().getCode() == 401) {
            authorize();
            notificationApi = createService(DeviceNotificationApi.class);
            result = execute(notificationApi.poll(deviceId, null, startTimestamp.toString(),
                    30L));
            return new DHResponse<List<DeviceNotification>>(DeviceNotification.createList(result.getData()),
                    result.getFailureData());
        } else {
            return response;
        }
    }


    void pollNotifications(String deviceId, NotificationFilter filter, boolean isAuthNeeded,
                           DeviceNotificationsCallback notificationsCallback) {
        isSubscribed = true;
        notificationApi = createService(DeviceNotificationApi.class);
        Period period = new Period(filter.getStartTimestamp(), filter.getEndTimestamp());
        if (pollCall != null && pollCall.isExecuted()) {
            pollCall.cancel();
            pollCall = null;
        }
        pollCall = notificationApi.poll(deviceId, StringUtils.join(filter.getNotificationNames(),","),
                filter.getStartTimestamp().toString(),
                (long) period.toStandardSeconds().getSeconds());

        pollCall.enqueue(getNotificationsCallback(deviceId, filter, isAuthNeeded, notificationsCallback));

    }

    private Callback<List<com.devicehive.rest.model.DeviceNotification>> getNotificationsCallback(
            final String deviceId, final NotificationFilter filter, final boolean isAuthNeeded,
            final DeviceNotificationsCallback notificationCallback) {
        if (pollNotificationsCallback == null) {
            pollNotificationsCallback = new Callback<List<com.devicehive.rest.model.DeviceNotification>>() {
                public void onResponse(Call<List<com.devicehive.rest.model.DeviceNotification>> call,
                                       Response<List<com.devicehive.rest.model.DeviceNotification>> response) {
                    if (response.isSuccessful()) {
                        List<DeviceNotification> notifications = new ArrayList<DeviceNotification>();
                        notifications.addAll(DeviceNotification.createList(response.body()));
                        notificationCallback.onSuccess(notifications);
                        if (isSubscribed) {
                            filter.setStartTimestamp(DateTime.now());
                            filter.setEndTimestamp(DateTime.now().plusSeconds(30));
                            pollNotifications(deviceId, filter, false, notificationCallback);
                        }
                    } else if (response.code() == 401 && isAuthNeeded) {
                        System.out.println("AUTH");
                        authorize();
                        pollNotifications(deviceId, filter, false, notificationCallback);
                    } else {
                        notificationCallback.onFail(createFailData(response.code(), parseErrorMessage(response)));
                        unsubscribeAll();
                    }
                }

                public void onFailure(Call<List<com.devicehive.rest.model.DeviceNotification>> call, Throwable t) {
                    if (!t.getMessage().equals(CANCELED)) {
                        notificationCallback.onFail(new FailureData(FailureData.NO_CODE, t.getMessage()));
                        unsubscribeAll();
                    }

                }
            };
        }
        return pollNotificationsCallback;
    }

    public void pollManyNotifications(String deviceIds, NotificationFilter filter, boolean isAuthNeeded,
                                      DeviceNotificationsCallback notificationsCallback) {
        isSubscribed = true;
        notificationApi = createService(DeviceNotificationApi.class);
        Period period = new Period(filter.getStartTimestamp(), filter.getEndTimestamp());
        if (pollManyCall != null && pollManyCall.isExecuted()) {
            pollManyCall.cancel();
            pollManyCall = null;
        }
        pollManyCall = notificationApi.pollMany((long) period.toStandardSeconds().getSeconds(), deviceIds,
                StringUtils.join(filter.getNotificationNames(),","), filter.getStartTimestamp().toString());

        pollManyCall.enqueue(getNotificationsAllCallback(deviceIds, filter, isAuthNeeded, notificationsCallback));

    }


    private Callback<List<com.devicehive.rest.model.DeviceNotification>> getNotificationsAllCallback(
            final String deviceIds, final NotificationFilter filter, final boolean isAuthNeeded,
            final DeviceNotificationsCallback notificationCallback) {
        if (pollNotificationsCallback == null) {
            pollNotificationsCallback = new Callback<List<com.devicehive.rest.model.DeviceNotification>>() {
                public void onResponse(Call<List<com.devicehive.rest.model.DeviceNotification>> call,
                                       Response<List<com.devicehive.rest.model.DeviceNotification>> response) {
                    if (response.isSuccessful()) {
                        List<DeviceNotification> notifications = new ArrayList<DeviceNotification>();
                        notifications.addAll(DeviceNotification.createList(response.body()));
                        notificationCallback.onSuccess(notifications);
                        if (isSubscribed) {
                            filter.setStartTimestamp(DateTime.now());
                            filter.setEndTimestamp(DateTime.now().plusSeconds(30));
                            pollManyNotifications(deviceIds, filter, false, notificationCallback);
                        }
                    } else if (response.code() == 401 && isAuthNeeded) {
                        System.out.println("AUTH");
                        authorize();
                        pollManyNotifications(deviceIds, filter, false, notificationCallback);
                    } else {
                        notificationCallback.onFail(createFailData(response.code(), parseErrorMessage(response)));
                        unsubscribeAll();
                    }
                }

                public void onFailure(Call<List<com.devicehive.rest.model.DeviceNotification>> call, Throwable t) {
                    if (!t.getMessage().equals(CANCELED)) {
                        notificationCallback.onFail(new FailureData(FailureData.NO_CODE, t.getMessage()));
                        unsubscribeAll();
                    }

                }
            };
        }
        return pollNotificationsCallback;
    }

    public void unsubscribeAll() {
        isSubscribed = false;
        if (pollCall != null) {
            pollCall.cancel();
        }
    }
}
