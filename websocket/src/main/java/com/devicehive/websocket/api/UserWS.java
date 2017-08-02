package com.devicehive.websocket.api;

import com.devicehive.websocket.adapter.JsonStringWrapperAdapterFactory;
import com.devicehive.websocket.listener.UserListener;
import com.devicehive.websocket.model.RoleEnum;
import com.devicehive.websocket.model.SortOrder;
import com.devicehive.websocket.model.StatusEnum;
import com.devicehive.websocket.model.repsonse.*;
import com.devicehive.websocket.model.request.*;
import com.devicehive.websocket.model.request.data.User;
import com.devicehive.websocket.model.request.data.UserUpdate;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.OkHttpClient;
import okhttp3.Request;

import javax.annotation.Nullable;

import static com.devicehive.websocket.model.ActionConstant.*;

public class UserWS extends BaseWebSocketListener implements UserApi {

    public static final String TAG = "UserWS";
    private final UserListener listener;


    public UserWS(OkHttpClient client, Request request, UserListener listener) {
        super(client, request, listener);
        this.listener = listener;
    }

    @Override
    public String getKey() {
        return TAG;
    }

    @Override
    public void onSuccess(String message) {
        ResponseAction action = getResponseAction(message);
        Gson gson = new GsonBuilder()
                .registerTypeAdapterFactory(new JsonStringWrapperAdapterFactory())
                .create();
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
    public void list(@Nullable Long requestId, String login, String loginPattern, StatusEnum status, RoleEnum role, String sortField, SortOrder sortOrder, Integer take, Integer skip) {
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
    public void get(@Nullable Long requestId, Long userId) {
        UserGetAction action = new UserGetAction();
        action.setRequestId(requestId);
        action.setUserId(userId);
        send(action);
    }

    @Override
    public void insert(@Nullable Long requestId, User user) {
        UserInsertAction action = new UserInsertAction();
        action.setRequestId(requestId);
        action.setUser(user);
        send(action);
    }

    @Override
    public void update(@Nullable Long requestId, Long userId, UserUpdate user) {
        UserUpdateAction action = new UserUpdateAction();
        action.setRequestId(requestId);
        action.setUserId(userId);
        action.setUser(user);
        send(action);
    }

    @Override
    public void delete(@Nullable Long requestId, Long userId) {
        UserDeleteAction action = new UserDeleteAction();
        action.setRequestId(requestId);
        action.setUserId(userId);
        send(action);
    }

    @Override
    public void getCurrent(@Nullable Long requestId) {
        UserGetCurrentAction action = new UserGetCurrentAction();
        action.setRequestId(requestId);
        send(action);
    }

    @Override
    public void updateCurrent(@Nullable Long requestId, UserUpdate user) {
        UserUpdateCurrentAction action = new UserUpdateCurrentAction();
        action.setRequestId(requestId);
        action.setUser(user);
        send(action);
    }

    @Override
    public void getNetwork(@Nullable Long requestId, Long userId, Long networkId) {
        UserGetNetworkAction action = new UserGetNetworkAction();
        action.setRequestId(requestId);
        action.setUserId(userId);
        action.setNetworkId(networkId);
        send(action);
    }

    @Override
    public void assignNetwork(@Nullable Long requestId, Long userId, Long networkId) {
        UserAssignNetworkAction action = new UserAssignNetworkAction();
        action.setRequestId(requestId);
        action.setUserId(userId);
        action.setNetworkId(networkId);
        send(action);
    }

    @Override
    public void unassignNetwork(@Nullable Long requestId, Long userId, Long networkId) {
        UserUnassignNetworkAction action = new UserUnassignNetworkAction();
        action.setRequestId(requestId);
        action.setUserId(userId);
        action.setNetworkId(networkId);
        send(action);
    }
}
