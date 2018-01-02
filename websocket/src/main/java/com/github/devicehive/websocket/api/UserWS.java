/*
 *
 *
 *   UserWS.java
 *
 *   Copyright (C) 2018 DataArt
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

import com.github.devicehive.rest.model.*;
import com.github.devicehive.websocket.listener.UserListener;
import com.github.devicehive.websocket.model.repsonse.*;
import com.github.devicehive.websocket.model.request.*;

import static com.github.devicehive.websocket.model.ActionConstant.*;

public class UserWS extends BaseWebSocketApi implements UserApi {

    static final String TAG = "user";
    private UserListener listener;


    UserWS(WebSocketClient client) {
        super(client, null);
    }

    public void setListener(UserListener listener) {
        super.setListener(listener);
        this.listener = listener;
    }

    @Override
    public String getKey() {
        return TAG;
    }

    @Override
    public void onSuccess(String message) {
        ResponseAction action = getResponseAction(message);
        if (action.compareAction(USER_LIST)) {
            UserListResponse response = gson.fromJson(message, UserListResponse.class);
            listener.onList(response);
        } else if (action.compareAction(USER_GET)) {
            UserGetResponse response = gson.fromJson(message, UserGetResponse.class);
            listener.onGet(response);
        } else if (action.compareAction(USER_INSERT)) {
            UserInsertResponse response = gson.fromJson(message, UserInsertResponse.class);
            listener.onInsert(response);
        } else if (action.compareAction(USER_UPDATE)) {
            listener.onUpdate(action);
        } else if (action.compareAction(USER_DELETE)) {
            listener.onDelete(action);
        } else if (action.compareAction(USER_GET_CURRENT)) {
            UserGetCurrentResponse response = gson.fromJson(message, UserGetCurrentResponse.class);
            listener.onGetCurrent(response);
        } else if (action.compareAction(USER_UPDATE_CURRENT)) {
            listener.onUpdateCurrent(action);
        } else if (action.compareAction(USER_GET_NETWORK)) {
            UserGetNetworkResponse response = gson.fromJson(message, UserGetNetworkResponse.class);
            listener.onGetNetwork(response);
        } else if (action.compareAction(USER_ASSIGN_NETWORK)) {
            listener.onAssignNetwork(action);
        } else if (action.compareAction(USER_UNASSIGN_NETWORK)) {
            listener.onUnassignNetwork(action);
        }
    }

    @Override
    public void list(Long requestId, String login, String loginPattern, StatusEnum status, RoleEnum role, String sortField, SortOrder sortOrder, Integer take, Integer skip) {
        UserListAction action = new UserListAction();
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
    public void get(Long requestId, Long userId) {
        UserGetAction action = new UserGetAction();
        action.setRequestId(requestId);
        action.setUserId(userId);
        send(action);
    }

    @Override
    public void insert(Long requestId, UserUpdate user) {
        UserInsertAction action = new UserInsertAction();
        action.setRequestId(requestId);
        action.setUser(user);
        send(action);
    }

    @Override
    public void update(Long requestId, Long userId, UserUpdate user) {
        UserUpdateAction action = new UserUpdateAction();
        action.setRequestId(requestId);
        action.setUserId(userId);
        action.setUser(user);
        send(action);
    }

    @Override
    public void delete(Long requestId, Long userId) {
        UserDeleteAction action = new UserDeleteAction();
        action.setRequestId(requestId);
        action.setUserId(userId);
        send(action);
    }

    @Override
    public void getCurrent(Long requestId) {
        UserGetCurrentAction action = new UserGetCurrentAction();
        action.setRequestId(requestId);
        send(action);
    }

    @Override
    public void updateCurrent(Long requestId, UserUpdate user) {
        UserUpdateCurrentAction action = new UserUpdateCurrentAction();
        action.setRequestId(requestId);
        action.setUser(user);
        send(action);
    }

    @Override
    public void getNetwork(Long requestId, Long userId, Long networkId) {
        UserGetNetworkAction action = new UserGetNetworkAction();
        action.setRequestId(requestId);
        action.setUserId(userId);
        action.setNetworkId(networkId);
        send(action);
    }

    @Override
    public void assignNetwork(Long requestId, Long userId, Long networkId) {
        UserAssignNetworkAction action = new UserAssignNetworkAction();
        action.setRequestId(requestId);
        action.setUserId(userId);
        action.setNetworkId(networkId);
        send(action);
    }

    @Override
    public void unassignNetwork(Long requestId, Long userId, Long networkId) {
        UserUnassignNetworkAction action = new UserUnassignNetworkAction();
        action.setRequestId(requestId);
        action.setUserId(userId);
        action.setNetworkId(networkId);
        send(action);
    }
}
