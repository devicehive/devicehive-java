package com.devicehive.websocket.api;

import com.devicehive.websocket.adapter.JsonStringWrapperAdapterFactory;
import com.devicehive.websocket.api.listener.DeviceListener;
import com.devicehive.websocket.model.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

public class DeviceWS extends WebSocketListener implements DeviceApi {
    private final DeviceListener deviceListener;
    private WebSocket ws;
    private Gson writer = new Gson();

    public DeviceWS(OkHttpClient client, Request request, DeviceListener deviceListener) {
        ws = client.newWebSocket(request, this);
        this.deviceListener = deviceListener;
    }


    @Override
    public void onMessage(WebSocket webSocket, String text) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapterFactory(new JsonStringWrapperAdapterFactory())
                .create();
//        System.out.println(text);
        Action action = gson.fromJson(text, Action.class);
        String status = action.getStatus();
        if (status.equalsIgnoreCase("error")) {
            ErrorAction errorAction = gson.fromJson(text, ErrorAction.class);
            deviceListener.onError(errorAction);
        } else {
            String actionName = action.getAction();
            if (actionName.equalsIgnoreCase("device/list")) {
                DeviceResponse response = gson.fromJson(text, DeviceResponse.class);
                deviceListener.onDeviceList(response.getDevices());
            } else if (actionName.equalsIgnoreCase("device/get")) {
                DeviceVO deviceVO = gson.fromJson(text, DeviceVO.class);
                deviceListener.onDeviceGet(deviceVO);
            }
        }
    }

    @Override
    public void get(String deviceId) {
        DeviceGetAction action = new DeviceGetAction();
        action.setDeviceId(deviceId);
        ws.send(writer.toJson(action));
    }


    @Override
    public void list(String name, String namePattern, Long networkId,
                     String networkName, String sortField, String sortOrder, int take, int skip) {

        DeviceListAction action = new DeviceListAction();
        action.setName(name);
        action.setNamePattern(namePattern);
        action.setNetworkId(networkId);
        action.setNetworkName(networkName);
        action.setSortField(sortField);
        action.setSortOrder(sortOrder);
        action.setTake(take);
        action.setSkip(skip);
        ws.send(writer.toJson(action));
    }

    @Override
    public void save(DeviceVO device) {

    }

    @Override
    public void delete(String deviceId) {

    }
}
