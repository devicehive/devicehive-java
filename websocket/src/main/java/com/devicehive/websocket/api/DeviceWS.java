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

import static com.devicehive.websocket.model.ActionConstant.DEVICE_GET;
import static com.devicehive.websocket.model.ActionConstant.DEVICE_LIST;

public class DeviceWS extends BaseWebSocketListener implements DeviceApi {

    public static final String TAG = "DeviceWS";
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
    public void onSuccess(String message) {
        ResponseAction action = getResponseAction(message);
        Gson gson = new GsonBuilder()
                .registerTypeAdapterFactory(new JsonStringWrapperAdapterFactory())
                .create();
        String actionName = action.getAction();
        if (actionName.equalsIgnoreCase(DEVICE_LIST)) {
            DeviceListResponse response = gson.fromJson(message, DeviceListResponse.class);
            deviceListener.onDeviceList(response.getDevices());
        } else if (actionName.equalsIgnoreCase(DEVICE_GET)) {
            DeviceGetResponse device = gson.fromJson(message, DeviceGetResponse.class);
            deviceListener.onDeviceGet(device.getDevice());
        }
    }

    @Override
    public void get(String deviceId, Long requestId) {
        DeviceGetAction action = new DeviceGetAction();
        action.setDeviceId(deviceId);
        action.setRequestId(requestId);
        send(action);
    }

    @Override
    public void list(String name, String namePattern, Long networkId, String networkName, Long requestId, String sortField, String sortOrder, int take, int skip) {
        DeviceListAction action = new DeviceListAction();
        action.setName(name);
        action.setNamePattern(namePattern);
        action.setNetworkId(networkId);
        action.setNetworkName(networkName);
        action.setSortField(sortField);
        action.setSortOrder(sortOrder);
        action.setTake(take);
        action.setSkip(skip);
        action.setRequestId(requestId);

        send(action);
    }

    @Override
    public void save(@NonNull DeviceVO device, Long requestId) {
        DeviceSaveAction action = new DeviceSaveAction();
        action.setDevice(device);
        action.setRequestId(requestId);
        send(action);
    }


    @Override
    public void delete(@NonNull String deviceId, Long requestId) {
        DeviceDeleteAction action = new DeviceDeleteAction();
        action.setDeviceId(deviceId);
        action.setRequestId(requestId);
        send(action);
    }
}
