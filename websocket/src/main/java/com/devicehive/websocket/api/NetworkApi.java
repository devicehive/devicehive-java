package com.devicehive.websocket.api;

import com.devicehive.websocket.model.request.data.NetworkUpdate;

import javax.annotation.Nullable;

interface NetworkApi {

    void list( Long requestId, String name, String namePattern, String sortField, Boolean sortOrderAsc, Integer take, Integer skip);

    void get( Long requestId, Long id);

    void insert( Long requestId, NetworkUpdate networkUpdate);

    void update( Long requestId, NetworkUpdate networkUpdate);

    void delete( Long requestId, Long id);
}
