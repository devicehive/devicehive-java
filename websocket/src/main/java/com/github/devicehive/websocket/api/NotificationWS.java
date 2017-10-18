/*
 *
 *
 *   NotificationWS.java
 *
 *   Copyright (C) 2017 DataArt
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

package com.github.devicehive.websocket.api;

import com.github.devicehive.websocket.listener.NotificationListener;
import com.github.devicehive.websocket.model.SortOrder;
import com.github.devicehive.websocket.model.repsonse.NotificationInsertResponse;
import com.github.devicehive.websocket.model.request.NotificationUnsubscribeAction;
import com.github.devicehive.websocket.model.request.data.DeviceNotificationWrapper;
import okhttp3.WebSocket;
import org.joda.time.DateTime;

import java.util.List;

import static com.github.devicehive.websocket.model.ActionConstant.*;

public class NotificationWS extends BaseWebSocketApi implements NotificationApi {

    static final String TAG = "notification";
    private final NotificationListener listener;

    NotificationWS(WebSocket ws, NotificationListener listener) {
        super(ws, listener);
        this.listener = listener;
    }

    @Override
    public String getKey() {
        return TAG;
    }


    @Override
    public void onSuccess(String message) {
        com.github.devicehive.websocket.model.repsonse.ResponseAction action = getResponseAction(message);
        if (action.compareAction(NOTIFICATION_LIST)) {
            com.github.devicehive.websocket.model.repsonse.NotificationListResponse response = gson.fromJson(message, com.github.devicehive.websocket.model.repsonse.NotificationListResponse.class);
            listener.onList(response);
        } else if (action.compareAction(NOTIFICATION_GET)) {
            com.github.devicehive.websocket.model.repsonse.NotificationGetResponse response = gson.fromJson(message, com.github.devicehive.websocket.model.repsonse.NotificationGetResponse.class);
            listener.onGet(response);
        } else if (action.compareAction(NOTIFICATION_INSERT)) {
            com.github.devicehive.websocket.model.repsonse.NotificationInsertResponse response = gson.fromJson(message, NotificationInsertResponse.class);
            listener.onInsert(response);
        } else if (action.compareAction(NOTIFICATION_SUBSCRIBE)) {
            com.github.devicehive.websocket.model.repsonse.NotificationSubscribeResponse response = gson.fromJson(message, com.github.devicehive.websocket.model.repsonse.NotificationSubscribeResponse.class);
            listener.onSubscribe(response);
        } else if (action.compareAction(NOTIFICATION_UNSUBSCRIBE)) {
            listener.onUnsubscribe(action);
        } else if (action.compareAction("authenticate")) {
            com.github.devicehive.websocket.model.repsonse.ErrorResponse response = new com.github.devicehive.websocket.model.repsonse.ErrorResponse();

            response.setError(message);
            listener.onError(response);
        }

    }


    public void list(String deviceId, String notification, DateTime start, DateTime end,
                     String sortField, SortOrder sortOrder, int take, int skip) {
        list(null, deviceId, notification, start, end, sortField, sortOrder, take, skip);
    }

    public void get(String deviceId, Long notificationId) {
        get(null, deviceId, notificationId);
    }

    public void insert(String deviceId, DeviceNotificationWrapper notification) {
        insert(null, deviceId, notification);
    }

    public void subscribe(String deviceId, List<String> deviceIds, List<String> names) {
        subscribe(null, deviceId, deviceIds, names);
    }

    public void unsubscribe(String subscriptionId, List<String> deviceIds) {
        unsubscribe(null, subscriptionId, deviceIds);
    }

    @Override
    public void list(Long requestId, String deviceId, String notification, DateTime start, DateTime end,
                     String sortField, SortOrder sortOrder, int take, int skip) {
        com.github.devicehive.websocket.model.request.NotificationListAction action = new com.github.devicehive.websocket.model.request.NotificationListAction();
        action.setRequestId(requestId);
        action.setDeviceId(deviceId);
        action.setNotification(notification);
        action.setStart(start);
        action.setEnd(end);
        action.setSortField(sortField);
        action.setSortOrder(sortOrder);
        action.setTake(take);
        action.setTake(skip);
        send(action);
    }

    @Override
    public void get(Long requestId, String deviceId, Long notificationId) {
        com.github.devicehive.websocket.model.request.NotificationGetAction action = new com.github.devicehive.websocket.model.request.NotificationGetAction();
        action.setRequestId(requestId);
        action.setDeviceId(deviceId);
        action.setNotificationId(notificationId);
        send(action);
    }

    @Override
    public void insert(Long requestId, String deviceId, DeviceNotificationWrapper notification) {
        com.github.devicehive.websocket.model.request.NotificationInsertAction action = new com.github.devicehive.websocket.model.request.NotificationInsertAction();
        action.setRequestId(requestId);
        action.setDeviceId(deviceId);
        action.setNotification(notification);
        send(action);
    }

    @Override
    public void subscribe(Long requestId, String deviceId, List<String> deviceIds, List<String> names) {
        com.github.devicehive.websocket.model.request.NotificationSubscribeAction action = new com.github.devicehive.websocket.model.request.NotificationSubscribeAction();
        action.setRequestId(requestId);
        action.setDeviceId(deviceId);
        action.setDeviceIds(deviceIds);
        action.setNames(names);
        send(action);
    }

    @Override
    public void unsubscribe(Long requestId, String subscriptionId, List<String> deviceIds) {
        com.github.devicehive.websocket.model.request.NotificationUnsubscribeAction action = new NotificationUnsubscribeAction();
        action.setRequestId(requestId);
        action.setDeviceIds(deviceIds);
        action.setSubscriptionId(subscriptionId);
        send(action);
    }
}
