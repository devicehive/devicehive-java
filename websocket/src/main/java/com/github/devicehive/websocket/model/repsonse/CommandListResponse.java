/*
 *
 *
 *   CommandListResponse.java
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

package com.github.devicehive.websocket.model.repsonse;

import com.github.devicehive.rest.model.DeviceCommand;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CommandListResponse extends ResponseAction {

    @SerializedName("commands")
    List<DeviceCommand> commands;

    public List<DeviceCommand> getCommands() {
        return commands;
    }

    public void setCommands(List<DeviceCommand> commands) {
        this.commands = commands;
    }

    @Override
    public String toString() {
        return "{\n\"CommandListResponse\":{\n"
                + "\"commands\":" + commands
                + ",\n \"action\":\"" + action + "\""
                + ",\n \"requestId\":\"" + requestId + "\""
                + ",\n \"status\":\"" + status + "\""
                + "}\n}";
    }
}
