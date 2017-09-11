package com.devicehive.websocket.api;

import com.devicehive.websocket.model.RoleEnum;
import com.devicehive.websocket.model.SortOrder;
import com.devicehive.websocket.model.StatusEnum;
import com.devicehive.websocket.model.request.data.User;
import com.devicehive.websocket.model.request.data.UserUpdate;

import javax.annotation.Nullable;

interface UserApi {

    void list(@Nullable Long requestId, String login, String loginPattern, StatusEnum status, RoleEnum role,
              String sortField, SortOrder sortOrder, Integer take, Integer skip);

    void get(@Nullable Long requestId, Long userId);

    void insert(@Nullable Long requestId, User user);

    void update(@Nullable Long requestId, Long userId, UserUpdate user);

    void delete(@Nullable Long requestId, Long userId);

    void getCurrent(@Nullable Long requestId);

    void updateCurrent(@Nullable Long requestId, UserUpdate user);

    void getNetwork(@Nullable Long requestId, Long userId, Long networkId);

    void assignNetwork(@Nullable Long requestId, Long userId, Long networkId);

    void unassignNetwork(@Nullable Long requestId, Long userId, Long networkId);

}
