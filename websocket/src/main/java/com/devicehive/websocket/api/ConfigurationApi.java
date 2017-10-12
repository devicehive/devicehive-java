package com.devicehive.websocket.api;

import javax.annotation.Nullable;

interface ConfigurationApi {

    void get( Long requestId, String name);

    void put( Long requestId, String name, String value);

    void delete( Long requestId, String name);


}
