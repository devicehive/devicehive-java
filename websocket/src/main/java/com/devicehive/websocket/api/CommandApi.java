package com.devicehive.websocket.api;

import com.devicehive.websocket.model.request.CommandListAction;
import com.devicehive.websocket.model.request.data.DeviceCommandWrapper;
import org.joda.time.DateTime;

import javax.annotation.Nullable;
import java.util.List;

interface CommandApi {


    void get(String deviceId, Long commandId, @Nullable Long requestId);

    void list(String deviceId, @Nullable Long requestId, DateTime start, DateTime end, String commandName, String status,
              CommandListAction.SortOrder sortOrder, Integer take, Integer skip);

    void insert(String deviceId,@Nullable  Long requestId, DeviceCommandWrapper wrapper);

    void update(String deviceId, String commandId, @Nullable Long requestId, DeviceCommandWrapper wrapper);

    void subscribe(List<String> names, String deviceId, @Nullable Long requestId, List<String> deviceIds, DateTime timestamp, Integer limit);

    void unsubscribe(String subscriptionId, @Nullable  Long requestId,List<String> deviceIds);


}
