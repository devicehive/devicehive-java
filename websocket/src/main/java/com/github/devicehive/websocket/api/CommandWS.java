/*
 *
 *
 *   CommandWS.java
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

import com.github.devicehive.rest.model.SortOrder;
import com.github.devicehive.websocket.listener.CommandListener;
import com.github.devicehive.websocket.model.ActionConstant;
import com.github.devicehive.websocket.model.repsonse.*;
import com.github.devicehive.websocket.model.request.*;
import com.github.devicehive.rest.model.DeviceCommandWrapper;
import com.google.gson.JsonSyntaxException;
import org.joda.time.DateTime;

import java.util.List;

public class CommandWS extends BaseWebSocketApi implements CommandApi {

    static final String TAG = "command";

    private CommandListener listener;

    CommandWS(WebSocketClient client) {
        super(client, null);
    }

    public void setListener(CommandListener listener) {
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
        if (action.compareAction(ActionConstant.COMMAND_LIST)) {
            CommandListResponse response = gson.fromJson(message, CommandListResponse.class);
            listener.onList(response);
        } else if (action.compareAction(ActionConstant.COMMAND_GET)) {
            CommandGetResponse response = gson.fromJson(message, CommandGetResponse.class);
            listener.onGet(response);
        } else if (action.compareAction(ActionConstant.COMMAND_INSERT)) {
            try {
                CommandInsertResponse response = gson.fromJson(message, CommandInsertResponse.class);
                listener.onInsert(response);
            } catch (JsonSyntaxException e) {
                e.printStackTrace();
            }

        } else if (action.compareAction(ActionConstant.COMMAND_SUBSCRIBE)) {
            CommandSubscribeResponse response = gson.fromJson(message, CommandSubscribeResponse.class);
            listener.onSubscribe(response);
        } else if (action.compareAction(ActionConstant.COMMAND_UNSUBSCRIBE)) {
            listener.onUnsubscribe(action);
        }

    }

    public void get(String deviceId, Long commandId) {
        get(null, deviceId, commandId);
    }

    public void list(String deviceId, DateTime start, DateTime end, String commandName, String status,
                     SortOrder sortOrder, Integer take, Integer skip) {
        list(null, deviceId, start, end, commandName, status, sortOrder, take, skip);
    }

    public void insert(String deviceId, DeviceCommandWrapper wrapper) {
        insert(null, deviceId, wrapper);
    }

    public void update(String deviceId, String commandId, DeviceCommandWrapper wrapper) {
        update(null, deviceId, commandId, wrapper);
    }

    public void subscribe(List<String> names, String deviceId, List<String> deviceIds, DateTime timestamp, Integer limit) {
        subscribe(null, names, deviceId, deviceIds, timestamp, limit);
    }

    public void unsubscribe(String subscriptionId, List<String> deviceIds) {
        unsubscribe(null, subscriptionId, deviceIds);
    }

    @Override
    public void get(Long requestId, String deviceId, Long commandId) {
        CommandGetAction action = new CommandGetAction();
        action.setDeviceId(deviceId);
        action.setCommandId(commandId);
        action.setRequestId(requestId);
        send(action);
    }

    @Override
    public void list(Long requestId, String deviceId, DateTime start, DateTime end, String commandName,
                     String status, SortOrder sortOrder, Integer take, Integer skip) {
        CommandListAction action = new CommandListAction();
        action.setDeviceId(deviceId);
        action.setStart(start);
        action.setEnd(end);
        action.setCommandName(commandName);
        action.setStatus(status);
        action.setSortOrder(sortOrder);
        action.setTake(take);
        action.setSkip(skip);
        action.setRequestId(requestId);
        send(action);
    }

    @Override
    public void insert(Long requestId, String deviceId, DeviceCommandWrapper wrapper) {
        CommandInsertAction action = new CommandInsertAction();
        action.setCommand(wrapper);
        action.setDeviceId(deviceId);
        action.setRequestId(requestId);
        send(action);
    }

    @Override
    public void update(Long requestId, String deviceId, String commandId, DeviceCommandWrapper wrapper) {
        CommandUpdateAction action = new CommandUpdateAction();
        action.setDeviceId(deviceId);
        action.setCommandId(commandId);
        action.setCommand(wrapper);
        action.setRequestId(requestId);
        send(action);
    }

    @Override
    public void subscribe(Long requestId, List<String> names, String deviceId,
                          List<String> deviceIds, DateTime timestamp, Integer limit) {
        CommandSubscribeAction action = new CommandSubscribeAction();

        action.setNames(names);
        action.setDeviceId(deviceId);
        action.setDeviceIds(deviceIds);
        action.setTimestamp(timestamp);
        action.setLimit(limit);
        action.setRequestId(requestId);
        send(action);
    }

    @Override
    public void unsubscribe(Long requestId, String subscriptionId, List<String> deviceIds) {
        CommandUnsubscribeAction action = new CommandUnsubscribeAction();
        action.setSubscriptionId(subscriptionId);
        action.setDeviceIds(deviceIds);
        action.setRequestId(requestId);
        send(action);
    }

}
