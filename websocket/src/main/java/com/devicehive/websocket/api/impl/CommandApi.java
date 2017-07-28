package com.devicehive.websocket.api.impl;

import com.devicehive.websocket.model.request.CommandListAction;
import com.devicehive.websocket.model.request.data.DeviceCommandWrapper;
import org.joda.time.DateTime;

import java.util.List;

public interface CommandApi {


    void get(String deviceId, String commandId);

    void list(String deviceId, DateTime start, DateTime end, String commandName, String status,
              CommandListAction.SortOrder sortOrder, Integer take, Integer skip);

    void insert(String deviceId, DeviceCommandWrapper wrapper);

    void update(String deviceId, String commandId, DeviceCommandWrapper wrapper);

    void subscribe(List<String> names, String deviceId, List<String> deviceIds, DateTime timestamp, Integer limit);

    void unsubscribe(String subscriptionId, List<String> deviceIds);


}
