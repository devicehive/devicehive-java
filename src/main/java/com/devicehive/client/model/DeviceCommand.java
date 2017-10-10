package com.devicehive.client.model;

import com.devicehive.client.DeviceHive;
import com.devicehive.rest.model.DeviceCommandWrapper;
import com.devicehive.websocket.model.repsonse.data.JsonStringWrapper;
import lombok.Data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Data
public class DeviceCommand {
    private Long id = null;

    private String commandName = null;

    private String deviceId = null;

    private JsonStringWrapper parameters = null;

    private String status;
    private JsonStringWrapper result;

    private DeviceCommand() {

    }

    public static DeviceCommand create(com.devicehive.rest.model.CommandInsert command, String commandName,
                                       String deviceId, com.devicehive.rest.model.JsonStringWrapper parameters) {
        if (command == null) {
            return null;
        }
        DeviceCommand deviceCommand = new DeviceCommand();
        deviceCommand.commandName = commandName;
        deviceCommand.id = command.getCommandId();
        deviceCommand.deviceId = deviceId;
        deviceCommand.parameters = new JsonStringWrapper(parameters.getJsonString());
        ;
        return deviceCommand;
    }

    public static DeviceCommand create(com.devicehive.websocket.model.repsonse.data.DeviceCommand command) {
        if (command == null) {
            return null;
        }
        DeviceCommand deviceCommand = new DeviceCommand();
        deviceCommand.commandName = command.getCommandName();
        deviceCommand.id = command.getId();
        deviceCommand.deviceId = command.getDeviceId();
        deviceCommand.parameters = command.getParameters();
        return deviceCommand;
    }

    public static DeviceCommand create(com.devicehive.rest.model.DeviceCommand command) {
        if (command == null) {
            return null;
        }
        DeviceCommand deviceCommand = new DeviceCommand();
        deviceCommand.commandName = command.getCommand();
        deviceCommand.id = command.getId();
        deviceCommand.deviceId = command.getDeviceId();
        System.out.println(command.getParameters());
        deviceCommand.parameters = new JsonStringWrapper(command.getParameters().getJsonString());
        return deviceCommand;
    }

    public static List<DeviceCommand> createList(List<com.devicehive.websocket.model.repsonse.data.DeviceCommand> commands) {
        List<DeviceCommand> list = new ArrayList<DeviceCommand>();
        if (commands == null) {
            return Collections.emptyList();
        }
        for (com.devicehive.websocket.model.repsonse.data.DeviceCommand deviceCommand :
                commands) {
            list.add(DeviceCommand.create(deviceCommand));

        }
        return list;

    }

    public static List<DeviceCommand> createListFromRest(List<com.devicehive.rest.model.DeviceCommand> commands) {
        List<DeviceCommand> list = new ArrayList<DeviceCommand>();
        if (commands == null) {
            return Collections.emptyList();
        }
        for (com.devicehive.rest.model.DeviceCommand deviceCommand :
                commands) {
            list.add(DeviceCommand.create(deviceCommand));

        }
        return list;

    }


    public boolean updateCommand() {
        DeviceCommandWrapper wrapper = new DeviceCommandWrapper();
        wrapper.setCommand(commandName);
        if (parameters != null) {
            wrapper.setParameters(new com.devicehive.rest.model.JsonStringWrapper(parameters.getJsonString()));
        }
        return DeviceHive.getInstance().getCommandService().updateCommand(deviceId, id, wrapper).isSuccessful();
    }

    public DHResponse<String> fetchCommandStatus() {
        DHResponse<com.devicehive.rest.model.DeviceCommand> deviceCommand =
                DeviceHive.getInstance().getCommandService().getCommand(deviceId, id);
        status = deviceCommand.isSuccessful() ? deviceCommand.getData().getStatus() : null;
        return new DHResponse<>(status, deviceCommand.getFailureData());
    }

    public DHResponse<JsonStringWrapper> fetchCommandResult() {
        DHResponse<com.devicehive.rest.model.DeviceCommand> deviceCommand =
                DeviceHive.getInstance().getCommandService().getCommand(deviceId, id);
        if (deviceCommand.isSuccessful()) {
            if (deviceCommand.getData().getResult() != null) {
                result = new JsonStringWrapper(deviceCommand.getData().getResult().getJsonString());
            }
        }
        return new DHResponse<>(result, deviceCommand.getFailureData());
    }
}
