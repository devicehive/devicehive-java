package com.devicehive.websocket.model;

import com.devicehive.rest.adapters.DateTimeTypeAdapter;
import com.devicehive.rest.adapters.JsonStringWrapperAdapterFactory;
import com.devicehive.websocket.Const;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.joda.time.DateTime;

public class GsonHelper {

    private final Gson gson;

    private GsonHelper() {
        gson = new GsonBuilder()
                .registerTypeAdapterFactory(new JsonStringWrapperAdapterFactory())
                .registerTypeAdapter(DateTime.class, new DateTimeTypeAdapter(Const.TIMESTAMP_FORMAT))
                .create();
    }

    private static class InstanceHolder {
        static final GsonHelper INSTANCE = new GsonHelper();
    }

    public static GsonHelper getInstance() {
        return GsonHelper.InstanceHolder.INSTANCE;
    }

    public Gson getGsonFactory() {
        return gson;
    }
}
