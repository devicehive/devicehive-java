package com.devicehive.websocket.api;

import com.devicehive.websocket.listener.CommandListener;
import com.devicehive.websocket.model.request.*;
import com.devicehive.websocket.model.request.data.DeviceCommandWrapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.joda.time.DateTime;

import java.util.List;

public class CommandWS extends BaseWebSocketListener implements CommandApi {

    public static final String TAG = "CommandWS";

    private final CommandListener listener;

    public CommandWS(OkHttpClient client, Request request, CommandListener listener) {
        super(client, request, listener);
        this.listener = listener;
    }

    @Override
    public String getKey() {
        return TAG;
    }

    @Override
    public void onSuccess(String message) {
        //TODO CommandParsing Handling add


    }

    @Override
    public void get(String deviceId, Long commandId, Long requestId) {
        DeviceGetAction action = new DeviceGetAction();
        action.setDeviceId(deviceId);
        action.setCommandId(commandId);
        action.setRequestId(requestId);
        send(action);
    }

    @Override
    public void list(String deviceId, Long requestId, DateTime start, DateTime end, String commandName, String status, CommandListAction.SortOrder sortOrder, Integer take, Integer skip) {
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
    public void insert(String deviceId, Long requestId, DeviceCommandWrapper wrapper) {
        CommandInsertAction action = new CommandInsertAction();
        action.setCommand(wrapper);
        action.setDeviceId(deviceId);
        action.setRequestId(requestId);
        send(action);
    }

    @Override
    public void update(String deviceId, String commandId, Long requestId, DeviceCommandWrapper wrapper) {
        CommandUpdateAction action = new CommandUpdateAction();
        action.setDeviceId(deviceId);
        action.setCommandId(commandId);
        action.setCommand(wrapper);
        action.setRequestId(requestId);
        send(action);
    }

    @Override
    public void subscribe(List<String> names, String deviceId, Long requestId, List<String> deviceIds, DateTime timestamp, Integer limit) {
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
    public void unsubscribe(String subscriptionId, Long requestId, List<String> deviceIds) {
        CommandUnsubscribeAction action = new CommandUnsubscribeAction();
        action.setSubscriptionId(subscriptionId);
        action.setDeviceIds(deviceIds);
        action.setRequestId(requestId);
        send(action);
    }

}
