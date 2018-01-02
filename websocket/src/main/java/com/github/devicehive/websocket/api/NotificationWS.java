/*
 *
 *
 *   NotificationWS.java
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

package com.github.devicehive.websocket.api;

import com.github.devicehive.rest.model.DeviceNotificationWrapper;
import com.github.devicehive.rest.model.SortOrder;
import com.github.devicehive.websocket.listener.NotificationListener;
import com.github.devicehive.websocket.model.repsonse.*;
import com.github.devicehive.websocket.model.request.*;
import org.joda.time.DateTime;

import java.util.List;

import static com.github.devicehive.websocket.model.ActionConstant.*;

public class NotificationWS extends BaseWebSocketApi implements NotificationApi {

    static final String TAG = "notification";
    private NotificationListener listener;

    NotificationWS(WebSocketClient client) {
        super(client, null);
    }

    public void setListener(NotificationListener listener) {
        super.setListener(listener);
        this.listener = listener;
    }

    @Override
    public String getKey() {
        return TAG;
    }


    @Override
    public void onSuccess(String message) {
        ResponseAction action = getResponseAction(message);
        if (action.compareAction(NOTIFICATION_LIST)) {
            NotificationListResponse response = gson.fromJson(message, NotificationListResponse.class);
            listener.onList(response);
        } else if (action.compareAction(NOTIFICATION_GET)) {
            NotificationGetResponse response = gson.fromJson(message, NotificationGetResponse.class);
            listener.onGet(response);
        } else if (action.compareAction(NOTIFICATION_INSERT)) {
            NotificationInsertResponse response = gson.fromJson(message, NotificationInsertResponse.class);
            listener.onInsert(response);
        } else if (action.compareAction(NOTIFICATION_SUBSCRIBE)) {
            NotificationSubscribeResponse response = gson.fromJson(message, NotificationSubscribeResponse.class);
            listener.onSubscribe(response);
        } else if (action.compareAction(NOTIFICATION_UNSUBSCRIBE)) {
            listener.onUnsubscribe(action);
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
        NotificationListAction action = new NotificationListAction();
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
        NotificationGetAction action = new NotificationGetAction();
        action.setRequestId(requestId);
        action.setDeviceId(deviceId);
        action.setNotificationId(notificationId);
        send(action);
    }

    @Override
    public void insert(Long requestId, String deviceId, DeviceNotificationWrapper notification) {
        NotificationInsertAction action = new NotificationInsertAction();
        action.setRequestId(requestId);
        action.setDeviceId(deviceId);
        action.setNotification(notification);
        send(action);
    }

    @Override
    public void subscribe(Long requestId, String deviceId, List<String> deviceIds, List<String> names) {
        NotificationSubscribeAction action = new NotificationSubscribeAction();
        action.setRequestId(requestId);
        action.setDeviceId(deviceId);
        action.setDeviceIds(deviceIds);
        action.setNames(names);
        send(action);
    }

    @Override
    public void unsubscribe(Long requestId, String subscriptionId, List<String> deviceIds) {
        NotificationUnsubscribeAction action = new NotificationUnsubscribeAction();
        action.setRequestId(requestId);
        action.setDeviceIds(deviceIds);
        action.setSubscriptionId(subscriptionId);
        send(action);
    }
}
