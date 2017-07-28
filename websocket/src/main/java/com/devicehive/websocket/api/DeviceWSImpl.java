package com.devicehive.websocket.api;

import com.devicehive.websocket.adapter.JsonStringWrapperAdapterFactory;
import com.devicehive.websocket.api.listener.DeviceListener;
import com.devicehive.websocket.model.repsonse.DeviceGetResponse;
import com.devicehive.websocket.model.repsonse.DeviceListResponse;
import com.devicehive.websocket.model.repsonse.ErrorAction;
import com.devicehive.websocket.model.repsonse.ResponseAction;
import com.devicehive.websocket.model.repsonse.data.DeviceVO;
import com.devicehive.websocket.model.request.DeviceDeleteAction;
import com.devicehive.websocket.model.request.DeviceGetAction;
import com.devicehive.websocket.model.request.DeviceListAction;
import com.devicehive.websocket.model.request.DeviceSaveAction;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.NonNull;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

public class DeviceWSImpl extends WebSocketListener implements DeviceApi {
    private final DeviceListener deviceListener;
    private WebSocket ws;
    private Gson writer = new Gson();

    public DeviceWSImpl(OkHttpClient client, Request request, DeviceListener deviceListener) {
        ws = client.newWebSocket(request, this);
        this.deviceListener = deviceListener;
    }


    @Override
    public void onMessage(WebSocket webSocket, String text) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapterFactory(new JsonStringWrapperAdapterFactory())
                .create();
//        System.out.println(text);
        ResponseAction action = gson.fromJson(text, ResponseAction.class);
        String status = action.getStatus();
        if (status.equalsIgnoreCase("error")) {
            System.out.println(text);
            ErrorAction errorAction = gson.fromJson(text, ErrorAction.class);
            deviceListener.onError(errorAction);
        } else {
            String actionName = action.getAction();
            if (actionName.equalsIgnoreCase("device/list")) {
                DeviceListResponse response = gson.fromJson(text, DeviceListResponse.class);
                deviceListener.onDeviceList(response.getDevices());
            } else if (actionName.equalsIgnoreCase("device/get")) {
                DeviceGetResponse device = gson.fromJson(text, DeviceGetResponse.class);
                deviceListener.onDeviceGet(device.getDevice());
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

        DeviceListAction deviceListAction = new DeviceListAction();
        deviceListAction.setName(name);
        deviceListAction.setNamePattern(namePattern);
        deviceListAction.setNetworkId(networkId);
        deviceListAction.setNetworkName(networkName);
        deviceListAction.setSortField(sortField);
        deviceListAction.setSortOrder(sortOrder);
        deviceListAction.setTake(take);
        deviceListAction.setSkip(skip);

        ws.send(writer.toJson(deviceListAction));
    }

    @Override
    public void save(@NonNull DeviceVO device) {
        DeviceSaveAction action = new DeviceSaveAction();
        action.setDevice(device);
        ws.send(writer.toJson(action));
    }


    @Override
    public void delete(@NonNull String deviceId) {
        DeviceDeleteAction deleteAction = new DeviceDeleteAction();
        deleteAction.setDeviceId(deviceId);
        ws.send(writer.toJson(deleteAction));
    }
}
