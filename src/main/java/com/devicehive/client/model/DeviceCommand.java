package com.devicehive.client.model;

import com.devicehive.rest.model.JsonStringWrapper;
import lombok.Data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Class DeviceCommand, constructor is private
 * |fields:commandName,parameters,id,deviceid,name,parameters
 * |--getName(),getParameters(),getDeviceId(),getId()
 * |--subscribeUpdates(callback)->callback_proto(deviceCommand)
 * |--updateCommand()
 * |--save()
 * |--fetchCommandStatus()
 * \--fetchCommandResult()
 */
@Data
public class DeviceCommand {
    private Long id = null;

    private String commandName = null;

    private String deviceId = null;

    private JsonStringWrapper parameters = null;

    public static DeviceCommand create(com.devicehive.rest.model.DeviceCommand command) {
        if (command == null) {
            return null;
        }
        DeviceCommand deviceCommand = new DeviceCommand();
        deviceCommand.commandName = command.getCommand();
        deviceCommand.id = command.getId();
        deviceCommand.deviceId = command.getDeviceId();
        deviceCommand.parameters = command.getParameters();
        return deviceCommand;
    }

    public static List<DeviceCommand> createList(List<com.devicehive.rest.model.DeviceCommand> commands) {
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


}
