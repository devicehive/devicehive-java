/*
 *
 *
 *   DeviceNotificationService.java
 *
 *   Copyright (C) 2018 DataArt
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
 */

package com.github.devicehive.client.service;

import com.github.devicehive.client.model.DHResponse;
import com.github.devicehive.client.model.DeviceNotification;
import com.github.devicehive.client.model.DeviceNotificationsCallback;
import com.github.devicehive.client.model.FailureData;
import com.github.devicehive.client.model.NotificationFilter;
import com.github.devicehive.client.model.Parameter;
import com.github.devicehive.rest.api.DeviceNotificationApi;
import com.github.devicehive.rest.model.DeviceNotificationWrapper;
import com.github.devicehive.rest.model.NotificationInsert;
import com.github.devicehive.rest.utils.StringUtils;

import org.joda.time.DateTime;
import org.joda.time.Period;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class DeviceNotificationService extends BaseService {

    private static final String CANCELED = "Canceled";
    private boolean isSubscribed;
    private Callback<List<com.github.devicehive.rest.model.DeviceNotification>> pollNotificationsCallback;
    private Call<List<com.github.devicehive.rest.model.DeviceNotification>> pollCall;
    private Call<List<com.github.devicehive.rest.model.DeviceNotification>> pollManyCall;

    DHResponse<DeviceNotification> sendNotification(String deviceId, String notification, List<Parameter> parameters) {
        DeviceNotificationApi notificationApi = createService(DeviceNotificationApi.class);
        DeviceNotificationWrapper notificationWrapper = createDeviceNotificationWrapper(notification, parameters);
        DHResponse<NotificationInsert> result = execute(notificationApi.insert(deviceId, notificationWrapper));

        if (result.isSuccessful()) {
            return getNotification(deviceId, result.getData().getId());
        } else if (result.getFailureData().getCode() == 401) {
            refreshAndAuthorize();
            notificationApi = createService(DeviceNotificationApi.class);
            result = execute(notificationApi.insert(deviceId, notificationWrapper));
            return getNotification(deviceId, result.getData().getId());
        } else {
            return DHResponse.create(null, result.getFailureData());
        }
    }

    private DeviceNotificationWrapper createDeviceNotificationWrapper(String notification, List<Parameter> parameters) {
        DeviceNotificationWrapper notificationWrapper = new DeviceNotificationWrapper();
        notificationWrapper.setNotification(notification);
        notificationWrapper.setTimestamp(DateTime.now());
        if (parameters != null) {
            notificationWrapper.setParameters(wrapParameters(parameters));
        }
        return notificationWrapper;

    }

    private DHResponse<DeviceNotification> getNotification(String deviceId, long notificationId) {
        DeviceNotificationApi notificationApi = createService(DeviceNotificationApi.class);
        DHResponse<com.github.devicehive.rest.model.DeviceNotification> result = execute(notificationApi.get(deviceId,
                notificationId));
        if (result.isSuccessful()) {
            return DHResponse.create(DeviceNotification.create(result.getData()),
                    result.getFailureData());
        } else if (result.getFailureData().getCode() == 401) {
            refreshAndAuthorize();
            notificationApi = createService(DeviceNotificationApi.class);
            result = execute(notificationApi.get(deviceId,
                    notificationId));
            return DHResponse.create(DeviceNotification.create(result.getData()),
                    result.getFailureData());
        } else {
            return DHResponse.create(null, result.getFailureData());
        }
    }

    DHResponse<List<DeviceNotification>> getDeviceNotifications(String deviceId, DateTime startTimestamp,
                                                                DateTime endTimestamp) {
        DeviceNotificationApi notificationApi = createService(DeviceNotificationApi.class);

        Period period = new Period(startTimestamp, endTimestamp);

        DHResponse<List<DeviceNotification>> response;

        DHResponse<List<com.github.devicehive.rest.model.DeviceNotification>> result =
                execute(notificationApi.poll(deviceId, null, startTimestamp.toString(),
                        (long) period.toStandardSeconds().getSeconds()));

        response = new DHResponse<List<DeviceNotification>>(
                DeviceNotification.createList(result.getData()),
                result.getFailureData());
        if (response.isSuccessful()) {
            return response;
        } else if (response.getFailureData().getCode() == 401) {
            refreshAndAuthorize();
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
        DeviceNotificationApi notificationApi = createService(DeviceNotificationApi.class);
        Period period = new Period(filter.getStartTimestamp(), filter.getEndTimestamp());
        if (pollCall != null && pollCall.isExecuted()) {
            pollCall.cancel();
            pollCall = null;
        }
        pollCall = notificationApi.poll(deviceId, StringUtils.join(",", filter.getNotificationNames()),
                filter.getStartTimestamp().toString(),
                (long) period.toStandardSeconds().getSeconds());

        pollCall.enqueue(getNotificationsCallback(deviceId, filter, isAuthNeeded, notificationsCallback));

    }

    private Callback<List<com.github.devicehive.rest.model.DeviceNotification>> getNotificationsCallback(
            final String deviceId, final NotificationFilter filter, final boolean isAuthNeeded,
            final DeviceNotificationsCallback notificationCallback) {
        if (pollNotificationsCallback == null) {
            pollNotificationsCallback = new Callback<List<com.github.devicehive.rest.model.DeviceNotification>>() {
                public void onResponse(Call<List<com.github.devicehive.rest.model.DeviceNotification>> call,
                                       Response<List<com.github.devicehive.rest.model.DeviceNotification>> response) {
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
                        refreshAndAuthorize();
                        pollNotifications(deviceId, filter, false, notificationCallback);
                    } else {
                        notificationCallback.onFail(createFailData(response.code(), parseErrorMessage(response)));
                        unsubscribeAll();
                    }
                }

                public void onFailure(Call<List<com.github.devicehive.rest.model.DeviceNotification>> call, Throwable t) {
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
        DeviceNotificationApi notificationApi = createService(DeviceNotificationApi.class);
        Period period = new Period(filter.getStartTimestamp(), filter.getEndTimestamp());
        if (pollManyCall != null && pollManyCall.isExecuted()) {
            pollManyCall.cancel();
            pollManyCall = null;
        }
        pollManyCall = notificationApi.pollMany(deviceIds, StringUtils.join(",", filter.getNotificationNames()),
                filter.getStartTimestamp().toString(), (long) period.toStandardSeconds().getSeconds());

        pollManyCall.enqueue(getNotificationsAllCallback(deviceIds, filter, isAuthNeeded, notificationsCallback));

    }


    private Callback<List<com.github.devicehive.rest.model.DeviceNotification>> getNotificationsAllCallback(
            final String deviceIds, final NotificationFilter filter, final boolean isAuthNeeded,
            final DeviceNotificationsCallback notificationCallback) {
        if (pollNotificationsCallback == null) {
            pollNotificationsCallback = new Callback<List<com.github.devicehive.rest.model.DeviceNotification>>() {
                public void onResponse(Call<List<com.github.devicehive.rest.model.DeviceNotification>> call,
                                       Response<List<com.github.devicehive.rest.model.DeviceNotification>> response) {
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
                        refreshAndAuthorize();
                        pollManyNotifications(deviceIds, filter, false, notificationCallback);
                    } else {
                        notificationCallback.onFail(createFailData(response.code(), parseErrorMessage(response)));
                        unsubscribeAll();
                    }
                }

                public void onFailure(Call<List<com.github.devicehive.rest.model.DeviceNotification>> call, Throwable t) {
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
