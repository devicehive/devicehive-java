package com.devicehive.websocket.api;

import com.devicehive.websocket.listener.NetworkListener;
import com.devicehive.websocket.model.repsonse.NetworkGetResponse;
import com.devicehive.websocket.model.repsonse.NetworkInsertResponse;
import com.devicehive.websocket.model.repsonse.NetworkListResponse;
import com.devicehive.websocket.model.repsonse.ResponseAction;
import com.devicehive.websocket.model.request.*;
import com.devicehive.websocket.model.request.data.NetworkUpdate;
import okhttp3.WebSocket;

import javax.annotation.Nullable;

import static com.devicehive.websocket.model.ActionConstant.*;

public class NetworkWS extends BaseWebSocketApi implements NetworkApi {

    static final String TAG = "network";
    private final NetworkListener listener;


    NetworkWS(WebSocket ws, NetworkListener listener) {
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
        String actionName = action.getAction();

        if (actionName.equalsIgnoreCase(NETWORK_LIST)) {
            NetworkListResponse response = gson.fromJson(message, NetworkListResponse.class);
            listener.onList(response);
        } else if (actionName.equalsIgnoreCase(NETWORK_GET)) {
            NetworkGetResponse response = gson.fromJson(message, NetworkGetResponse.class);
            listener.onGet(response);
        } else if (actionName.equalsIgnoreCase(NETWORK_INSERT)) {
            NetworkInsertResponse response = gson.fromJson(message, NetworkInsertResponse.class);
            listener.onInsert(response);
        } else if (actionName.equalsIgnoreCase(NETWORK_UPDATE)) {
            listener.onUpdate(action);
        } else if (actionName.equalsIgnoreCase(NETWORK_DELETE)) {
            listener.onDelete(action);
        }

    }

    @Override
    public void list( Long requestId, String name, String namePattern, String sortField, Boolean sortOrderAsc, Integer take, Integer skip) {
        NetworkListAction action = new NetworkListAction();
        action.setName(name);
        action.setNamePattern(namePattern);
        action.setSortField(sortField);
        action.setSkip(skip);
        action.setTake(take);
        action.setSortOrderAsc(sortOrderAsc);
        action.setRequestId(requestId);
        send(action);
    }

    @Override
    public void get( Long requestId, Long id) {
        NetworkGetAction action = new NetworkGetAction();
        action.setId(id);
        action.setRequestId(requestId);
        send(action);
    }

    @Override
    public void insert( Long requestId, NetworkUpdate networkUpdate) {
        NetworkInsertAction action = new NetworkInsertAction();
        action.setNetwork(networkUpdate);
        action.setRequestId(requestId);
        send(action);
    }

    @Override
    public void update( Long requestId, NetworkUpdate networkUpdate) {
        NetworkUpdateAction action = new NetworkUpdateAction();
        action.setNetwork(networkUpdate);
        action.setRequestId(requestId);
        send(action);
    }

    @Override
    public void delete( Long requestId, Long id) {
        NetworkDeleteAction action = new NetworkDeleteAction();
        action.setId(id);
        action.setRequestId(requestId);
        send(action);
    }
}
