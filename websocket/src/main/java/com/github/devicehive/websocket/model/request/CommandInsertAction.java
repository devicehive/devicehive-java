/*
 *
 *
 *   CommandInsertAction.java
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

import com.github.devicehive.websocket.model.ActionConstant;
import com.github.devicehive.rest.model.DeviceCommandWrapper;
import com.google.gson.annotations.SerializedName;

public class CommandInsertAction extends RequestAction {

    @SerializedName("deviceId")
    String deviceId;
    @SerializedName("command")
    DeviceCommandWrapper command;

    public CommandInsertAction() {
        super(ActionConstant.COMMAND_INSERT);
    }


    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public DeviceCommandWrapper getCommand() {
        return command;
    }

    public void setCommand(DeviceCommandWrapper command) {
        this.command = command;
    }

    @Override
    public String toString() {
        return "{\n\"CommandInsertAction\":{\n"
                + "\"deviceId\":\"" + deviceId + "\""
                + ",\n \"command\":" + command
                + ",\n \"requestId\":\"" + requestId + "\""
                + "}\n}";
    }
}
