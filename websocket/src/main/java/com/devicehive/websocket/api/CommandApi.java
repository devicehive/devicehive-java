package com.devicehive.websocket.api;

import com.devicehive.websocket.model.SortOrder;
import com.devicehive.websocket.model.request.data.DeviceCommandWrapper;
import org.joda.time.DateTime;

import javax.annotation.Nullable;
import java.util.List;

interface CommandApi {


    void get(@Nullable Long requestId, String deviceId, Long commandId);

    void list(@Nullable Long requestId, String deviceId, DateTime start, DateTime end, String commandName, String status,
              SortOrder sortOrder, Integer take, Integer skip);

    void insert(@Nullable Long requestId, String deviceId, DeviceCommandWrapper wrapper);

    void update(@Nullable Long requestId, String deviceId, String commandId, DeviceCommandWrapper wrapper);

    void subscribe(@Nullable Long requestId, List<String> names, String deviceId, List<String> deviceIds, DateTime timestamp, Integer limit);

    void unsubscribe(@Nullable Long requestId, String subscriptionId, List<String> deviceIds);


}
