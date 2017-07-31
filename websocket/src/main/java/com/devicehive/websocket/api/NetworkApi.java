package com.devicehive.websocket.api;

import com.devicehive.websocket.model.request.data.NetworkUpdate;

import javax.annotation.Nullable;

interface NetworkApi {

    void list(String name, @Nullable Long requestId, String namePattern, String sortField, Boolean sortOrderAsc, Integer take, Integer skip);

    void get(Long id, @Nullable Long requestId);

    void insert(@Nullable Long requestId, NetworkUpdate networkUpdate);

    void update(@Nullable Long requestId, NetworkUpdate networkUpdate);

    void delete(@Nullable Long requestId, Long id);
}
