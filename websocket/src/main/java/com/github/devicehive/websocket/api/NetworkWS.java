/*
 *
 *
 *   NetworkWS.java
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

import com.github.devicehive.websocket.listener.NetworkListener;
import com.github.devicehive.websocket.model.ActionConstant;
import com.github.devicehive.websocket.model.repsonse.NetworkInsertResponse;
import com.github.devicehive.websocket.model.repsonse.NetworkListResponse;
import com.github.devicehive.websocket.model.request.NetworkDeleteAction;

public class NetworkWS extends BaseWebSocketApi implements NetworkApi {

    static final String TAG = "network";
    private final NetworkListener listener;


    NetworkWS(WebSocketClient client, NetworkListener listener) {
        super(client, listener);
        this.listener = listener;
    }

    @Override
    public String getKey() {
        return TAG;
    }

    @Override
    public void onSuccess(String message) {
        com.github.devicehive.websocket.model.repsonse.ResponseAction action = getResponseAction(message);
        String actionName = action.getAction();

        if (actionName.equalsIgnoreCase(ActionConstant.NETWORK_LIST)) {
            NetworkListResponse response = gson.fromJson(message, NetworkListResponse.class);
            listener.onList(response);
        } else if (actionName.equalsIgnoreCase(ActionConstant.NETWORK_GET)) {
            com.github.devicehive.websocket.model.repsonse.NetworkGetResponse response = gson.fromJson(message, com.github.devicehive.websocket.model.repsonse.NetworkGetResponse.class);
            listener.onGet(response);
        } else if (actionName.equalsIgnoreCase(ActionConstant.NETWORK_INSERT)) {
            NetworkInsertResponse response = gson.fromJson(message, NetworkInsertResponse.class);
            listener.onInsert(response);
        } else if (actionName.equalsIgnoreCase(ActionConstant.NETWORK_UPDATE)) {
            listener.onUpdate(action);
        } else if (actionName.equalsIgnoreCase(ActionConstant.NETWORK_DELETE)) {
            listener.onDelete(action);
        }

    }

    @Override
    public void list( Long requestId, String name, String namePattern, String sortField, Boolean sortOrderAsc, Integer take, Integer skip) {
        com.github.devicehive.websocket.model.request.NetworkListAction action = new com.github.devicehive.websocket.model.request.NetworkListAction();
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
        com.github.devicehive.websocket.model.request.NetworkGetAction action = new com.github.devicehive.websocket.model.request.NetworkGetAction();
        action.setId(id);
        action.setRequestId(requestId);
        send(action);
    }

    @Override
    public void insert( Long requestId, com.github.devicehive.websocket.model.request.data.NetworkUpdate networkUpdate) {
        com.github.devicehive.websocket.model.request.NetworkInsertAction action = new com.github.devicehive.websocket.model.request.NetworkInsertAction();
        action.setNetwork(networkUpdate);
        action.setRequestId(requestId);
        send(action);
    }

    @Override
    public void update( Long requestId, com.github.devicehive.websocket.model.request.data.NetworkUpdate networkUpdate) {
        com.github.devicehive.websocket.model.request.NetworkUpdateAction action = new com.github.devicehive.websocket.model.request.NetworkUpdateAction();
        action.setNetwork(networkUpdate);
        action.setRequestId(requestId);
        send(action);
    }

    @Override
    public void delete( Long requestId, Long id) {
        com.github.devicehive.websocket.model.request.NetworkDeleteAction action = new NetworkDeleteAction();
        action.setId(id);
        action.setRequestId(requestId);
        send(action);
    }
}
