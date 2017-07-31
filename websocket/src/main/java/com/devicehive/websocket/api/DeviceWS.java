package com.devicehive.websocket.api;

import com.devicehive.websocket.adapter.JsonStringWrapperAdapterFactory;
import com.devicehive.websocket.listener.DeviceListener;
import com.devicehive.websocket.model.repsonse.DeviceGetResponse;
import com.devicehive.websocket.model.repsonse.DeviceListResponse;
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

import static com.devicehive.websocket.model.request.DeviceGetAction.DEVICE_GET;
import static com.devicehive.websocket.model.request.DeviceListAction.DEVICE_LIST;

public class DeviceWS extends BaseWebSocketListener implements DeviceApi {

    public static final String TAG="DeviceWS";
    private final DeviceListener deviceListener;

    public DeviceWS(OkHttpClient client, Request request, DeviceListener listener) {
        super(client, request, listener);
        this.deviceListener = listener;
    }
    @Override
    public String getKey() {
        return TAG;
    }


    @Override
    public void onSuccess(String text) {
        System.out.println("continue");
        ResponseAction action = getResponseAction(text);
        Gson gson = new GsonBuilder()
                .registerTypeAdapterFactory(new JsonStringWrapperAdapterFactory())
                .create();
        String actionName = action.getAction();
        if (actionName.equalsIgnoreCase(DEVICE_LIST)) {
            DeviceListResponse response = gson.fromJson(text, DeviceListResponse.class);
            deviceListener.onDeviceList(response.getDevices());
        } else if (actionName.equalsIgnoreCase(DEVICE_GET)) {
            DeviceGetResponse device = gson.fromJson(text, DeviceGetResponse.class);
            deviceListener.onDeviceGet(device.getDevice());
        }
    }

    @Override
    public void get(String deviceId) {
        DeviceGetAction action = new DeviceGetAction();
        action.setDeviceId(deviceId);
        send(action);
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

        send(deviceListAction);
    }

    @Override
    public void save(@NonNull DeviceVO device) {
        DeviceSaveAction action = new DeviceSaveAction();
        action.setDevice(device);
        send(action);
    }


    @Override
    public void delete(@NonNull String deviceId) {
        DeviceDeleteAction deleteAction = new DeviceDeleteAction();
        deleteAction.setDeviceId(deviceId);
        send(deleteAction);
    }
}
