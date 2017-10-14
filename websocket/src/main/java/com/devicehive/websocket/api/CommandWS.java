package com.devicehive.websocket.api;

import com.devicehive.websocket.listener.CommandListener;
import com.devicehive.websocket.model.SortOrder;
import com.devicehive.websocket.model.repsonse.*;
import com.devicehive.websocket.model.request.*;
import com.devicehive.websocket.model.request.data.DeviceCommandWrapper;
import com.google.gson.JsonSyntaxException;
import okhttp3.WebSocket;
import org.joda.time.DateTime;

import java.util.List;

import static com.devicehive.websocket.model.ActionConstant.*;

public class CommandWS extends BaseWebSocketApi implements CommandApi {

    static final String TAG = "command";

    private CommandListener listener;

    CommandWS(WebSocket ws, CommandListener listener) {
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
        if (action.compareAction(COMMAND_LIST)) {
            CommandListResponse response = gson.fromJson(message, CommandListResponse.class);
            listener.onList(response);
        } else if (action.compareAction(COMMAND_GET)) {
            CommandGetResponse response = gson.fromJson(message, CommandGetResponse.class);
            listener.onGet(response);
        } else if (action.compareAction(COMMAND_INSERT)) {
            try {
                CommandInsertResponse response = gson.fromJson(message, CommandInsertResponse.class);
                listener.onInsert(response);
            } catch (JsonSyntaxException e) {
                e.printStackTrace();
            }

        } else if (action.compareAction(COMMAND_SUBSCRIBE)) {
            CommandSubscribeResponse response = gson.fromJson(message, CommandSubscribeResponse.class);
            listener.onSubscribe(response);
        } else if (action.compareAction(COMMAND_UNSUBSCRIBE)) {
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
        DeviceGetAction action = new DeviceGetAction();
        action.setDeviceId(deviceId);
        action.setCommandId(commandId);
        action.setRequestId(requestId);
        send(action);
    }

    @Override
    public void list(Long requestId, String deviceId, DateTime start, DateTime end, String commandName, String status, SortOrder sortOrder, Integer take, Integer skip) {
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
    public void subscribe(Long requestId, List<String> names, String deviceId, List<String> deviceIds, DateTime timestamp, Integer limit) {
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
