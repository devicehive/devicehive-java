package com.devicehive.websocket.api;

import javax.annotation.Nullable;

interface ConfigurationApi {

    void get(String name, @Nullable Long requestId);

    void put(String name, String value, @Nullable Long requestId);

    void delete(String name, @Nullable Long requestId);


}
