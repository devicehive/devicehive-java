package com.devicehive.websocket.api;

import com.devicehive.websocket.model.RoleEnum;
import com.devicehive.websocket.model.SortOrder;
import com.devicehive.websocket.model.StatusEnum;
import com.devicehive.websocket.model.request.data.User;
import com.devicehive.websocket.model.request.data.UserUpdate;

import javax.annotation.Nullable;

interface UserApi {

    void list( Long requestId, String login, String loginPattern, StatusEnum status, RoleEnum role,
              String sortField, SortOrder sortOrder, Integer take, Integer skip);

    void get( Long requestId, Long userId);

    void insert( Long requestId, User user);

    void update( Long requestId, Long userId, UserUpdate user);

    void delete( Long requestId, Long userId);

    void getCurrent( Long requestId);

    void updateCurrent( Long requestId, UserUpdate user);

    void getNetwork( Long requestId, Long userId, Long networkId);

    void assignNetwork( Long requestId, Long userId, Long networkId);

    void unassignNetwork( Long requestId, Long userId, Long networkId);

}
