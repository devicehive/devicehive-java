/*
 *
 *
 *   DeviceTypeWS.java
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

import com.github.devicehive.rest.model.DeviceTypeCount;
import com.github.devicehive.rest.model.DeviceTypeUpdate;
import com.github.devicehive.rest.model.SortOrder;
import com.github.devicehive.websocket.listener.DeviceTypeListener;
import com.github.devicehive.websocket.model.repsonse.DeviceTypeCountResponse;
import com.github.devicehive.websocket.model.repsonse.DeviceTypeGetResponse;
import com.github.devicehive.websocket.model.repsonse.DeviceTypeInsertResponse;
import com.github.devicehive.websocket.model.repsonse.DeviceTypeListResponse;
import com.github.devicehive.websocket.model.repsonse.ResponseAction;
import com.github.devicehive.websocket.model.request.DeviceTypeCountAction;
import com.github.devicehive.websocket.model.request.DeviceTypeDeleteAction;
import com.github.devicehive.websocket.model.request.DeviceTypeGetAction;
import com.github.devicehive.websocket.model.request.DeviceTypeInsertAction;
import com.github.devicehive.websocket.model.request.DeviceTypeListAction;
import com.github.devicehive.websocket.model.request.DeviceTypeUpdateAction;

import static com.github.devicehive.websocket.model.ActionConstant.DEVICE_TYPE_COUNT;
import static com.github.devicehive.websocket.model.ActionConstant.DEVICE_TYPE_DELETE;
import static com.github.devicehive.websocket.model.ActionConstant.DEVICE_TYPE_GET;
import static com.github.devicehive.websocket.model.ActionConstant.DEVICE_TYPE_INSERT;
import static com.github.devicehive.websocket.model.ActionConstant.DEVICE_TYPE_LIST;
import static com.github.devicehive.websocket.model.ActionConstant.DEVICE_TYPE_UPDATE;

public class DeviceTypeWS extends BaseWebSocketApi implements DeviceTypeApi {

    static final String TAG = "devicetype";
    private DeviceTypeListener listener;

    DeviceTypeWS(WebSocketClient client) {
        super(client, null);
    }

    @Override
    public String getKey() {
        return TAG;
    }

    public void setListener(DeviceTypeListener listener) {
        super.setListener(listener);
        this.listener = listener;
    }


    @Override
    public void onSuccess(String message) {
        ResponseAction action = getResponseAction(message);
        String actionName = action.getAction();
        if (actionName.equalsIgnoreCase(DEVICE_TYPE_LIST)) {
            DeviceTypeListResponse response = gson.fromJson(message, DeviceTypeListResponse.class);
            listener.onList(response.getDeviceTypes());
        } else if (actionName.equalsIgnoreCase(DEVICE_TYPE_GET)) {
            DeviceTypeGetResponse response = gson.fromJson(message, DeviceTypeGetResponse.class);
            listener.onGet(response.getDeviceType());
        } else if (actionName.equalsIgnoreCase(DEVICE_TYPE_DELETE)) {
            listener.onDelete(action);
        } else if (actionName.equalsIgnoreCase(DEVICE_TYPE_INSERT)) {
            DeviceTypeInsertResponse response = gson.fromJson(message, DeviceTypeInsertResponse.class);
            listener.onInsert(response.getDeviceType());
        } else if (actionName.equalsIgnoreCase(DEVICE_TYPE_COUNT)) {
            DeviceTypeCountResponse response = gson.fromJson(message, DeviceTypeCountResponse.class);
            DeviceTypeCount count = new DeviceTypeCount();
            count.setCount(response.getCount());
            listener.onCount(count);
        } else if (actionName.equalsIgnoreCase(DEVICE_TYPE_UPDATE)) {
            listener.onUpdate(action);
        }
    }


    @Override
    public void get(Long requestId, long deviceTypeId) {
        DeviceTypeGetAction action = new DeviceTypeGetAction();
        action.setDeviceTypeId(deviceTypeId);
        action.setRequestId(requestId);
        send(action);
    }

    @Override
    public void list(Long requestId, String name, String namePattern, String sortField, SortOrder sortOrder, int take, int skip) {
        DeviceTypeListAction action = new DeviceTypeListAction();
        action.setRequestId(requestId);
        action.setName(name);
        action.setNamePattern(namePattern);
        action.setSortField(sortField);
        action.setSortOrder(sortOrder);
        action.setTake(take);
        action.setSkip(skip);
        send(action);
    }

    @Override
    public void insert(Long requestId, DeviceTypeUpdate deviceTypeUpdate) {
        DeviceTypeInsertAction action = new DeviceTypeInsertAction();
        action.setRequestId(requestId);
        action.setDeviceType(deviceTypeUpdate);
        send(action);
    }

    @Override
    public void count(Long requestId, String name, String namePattern) {
        DeviceTypeCountAction action = new DeviceTypeCountAction();
        action.setRequestId(requestId);
        action.setName(name);
        action.setNamePattern(namePattern);
        send(action);
    }

    @Override
    public void update(Long requestId, long deviceTypeId, DeviceTypeUpdate deviceTypeUpdate) {
        DeviceTypeUpdateAction action = new DeviceTypeUpdateAction();
        action.setRequestId(requestId);
        action.setDeviceTypeId(deviceTypeId);
        action.setDeviceType(deviceTypeUpdate);
        send(action);
    }

    @Override
    public void delete(Long requestId, long deviceTypeId) {
        DeviceTypeDeleteAction action = new DeviceTypeDeleteAction();
        action.setRequestId(requestId);
        action.setDeviceTypeId(deviceTypeId);
        send(action);
    }
}
