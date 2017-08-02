package com.devicehive.websocket.api;

import com.devicehive.websocket.model.SortOrder;
import com.devicehive.websocket.model.request.data.DeviceNotificationWrapper;
import org.joda.time.DateTime;

import javax.annotation.Nullable;
import java.util.List;

public interface NotificationApi {

    void list(@Nullable Long requestId, String deviceId, String notification, DateTime start, DateTime end,
              String sortField, SortOrder sortOrder, int take, int skip);

    void get(@Nullable Long requestId, String deviceId, Long notificationId);

    void insert(@Nullable Long requestId, String deviceId, DeviceNotificationWrapper notification);

    void subscribe(@Nullable Long requestId, String deviceId, List<String> deviceIds, List<String> names);

    void unsubscribe(@Nullable Long requestId, List<String> deviceIds, String subscriptionId);
}
