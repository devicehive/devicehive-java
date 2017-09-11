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
import okhttp3.WebSocket;

import javax.annotation.Nullable;

import static com.devicehive.websocket.model.ActionConstant.*;

public class DeviceWS extends BaseWebSocketApi implements DeviceApi {

    static final String TAG = "device";
    private final DeviceListener listener;

    DeviceWS(WebSocket ws, DeviceListener listener) {
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
        String actionName = action.getAction();
        if (actionName.equalsIgnoreCase(DEVICE_LIST)) {
            DeviceListResponse response = gson.fromJson(message, DeviceListResponse.class);
            listener.onList(response.getDevices());
        } else if (actionName.equalsIgnoreCase(DEVICE_GET)) {
            DeviceGetResponse response = gson.fromJson(message, DeviceGetResponse.class);
            listener.onGet(response.getDevice());
        } else if (actionName.equalsIgnoreCase(DEVICE_DELETE)) {
            listener.onDelete(action);
        } else if (actionName.equalsIgnoreCase(DEVICE_SAVE)) {
            listener.onSave(action);
        }
    }

    @Override
    public void get(@Nullable Long requestId, String deviceId) {
        DeviceGetAction action = new DeviceGetAction();
        action.setDeviceId(deviceId);
        action.setRequestId(requestId);
        send(action);
    }

    @Override
    public void list(@Nullable Long requestId, String name, String namePattern, Long networkId, String networkName, String sortField, String sortOrder, int take, int skip) {
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
    public void save(@Nullable Long requestId, @NonNull DeviceVO device) {
        DeviceSaveAction action = new DeviceSaveAction();
        action.setDevice(device);
        action.setRequestId(requestId);
        send(action);
    }


    @Override
    public void delete(@Nullable Long requestId, @NonNull String deviceId) {
        DeviceDeleteAction action = new DeviceDeleteAction();
        action.setDeviceId(deviceId);
        action.setRequestId(requestId);
        send(action);
    }
}
