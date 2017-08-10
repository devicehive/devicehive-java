package com.devicehive.websocket.api;

import com.devicehive.websocket.adapter.JsonStringWrapperAdapterFactory;
import com.devicehive.websocket.listener.NotificationListener;
import com.devicehive.websocket.model.SortOrder;
import com.devicehive.websocket.model.repsonse.*;
import com.devicehive.websocket.model.request.*;
import com.devicehive.websocket.model.request.data.DeviceNotificationWrapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.WebSocket;
import org.joda.time.DateTime;

import javax.annotation.Nullable;
import java.util.List;

import static com.devicehive.websocket.model.ActionConstant.*;

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
        ResponseAction action = getResponseAction(message);
        Gson gson = new GsonBuilder()
                .registerTypeAdapterFactory(new JsonStringWrapperAdapterFactory())
                .create();
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
        } else if (action.compareAction("authenticate")) {
            ErrorResponse response = new ErrorResponse();

            response.setError(message);
            listener.onError(response);
        }

    }

    @Override
    public void list(@Nullable Long requestId, String deviceId, String notification, DateTime start, DateTime end,
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
    public void get(@Nullable Long requestId, String deviceId, Long notificationId) {
        NotificationGetAction action = new NotificationGetAction();
        action.setRequestId(requestId);
        action.setDeviceId(deviceId);
        action.setNotificationId(notificationId);
        send(action);
    }

    @Override
    public void insert(@Nullable Long requestId, String deviceId, DeviceNotificationWrapper notification) {
        NotificationInsertAction action = new NotificationInsertAction();
        action.setRequestId(requestId);
        action.setDeviceId(deviceId);
        action.setNotification(notification);
        send(action);
    }

    @Override
    public void subscribe(@Nullable Long requestId, String deviceId, List<String> deviceIds, List<String> names) {
        NotificationSubscribeAction action = new NotificationSubscribeAction();
        action.setRequestId(requestId);
        action.setDeviceId(deviceId);
        action.setDeviceIds(deviceIds);
        action.setNames(names);
        send(action);
    }

    @Override
    public void unsubscribe(@Nullable Long requestId, List<String> deviceIds, String subscriptionId) {
        NotificationUnsubscribeAction action = new NotificationUnsubscribeAction();
        action.setRequestId(requestId);
        action.setDeviceIds(deviceIds);
        action.setSubscriptionId(subscriptionId);
        send(action);
    }
}
