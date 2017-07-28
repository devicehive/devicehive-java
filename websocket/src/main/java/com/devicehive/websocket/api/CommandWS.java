package com.devicehive.websocket.api;

import com.devicehive.websocket.listener.CommandListener;
import com.devicehive.websocket.model.request.CommandListAction;
import com.devicehive.websocket.model.request.data.DeviceCommandWrapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;
import org.joda.time.DateTime;

import java.util.List;

public class CommandWS extends BaseWebSocketListener implements CommandApi {


    private final CommandListener listener;

    public CommandWS(OkHttpClient client, Request request, CommandListener listener) {
        super(client, request);
        this.listener = listener;
    }

    @Override
    public void onMessage(WebSocket webSocket, String text) {


    }

    @Override
    public void get(String deviceId, String commandId) {

    }

    @Override
    public void list(String deviceId, DateTime start, DateTime end, String commandName, String status, CommandListAction.SortOrder sortOrder, Integer take, Integer skip) {

    }

    @Override
    public void insert(String deviceId, DeviceCommandWrapper wrapper) {

    }

    @Override
    public void update(String deviceId, String commandId, DeviceCommandWrapper wrapper) {

    }

    @Override
    public void subscribe(List<String> names, String deviceId, List<String> deviceIds, DateTime timestamp, Integer limit) {

    }

    @Override
    public void unsubscribe(String subscriptionId, List<String> deviceIds) {

    }

}
