package com.devicehive.client.service;

import com.devicehive.client.model.DHResponse;
import com.devicehive.client.model.DeviceCommand;
import com.devicehive.client.model.Parameter;
import com.devicehive.rest.api.DeviceCommandApi;
import com.devicehive.rest.model.DeviceCommandWrapper;
import org.joda.time.DateTime;
import org.joda.time.Period;
import retrofit2.Call;
import retrofit2.Callback;

import java.util.List;
import java.util.concurrent.CountDownLatch;

public class DeviceCommandService extends BaseService {

    public static final String CANCELED = "Canceled";
    private DeviceCommandApi deviceCommandApi;
    private Call<List<com.devicehive.rest.model.DeviceCommand>> pollCall;
    private boolean isSubscribed = false;
    private boolean isSubscribedMany = false;
    private Callback<List<com.devicehive.rest.model.DeviceCommand>> pollCommandsCallback;
    private Call<List<com.devicehive.rest.model.DeviceCommand>> pollManyCall;
    private CountDownLatch pollLatch = new CountDownLatch(1);


    public DHResponse<List<DeviceCommand>> getDeviceCommands(String deviceId, DateTime startTimestamp,
                                                             DateTime endTimestamp, int maxNumber) {
        return getDeviceCommands(deviceId, null, startTimestamp, endTimestamp, maxNumber);
    }

    private DHResponse<List<DeviceCommand>> getDeviceCommands(String deviceId, String deviceIds,
                                                              DateTime startTimestamp, DateTime endTimestamp,
                                                              int maxNumber) {

        deviceCommandApi = createService(DeviceCommandApi.class);

        Period period = new Period(startTimestamp, endTimestamp);

        DHResponse<List<DeviceCommand>> response;

        DHResponse<List<com.devicehive.rest.model.DeviceCommand>> result =
                execute(deviceCommandApi.poll(deviceId, deviceIds, startTimestamp.toString(),
                        (long) period.toStandardSeconds().getSeconds(), maxNumber));

        response = new DHResponse<List<DeviceCommand>>(
                DeviceCommand.createListFromRest(result.getData()),
                result.getFailureData());
        if (response.isSuccessful()) {
            return response;
        } else if (response.getFailureData().getCode() == 401) {
            authorize();
            deviceCommandApi = createService(DeviceCommandApi.class);
            result = execute(deviceCommandApi.poll(deviceId, null, startTimestamp.toString(),
                    30L, maxNumber));
            return new DHResponse<List<DeviceCommand>>(DeviceCommand.createListFromRest(result.getData()),
                    result.getFailureData());
        } else {
            return response;
        }
    }

    public DHResponse<DeviceCommand> sendCommand(String deviceId, String command, List<Parameter> parameters) {
        deviceCommandApi = createService(DeviceCommandApi.class);
        DHResponse<DeviceCommand> response;

        DeviceCommandWrapper wrapper = createDeviceCommandWrapper(command, parameters);
        DHResponse<com.devicehive.rest.model.CommandInsert> result = execute(deviceCommandApi.insert(deviceId, wrapper));

        response = new DHResponse<DeviceCommand>(DeviceCommand.create(result.getData(), command, deviceId,
                wrapper.getParameters()), result.getFailureData());

        if (response.isSuccessful()) {
            return response;
        } else if (response.getFailureData().getCode() == 401) {
            authorize();
            result = execute(deviceCommandApi.insert(deviceId, wrapper));
            return new DHResponse<DeviceCommand>(DeviceCommand.create(result.getData(), command, deviceId,
                    wrapper.getParameters()), result.getFailureData());

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
