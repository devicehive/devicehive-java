package com.devicehive.client.service;

import com.devicehive.client.model.DHResponse;
import com.devicehive.client.model.DeviceCommand;
import com.devicehive.client.model.DeviceCommandCallback;
import com.devicehive.client.model.Parameter;
import com.devicehive.rest.api.DeviceCommandApi;
import com.devicehive.rest.model.DeviceCommandWrapper;
import org.joda.time.DateTime;
import org.joda.time.Period;

import java.io.IOException;
import java.util.List;

public class DeviceCommandService extends BaseService {

    private DeviceCommandApi deviceCommandApi;

    public DHResponse<List<DeviceCommand>> getDeviceCommands(String deviceId, DateTime startTimestamp,
                                                             DateTime endTimestamp, int maxNumber) throws IOException {
        deviceCommandApi = createService(DeviceCommandApi.class);

        Period period = new Period(startTimestamp, endTimestamp);

        DHResponse<List<DeviceCommand>> response;

        DHResponse<List<com.devicehive.rest.model.DeviceCommand>> result =
                execute(deviceCommandApi.poll(deviceId, null, startTimestamp.toString(),
                        (long) period.toStandardSeconds().getSeconds(), maxNumber));

        response = new DHResponse<List<DeviceCommand>>(
                DeviceCommand.createList(result.getData()),
                result.getFailureData());
        if (response.isSuccessful()) {
            return response;
        } else if (response.getFailureData().getCode() == 401) {
            authorize();
            deviceCommandApi = createService(DeviceCommandApi.class);
            result = execute(deviceCommandApi.poll(deviceId, null, startTimestamp.toString(),
                    30L, maxNumber));
            return new DHResponse<List<DeviceCommand>>(DeviceCommand.createList(result.getData()),
                    result.getFailureData());
        } else {
            return response;
        }
    }

    public DHResponse<DeviceCommand> sendCommand(String deviceId, String command, List<Parameter> parameters, DeviceCommandCallback resultCallback) throws IOException {
        deviceCommandApi = createService(DeviceCommandApi.class);
        DHResponse<DeviceCommand> response;
        DeviceCommandWrapper wrapper = createDeviceCommandWrapper(command, parameters);

        DHResponse<com.devicehive.rest.model.DeviceCommand> result = execute(deviceCommandApi.insert(deviceId, wrapper));
        response = new DHResponse<DeviceCommand>(DeviceCommand.create(result.getData()), result.getFailureData());
        if (response.isSuccessful()) {
            return response;
        } else if (response.getFailureData().getCode() == 401) {
            authorize();
            result = execute(deviceCommandApi.insert(deviceId, wrapper));
            return new DHResponse<DeviceCommand>(DeviceCommand.create(result.getData()),
                    result.getFailureData());
        } else {
            return response;
        }
    }


    private DeviceCommandWrapper createDeviceCommandWrapper(String command, List<Parameter> parameters) {
        DeviceCommandWrapper commandWrapper = new DeviceCommandWrapper();
        commandWrapper.setCommand(command);
        commandWrapper.setTimestamp(DateTime.now());
        commandWrapper.setParameters(wrapParameters(parameters));
        return commandWrapper;

    }

}
