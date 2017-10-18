/*
 *
 *
 *   UserWS.java
 *
 *   Copyright (C) 2017 DataArt
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
 */

package com.github.devicehive.websocket.api;

import com.github.devicehive.websocket.listener.UserListener;
import com.github.devicehive.websocket.model.RoleEnum;
import com.github.devicehive.websocket.model.SortOrder;
import com.github.devicehive.websocket.model.repsonse.UserGetCurrentResponse;
import com.github.devicehive.websocket.model.request.UserDeleteAction;
import com.github.devicehive.websocket.model.request.data.User;
import com.github.devicehive.websocket.model.request.data.UserUpdate;
import okhttp3.WebSocket;

import static com.github.devicehive.websocket.model.ActionConstant.*;

public class UserWS extends BaseWebSocketApi implements UserApi {

    static final String TAG = "user";
    private final UserListener listener;


    UserWS(WebSocket ws, UserListener listener) {
        super(ws, listener);
        this.listener = listener;
    }

    @Override
    public String getKey() {
        return TAG;
    }

    @Override
    public void onSuccess(String message) {
        com.github.devicehive.websocket.model.repsonse.ResponseAction action = getResponseAction(message);
        if (action.compareAction(USER_LIST)) {
            com.github.devicehive.websocket.model.repsonse.UserListResponse response = gson.fromJson(message, com.github.devicehive.websocket.model.repsonse.UserListResponse.class);
            listener.onList(response);
        } else if (action.compareAction(USER_GET)) {
            com.github.devicehive.websocket.model.repsonse.UserGetResponse response = gson.fromJson(message, com.github.devicehive.websocket.model.repsonse.UserGetResponse.class);
            listener.onGet(response);
        } else if (action.compareAction(USER_INSERT)) {
            com.github.devicehive.websocket.model.repsonse.UserInsertResponse response = gson.fromJson(message, com.github.devicehive.websocket.model.repsonse.UserInsertResponse.class);
            listener.onInsert(response);
        } else if (action.compareAction(USER_UPDATE)) {
            listener.onUpdate(action);
        } else if (action.compareAction(USER_DELETE)) {
            listener.onDelete(action);
        } else if (action.compareAction(USER_GET_CURRENT)) {
            com.github.devicehive.websocket.model.repsonse.UserGetCurrentResponse response = gson.fromJson(message, UserGetCurrentResponse.class);
            listener.onGetCurrent(response);
        } else if (action.compareAction(USER_UPDATE_CURRENT)) {
            listener.onUpdateCurrent(action);
        } else if (action.compareAction(USER_GET_NETWORK)) {
            com.github.devicehive.websocket.model.repsonse.UserGetNetworkResponse response = gson.fromJson(message, com.github.devicehive.websocket.model.repsonse.UserGetNetworkResponse.class);
            listener.onGetNetwork(response);
        } else if (action.compareAction(USER_ASSIGN_NETWORK)) {
            listener.onAssignNetwork(action);
        } else if (action.compareAction(USER_UNASSIGN_NETWORK)) {
            listener.onUnassignNetwork(action);
        }
    }

    @Override
    public void list(Long requestId, String login, String loginPattern, com.github.devicehive.websocket.model.StatusEnum status, RoleEnum role, String sortField, SortOrder sortOrder, Integer take, Integer skip) {
        com.github.devicehive.websocket.model.request.UserListAction action = new com.github.devicehive.websocket.model.request.UserListAction();
        action.setRequestId(requestId);
        action.setLogin(login);
        action.setLoginPattern(loginPattern);
        action.setStatus(status);
        action.setRole(role);
        action.setSortField(sortField);
        action.setSortOrder(sortOrder);
        action.setTake(take);
        action.setSkip(skip);
        send(action);
    }

    @Override
    public void get( Long requestId, Long userId) {
        com.github.devicehive.websocket.model.request.UserGetAction action = new com.github.devicehive.websocket.model.request.UserGetAction();
        action.setRequestId(requestId);
        action.setUserId(userId);
        send(action);
    }

    @Override
    public void insert( Long requestId, User user) {
        com.github.devicehive.websocket.model.request.UserInsertAction action = new com.github.devicehive.websocket.model.request.UserInsertAction();
        action.setRequestId(requestId);
        action.setUser(user);
        send(action);
    }

    @Override
    public void update( Long requestId, Long userId, UserUpdate user) {
        com.github.devicehive.websocket.model.request.UserUpdateAction action = new com.github.devicehive.websocket.model.request.UserUpdateAction();
        action.setRequestId(requestId);
        action.setUserId(userId);
        action.setUser(user);
        send(action);
    }

    @Override
    public void delete( Long requestId, Long userId) {
        com.github.devicehive.websocket.model.request.UserDeleteAction action = new UserDeleteAction();
        action.setRequestId(requestId);
        action.setUserId(userId);
        send(action);
    }

    @Override
    public void getCurrent( Long requestId) {
        com.github.devicehive.websocket.model.request.UserGetCurrentAction action = new com.github.devicehive.websocket.model.request.UserGetCurrentAction();
        action.setRequestId(requestId);
        send(action);
    }

    @Override
    public void updateCurrent( Long requestId, UserUpdate user) {
        com.github.devicehive.websocket.model.request.UserUpdateCurrentAction action = new com.github.devicehive.websocket.model.request.UserUpdateCurrentAction();
        action.setRequestId(requestId);
        action.setUser(user);
        send(action);
    }

    @Override
    public void getNetwork( Long requestId, Long userId, Long networkId) {
        com.github.devicehive.websocket.model.request.UserGetNetworkAction action = new com.github.devicehive.websocket.model.request.UserGetNetworkAction();
        action.setRequestId(requestId);
        action.setUserId(userId);
        action.setNetworkId(networkId);
        send(action);
    }

    @Override
    public void assignNetwork( Long requestId, Long userId, Long networkId) {
        com.github.devicehive.websocket.model.request.UserAssignNetworkAction action = new com.github.devicehive.websocket.model.request.UserAssignNetworkAction();
        action.setRequestId(requestId);
        action.setUserId(userId);
        action.setNetworkId(networkId);
        send(action);
    }

    @Override
    public void unassignNetwork( Long requestId, Long userId, Long networkId) {
        com.github.devicehive.websocket.model.request.UserUnassignNetworkAction action = new com.github.devicehive.websocket.model.request.UserUnassignNetworkAction();
        action.setRequestId(requestId);
        action.setUserId(userId);
        action.setNetworkId(networkId);
        send(action);
    }
}
