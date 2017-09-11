package com.devicehive.websocket.api;

import javax.annotation.Nullable;

interface ConfigurationApi {

    void get(@Nullable Long requestId, String name);

    void put(@Nullable Long requestId, String name, String value);

    void delete(@Nullable Long requestId, String name);


}
