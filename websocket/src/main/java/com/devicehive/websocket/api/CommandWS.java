package com.devicehive.websocket.api;

import com.devicehive.websocket.adapter.JsonStringWrapperAdapterFactory;
import com.devicehive.websocket.listener.CommandListener;
import com.devicehive.websocket.model.SortOrder;
import com.devicehive.websocket.model.repsonse.*;
import com.devicehive.websocket.model.request.*;
import com.devicehive.websocket.model.request.data.DeviceCommandWrapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.WebSocket;
import org.joda.time.DateTime;

import javax.annotation.Nullable;
import java.util.List;

import static com.devicehive.websocket.model.ActionConstant.*;

public class CommandWS extends BaseWebSocketApi implements CommandApi {

    static final String TAG = "command";

    private final CommandListener listener;

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
        Gson gson = new GsonBuilder()
                .registerTypeAdapterFactory(new JsonStringWrapperAdapterFactory())
                .create();
        if (action.compareAction(COMMAND_LIST)) {
            CommandListResponse response = gson.fromJson(message, CommandListResponse.class);
            listener.onList(response);
        } else if (action.compareAction(COMMAND_GET)) {
            CommandGetResponse response = gson.fromJson(message, CommandGetResponse.class);
            listener.onGet(response);
        } else if (action.compareAction(COMMAND_INSERT)) {
            CommandInsertResponse response = gson.fromJson(message, CommandInsertResponse.class);
            listener.onInsert(response);
        } else if (action.compareAction(COMMAND_SUBSCRIBE)) {
            CommandSubscribeResponse response = gson.fromJson(message, CommandSubscribeResponse.class);
            listener.onSubscribe(response);
        } else if (action.compareAction(COMMAND_UNSUBSCRIBE)) {
            listener.onUnsubscribe(action);
        }

    }

    @Override
    public void get(@Nullable Long requestId, String deviceId, Long commandId) {
        DeviceGetAction action = new DeviceGetAction();
        action.setDeviceId(deviceId);
        action.setCommandId(commandId);
        action.setRequestId(requestId);
        send(action);
    }

    @Override
    public void list(@Nullable Long requestId, String deviceId, DateTime start, DateTime end, String commandName, String status, SortOrder sortOrder, Integer take, Integer skip) {
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
    public void insert(@Nullable Long requestId, String deviceId, DeviceCommandWrapper wrapper) {
        CommandInsertAction action = new CommandInsertAction();
        action.setCommand(wrapper);
        action.setDeviceId(deviceId);
        action.setRequestId(requestId);
        send(action);
    }

    @Override
    public void update(@Nullable Long requestId, String deviceId, String commandId, DeviceCommandWrapper wrapper) {
        CommandUpdateAction action = new CommandUpdateAction();
        action.setDeviceId(deviceId);
        action.setCommandId(commandId);
        action.setCommand(wrapper);
        action.setRequestId(requestId);
        send(action);
    }

    @Override
    public void subscribe(@Nullable Long requestId, List<String> names, String deviceId, List<String> deviceIds, DateTime timestamp, Integer limit) {
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
    public void unsubscribe(@Nullable Long requestId, String subscriptionId, List<String> deviceIds) {
        CommandUnsubscribeAction action = new CommandUnsubscribeAction();
        action.setSubscriptionId(subscriptionId);
        action.setDeviceIds(deviceIds);
        action.setRequestId(requestId);
        send(action);
    }

}
