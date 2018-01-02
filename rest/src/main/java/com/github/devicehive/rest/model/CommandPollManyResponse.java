/*
 *
 *
 *   CommandPollManyResponse.java
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

package com.github.devicehive.rest.model;


import com.google.gson.annotations.SerializedName;

public class CommandPollManyResponse  {

    private static final long serialVersionUID = -4390548037685312874L;
    @SerializedName("notification")
    private DeviceCommand command;

    @SerializedName("deviceGuid")
    private String guid;

    public CommandPollManyResponse(DeviceCommand command, String guid) {
        this.command = command;
        this.guid = guid;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public DeviceCommand getCommand() {
        return command;
    }

    public void setCommand(DeviceCommand command) {
        this.command = command;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }
}
