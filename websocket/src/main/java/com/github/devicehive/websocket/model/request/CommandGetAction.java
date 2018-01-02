/*
 *
 *
 *   CommandGetAction.java
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

package com.github.devicehive.websocket.model.request;

import com.google.gson.annotations.SerializedName;

import static com.github.devicehive.websocket.model.ActionConstant.COMMAND_GET;

public class CommandGetAction extends RequestAction {

    @SerializedName("deviceId")
    String deviceId;
    @SerializedName("commandId")
    Long commandId;

    public CommandGetAction() {
        super(COMMAND_GET);
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public Long getCommandId() {
        return commandId;
    }

    public void setCommandId(Long commandId) {
        this.commandId = commandId;
    }

    @Override
    public String toString() {
        return "{\n\"CommandGetAction\":{\n"
                + "\"deviceId\":\"" + deviceId + "\""
                + ",\n \"commandId\":\"" + commandId + "\""
                + ",\n \"requestId\":\"" + requestId + "\""
                + "}\n}";
    }
}
