/*
 *
 *
 *   ConfigurationWS.java
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

package com.devicehive.websocket.api;

import com.devicehive.websocket.listener.ConfigurationListener;
import com.devicehive.websocket.model.repsonse.ConfigurationGetResponse;
import com.devicehive.websocket.model.repsonse.ConfigurationInsertResponse;
import com.devicehive.websocket.model.repsonse.ResponseAction;
import com.devicehive.websocket.model.request.ConfigurationDeleteAction;
import com.devicehive.websocket.model.request.ConfigurationGetAction;
import com.devicehive.websocket.model.request.ConfigurationInsertAction;
import okhttp3.WebSocket;

import javax.annotation.Nullable;

import static com.devicehive.websocket.model.ActionConstant.*;

public class ConfigurationWS extends BaseWebSocketApi implements ConfigurationApi {

    static final String TAG = "configuration";
    private ConfigurationListener listener;

    ConfigurationWS(WebSocket ws, ConfigurationListener listener) {
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
        if (actionName.equalsIgnoreCase(CONFIGURATION_GET)) {
            ConfigurationGetResponse response = gson.fromJson(message, ConfigurationGetResponse.class);
            listener.onGet(response);
        } else if (actionName.equalsIgnoreCase(CONFIGURATION_INSERT)) {
            ConfigurationInsertResponse response = gson.fromJson(message, ConfigurationInsertResponse.class);
            listener.onInsert(response);
        } else if (actionName.equalsIgnoreCase(CONFIGURATION_DELETE)) {
            listener.onDelete(action);

        }
    }

    @Override
    public void get( Long requestId, String name) {
        ConfigurationGetAction action = new ConfigurationGetAction();
        action.setName(name);
        action.setRequestId(requestId);
        send(action);
    }

    @Override
    public void put( Long requestId, String name, String value) {
        ConfigurationInsertAction action = new ConfigurationInsertAction();
        action.setName(name);
        action.setValue(value);
        action.setRequestId(requestId);
        send(action);
    }

    @Override
    public void delete( Long requestId, String name) {
        ConfigurationDeleteAction action = new ConfigurationDeleteAction();
        action.setName(name);
        action.setRequestId(requestId);
        send(action);
    }
}
