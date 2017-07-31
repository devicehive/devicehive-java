package com.devicehive.websocket.api;

import com.devicehive.websocket.listener.ErrorListener;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class ConfigurationWS extends BaseWebSocketListener implements ConfigurationApi {

    public static final String TAG = "ConfigurationWS";

    public ConfigurationWS(OkHttpClient client, Request request, ErrorListener listener) {
        super(client, request, listener);
    }

    @Override
    public String getKey() {
        return null;
    }

    @Override
    public void onSuccess(String message) {

    }

    @Override
    public void get() {

    }

    @Override
    public void put() {

    }

    @Override
    public void delete() {

    }
}
