package com.devicehive.websocket.api;

import com.devicehive.websocket.adapter.JsonStringWrapperAdapterFactory;
import com.devicehive.websocket.listener.ConfigurationListener;
import com.devicehive.websocket.model.repsonse.ConfigurationGetResponse;
import com.devicehive.websocket.model.repsonse.ConfigurationInsertResponse;
import com.devicehive.websocket.model.repsonse.ResponseAction;
import com.devicehive.websocket.model.request.ConfigurationDeleteAction;
import com.devicehive.websocket.model.request.ConfigurationGetAction;
import com.devicehive.websocket.model.request.ConfigurationInsertAction;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.OkHttpClient;
import okhttp3.Request;

import static com.devicehive.websocket.model.ActionConstant.CONFIGURATION_DELETE;
import static com.devicehive.websocket.model.ActionConstant.CONFIGURATION_GET;
import static com.devicehive.websocket.model.ActionConstant.CONFIGURATION_INSERT;

public class ConfigurationWS extends BaseWebSocketListener implements ConfigurationApi {

    public static final String TAG = "ConfigurationWS";
    private ConfigurationListener listener;

    public ConfigurationWS(OkHttpClient client, Request request, ConfigurationListener listener) {
        super(client, request, listener);
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
        if (actionName.equalsIgnoreCase(CONFIGURATION_GET)) {
            ConfigurationGetResponse response = gson.fromJson(message, ConfigurationGetResponse.class);
            listener.onGet(response);
        } else if (actionName.equalsIgnoreCase(CONFIGURATION_INSERT)) {
            ConfigurationInsertResponse response = gson.fromJson(message, ConfigurationInsertResponse.class);
            listener.onInsert(response);
        } else if (actionName.equalsIgnoreCase(CONFIGURATION_DELETE)) {
            ResponseAction response = gson.fromJson(message, ResponseAction.class);
            listener.onDelete(response);

        }
    }

    @Override
    public void get(String name, Long requestId) {
        ConfigurationGetAction action = new ConfigurationGetAction();
        action.setName(name);
        action.setRequestId(requestId);
        send(action);
    }

    @Override
    public void put(String name, String value, Long requestId) {
        ConfigurationInsertAction action = new ConfigurationInsertAction();
        action.setName(name);
        action.setValue(value);
        action.setRequestId(requestId);
        send(action);
    }

    @Override
    public void delete(String name, Long requestId) {
        ConfigurationDeleteAction action = new ConfigurationDeleteAction();
        action.setName(name);
        action.setRequestId(requestId);
        send(action);
    }
}
