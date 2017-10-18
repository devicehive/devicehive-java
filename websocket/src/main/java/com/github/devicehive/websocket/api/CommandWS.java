/*
 *
 *
 *   CommandWS.java
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

import com.github.devicehive.websocket.model.SortOrder;
import com.github.devicehive.websocket.model.repsonse.CommandListResponse;
import com.github.devicehive.websocket.model.request.CommandInsertAction;
import com.github.devicehive.websocket.model.ActionConstant;
import com.google.gson.JsonSyntaxException;
import okhttp3.WebSocket;
import org.joda.time.DateTime;

import java.util.List;

public class CommandWS extends BaseWebSocketApi implements CommandApi {

    static final String TAG = "command";

    private com.github.devicehive.websocket.listener.CommandListener listener;

    CommandWS(WebSocket ws, com.github.devicehive.websocket.listener.CommandListener listener) {
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
        if (action.compareAction(ActionConstant.COMMAND_LIST)) {
            com.github.devicehive.websocket.model.repsonse.CommandListResponse response = gson.fromJson(message, CommandListResponse.class);
            listener.onList(response);
        } else if (action.compareAction(ActionConstant.COMMAND_GET)) {
            com.github.devicehive.websocket.model.repsonse.CommandGetResponse response = gson.fromJson(message, com.github.devicehive.websocket.model.repsonse.CommandGetResponse.class);
            listener.onGet(response);
        } else if (action.compareAction(ActionConstant.COMMAND_INSERT)) {
            try {
                com.github.devicehive.websocket.model.repsonse.CommandInsertResponse response = gson.fromJson(message, com.github.devicehive.websocket.model.repsonse.CommandInsertResponse.class);
                listener.onInsert(response);
            } catch (JsonSyntaxException e) {
                e.printStackTrace();
            }

        } else if (action.compareAction(ActionConstant.COMMAND_SUBSCRIBE)) {
            com.github.devicehive.websocket.model.repsonse.CommandSubscribeResponse response = gson.fromJson(message, com.github.devicehive.websocket.model.repsonse.CommandSubscribeResponse.class);
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

    public void insert(String deviceId, com.github.devicehive.websocket.model.request.data.DeviceCommandWrapper wrapper) {
        insert(null, deviceId, wrapper);
    }

    public void update(String deviceId, String commandId, com.github.devicehive.websocket.model.request.data.DeviceCommandWrapper wrapper) {
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
        com.github.devicehive.websocket.model.request.DeviceGetAction action = new com.github.devicehive.websocket.model.request.DeviceGetAction();
        action.setDeviceId(deviceId);
        action.setCommandId(commandId);
        action.setRequestId(requestId);
        send(action);
    }

    @Override
    public void list(Long requestId, String deviceId, DateTime start, DateTime end, String commandName, String status, SortOrder sortOrder, Integer take, Integer skip) {
        com.github.devicehive.websocket.model.request.CommandListAction action = new com.github.devicehive.websocket.model.request.CommandListAction();
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
    public void insert(Long requestId, String deviceId, com.github.devicehive.websocket.model.request.data.DeviceCommandWrapper wrapper) {
        com.github.devicehive.websocket.model.request.CommandInsertAction action = new CommandInsertAction();
        action.setCommand(wrapper);
        action.setDeviceId(deviceId);
        action.setRequestId(requestId);
        send(action);
    }

    @Override
    public void update(Long requestId, String deviceId, String commandId, com.github.devicehive.websocket.model.request.data.DeviceCommandWrapper wrapper) {
        com.github.devicehive.websocket.model.request.CommandUpdateAction action = new com.github.devicehive.websocket.model.request.CommandUpdateAction();
        action.setDeviceId(deviceId);
        action.setCommandId(commandId);
        action.setCommand(wrapper);
        action.setRequestId(requestId);
        send(action);
    }

    @Override
    public void subscribe(Long requestId, List<String> names, String deviceId, List<String> deviceIds, DateTime timestamp, Integer limit) {
        com.github.devicehive.websocket.model.request.CommandSubscribeAction action = new com.github.devicehive.websocket.model.request.CommandSubscribeAction();

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
        com.github.devicehive.websocket.model.request.CommandUnsubscribeAction action = new com.github.devicehive.websocket.model.request.CommandUnsubscribeAction();
        action.setSubscriptionId(subscriptionId);
        action.setDeviceIds(deviceIds);
        action.setRequestId(requestId);
        send(action);
    }

}