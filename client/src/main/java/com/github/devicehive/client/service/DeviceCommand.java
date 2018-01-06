/*
 *
 *
 *   DeviceCommand.java
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

package com.github.devicehive.client.service;

import com.github.devicehive.client.model.DHResponse;
import com.github.devicehive.rest.model.DeviceCommandWrapper;
import com.google.gson.JsonObject;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class DeviceCommand {
    private Long id = null;
    private String commandName = null;
    private String deviceId = null;
    private Long networkId = null;
    private JsonObject parameters = new JsonObject();
    private String status;
    private DateTime timestamp;
    private int lifetime;
    private JsonObject result=new JsonObject();

    private DeviceCommand() {

    }

    public Long getId() {
        return id;
    }

    public String getCommandName() {
        return commandName;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public Long getNetworkId() {
        return networkId;
    }

    public JsonObject getParameters() {
        return parameters;
    }

    public DateTime getTimestamp() {
        return timestamp;
    }

    public int getLifetime() {
        return lifetime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public JsonObject getResult() {
        return result;
    }

    public void setResult(JsonObject result) {
        this.result = result;
    }

    static DeviceCommand create(com.github.devicehive.rest.model.CommandInsert command, String commandName,
                                String deviceId, long networkId, JsonObject parameters) {
        if (command == null) {
            return null;
        }
        DeviceCommand deviceCommand = new DeviceCommand();
        deviceCommand.commandName = commandName;
        deviceCommand.id = command.getCommandId();
        deviceCommand.deviceId = deviceId;
        deviceCommand.networkId = networkId;
        deviceCommand.parameters = parameters == null ? new JsonObject() : parameters;
        deviceCommand.timestamp = command.getTimestamp();
        return deviceCommand;
    }

    static DeviceCommand create(DeviceCommand command) {
        if (command == null) {
            return null;
        }
        DeviceCommand deviceCommand = new DeviceCommand();
        deviceCommand.commandName = command.getCommandName();
        deviceCommand.id = command.getId();
        deviceCommand.deviceId = command.getDeviceId();
        deviceCommand.parameters = command.getParameters() == null ? new JsonObject() : command.getParameters();
        deviceCommand.networkId = command.getNetworkId();
        deviceCommand.timestamp = command.getTimestamp();
        return deviceCommand;
    }

    static DeviceCommand create(com.github.devicehive.rest.model.DeviceCommand command) {
        if (command == null) {
            return null;
        }
        DeviceCommand deviceCommand = new DeviceCommand();
        deviceCommand.commandName = command.getCommand();
        deviceCommand.id = command.getId();
        deviceCommand.deviceId = command.getDeviceId();
        deviceCommand.networkId = command.getNetworkId();
        deviceCommand.parameters = command.getParameters() == null ? new JsonObject() : command.getParameters();
        deviceCommand.timestamp = command.getTimestamp();
        return deviceCommand;
    }

    static List<DeviceCommand> createList(List<DeviceCommand> commands) {
        List<DeviceCommand> list = new ArrayList<DeviceCommand>();
        if (commands == null) {
            return Collections.emptyList();
        }
        for (DeviceCommand deviceCommand :
                commands) {
            list.add(DeviceCommand.create(deviceCommand));

        }
        return list;

    }

    static List<DeviceCommand> createListFromRest(List<com.github.devicehive.rest.model.DeviceCommand> commands) {
        List<DeviceCommand> list = new ArrayList<DeviceCommand>();
        if (commands == null) {
            return Collections.emptyList();
        }
        for (com.github.devicehive.rest.model.DeviceCommand deviceCommand :
                commands) {
            list.add(DeviceCommand.create(deviceCommand));

        }
        return list;

    }


    public boolean updateCommand() {
        DeviceCommandWrapper wrapper = new DeviceCommandWrapper();
        wrapper.setCommand(commandName);
        wrapper.setParameters(parameters);
        wrapper.setResult(result);
        wrapper.setStatus(status);
        wrapper.setTimestamp(timestamp);
        wrapper.setLifetime(lifetime);
        return DeviceHive.getInstance().getCommandService().updateCommand(deviceId, id, wrapper).isSuccessful();
    }

    public DHResponse<String> fetchCommandStatus() {
        DHResponse<com.github.devicehive.rest.model.DeviceCommand> deviceCommand =
                DeviceHive.getInstance().getCommandService().getCommand(deviceId, id);
        status = deviceCommand.isSuccessful() ? deviceCommand.getData().getStatus() : null;
        return new DHResponse<>(status, deviceCommand.getFailureData());
    }

    public DHResponse<JsonObject> fetchCommandResult() {
        DHResponse<com.github.devicehive.rest.model.DeviceCommand> deviceCommand =
                DeviceHive.getInstance().getCommandService().getCommand(deviceId, id);
        if (deviceCommand.isSuccessful()) {
            if (deviceCommand.getData().getResult() != null) {
                result = deviceCommand.getData().getResult();
            }
        }
        return new DHResponse<>(result, deviceCommand.getFailureData());
    }

    @Override
    public String toString() {
        return "{\n\"DeviceCommand\":{\n"
                + "\"id\":\"" + id + "\""
                + ",\n \"commandName\":\"" + commandName + "\""
                + ",\n \"deviceId\":\"" + deviceId + "\""
                + ",\n \"networkId\":\"" + networkId + "\""
                + ",\n \"parameters\":" + parameters
                + ",\n \"status\":\"" + status + "\""
                + ",\n \"result\":" + result
                + "}\n}";
    }
}
