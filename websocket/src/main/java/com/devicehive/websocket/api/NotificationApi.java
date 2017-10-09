package com.devicehive.websocket.api;

import com.devicehive.websocket.model.SortOrder;
import com.devicehive.websocket.model.request.data.DeviceNotificationWrapper;
import org.joda.time.DateTime;

import java.util.List;

interface NotificationApi {

    void list(Long requestId, String deviceId, String notification, DateTime start, DateTime end,
              String sortField, SortOrder sortOrder, int take, int skip);

    void get(Long requestId, String deviceId, Long notificationId);

    void insert(Long requestId, String deviceId, DeviceNotificationWrapper notification);

    void subscribe(Long requestId, String deviceId, List<String> deviceIds, List<String> names);

    void unsubscribe(Long requestId, String subscriptionId, List<String> deviceIds);
}
