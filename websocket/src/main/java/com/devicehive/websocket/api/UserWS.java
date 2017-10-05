package com.devicehive.websocket.api;

import com.devicehive.websocket.listener.UserListener;
import com.devicehive.websocket.model.RoleEnum;
import com.devicehive.websocket.model.SortOrder;
import com.devicehive.websocket.model.StatusEnum;
import com.devicehive.websocket.model.repsonse.*;
import com.devicehive.websocket.model.request.*;
import com.devicehive.websocket.model.request.data.User;
import com.devicehive.websocket.model.request.data.UserUpdate;
import okhttp3.WebSocket;

import javax.annotation.Nullable;

import static com.devicehive.websocket.model.ActionConstant.*;

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
    public void list( Long requestId, String login, String loginPattern, StatusEnum status, RoleEnum role, String sortField, SortOrder sortOrder, Integer take, Integer skip) {
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
    public void get( Long requestId, Long userId) {
        UserGetAction action = new UserGetAction();
        action.setRequestId(requestId);
        action.setUserId(userId);
        send(action);
    }

    @Override
    public void insert( Long requestId, User user) {
        UserInsertAction action = new UserInsertAction();
        action.setRequestId(requestId);
        action.setUser(user);
        send(action);
    }

    @Override
    public void update( Long requestId, Long userId, UserUpdate user) {
        UserUpdateAction action = new UserUpdateAction();
        action.setRequestId(requestId);
        action.setUserId(userId);
        action.setUser(user);
        send(action);
    }

    @Override
    public void delete( Long requestId, Long userId) {
        UserDeleteAction action = new UserDeleteAction();
        action.setRequestId(requestId);
        action.setUserId(userId);
        send(action);
    }

    @Override
    public void getCurrent( Long requestId) {
        UserGetCurrentAction action = new UserGetCurrentAction();
        action.setRequestId(requestId);
        send(action);
    }

    @Override
    public void updateCurrent( Long requestId, UserUpdate user) {
        UserUpdateCurrentAction action = new UserUpdateCurrentAction();
        action.setRequestId(requestId);
        action.setUser(user);
        send(action);
    }

    @Override
    public void getNetwork( Long requestId, Long userId, Long networkId) {
        UserGetNetworkAction action = new UserGetNetworkAction();
        action.setRequestId(requestId);
        action.setUserId(userId);
        action.setNetworkId(networkId);
        send(action);
    }

    @Override
    public void assignNetwork( Long requestId, Long userId, Long networkId) {
        UserAssignNetworkAction action = new UserAssignNetworkAction();
        action.setRequestId(requestId);
        action.setUserId(userId);
        action.setNetworkId(networkId);
        send(action);
    }

    @Override
    public void unassignNetwork( Long requestId, Long userId, Long networkId) {
        UserUnassignNetworkAction action = new UserUnassignNetworkAction();
        action.setRequestId(requestId);
        action.setUserId(userId);
        action.setNetworkId(networkId);
        send(action);
    }
}
