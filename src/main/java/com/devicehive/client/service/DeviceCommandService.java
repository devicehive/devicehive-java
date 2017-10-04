package com.devicehive.client.service;

import com.devicehive.client.model.*;
import com.devicehive.rest.api.DeviceCommandApi;
import com.devicehive.rest.model.DeviceCommandWrapper;
import org.joda.time.DateTime;
import org.joda.time.Period;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DeviceCommandService extends BaseService {

    public static final String CANCELED = "Canceled";
    private DeviceCommandApi deviceCommandApi;
    private Call<List<com.devicehive.rest.model.DeviceCommand>> pollCall;
    private boolean isSubscribed = false;
    private Callback<List<com.devicehive.rest.model.DeviceCommand>> pollCommandsCallback;
    private Call<List<com.devicehive.rest.model.DeviceCommand>> pollManyCall;


    public DHResponse<List<DeviceCommand>> getDeviceCommands(String deviceId, DateTime startTimestamp,
                                                             DateTime endTimestamp, int maxNumber) throws IOException {
        return getDeviceCommands(deviceId, null, startTimestamp, endTimestamp, maxNumber);
    }

    private DHResponse<List<DeviceCommand>> getDeviceCommands(String deviceId, String deviceIds,
                                                              DateTime startTimestamp, DateTime endTimestamp,
                                                              int maxNumber) throws IOException {

        deviceCommandApi = createService(DeviceCommandApi.class);

        Period period = new Period(startTimestamp, endTimestamp);

        DHResponse<List<DeviceCommand>> response;

        DHResponse<List<com.devicehive.rest.model.DeviceCommand>> result =
                execute(deviceCommandApi.poll(deviceId, deviceIds, startTimestamp.toString(),
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

    public void pollCommands(final String deviceId, final CommandFilter filter, final boolean isAuthNeeded,
                             final DeviceCommandsCallback commandsCallback) {
        isSubscribed = true;
        deviceCommandApi = createService(DeviceCommandApi.class);
        Period period = new Period(filter.getStartTimestamp(), filter.getEndTimestamp());
        if (pollCall != null && pollCall.isExecuted()) {
            pollCall.cancel();
            pollCall = null;
        }
        pollCall = deviceCommandApi.poll(deviceId, filter.getCommandNames(), filter.getStartTimestamp().toString(),
                (long) period.toStandardSeconds().getSeconds(), filter.getMaxNumber());

        pollCall.enqueue(getCommandsCallback(deviceId, filter, isAuthNeeded, commandsCallback));

    }

    private Callback<List<com.devicehive.rest.model.DeviceCommand>> getCommandsCallback(final String deviceId, final CommandFilter filter, final boolean isAuthNeeded,
                                                                                        final DeviceCommandsCallback commandsCallback) {
        if (pollCommandsCallback == null) {
            pollCommandsCallback = new Callback<List<com.devicehive.rest.model.DeviceCommand>>() {
                public void onResponse(Call<List<com.devicehive.rest.model.DeviceCommand>> call,
                                       Response<List<com.devicehive.rest.model.DeviceCommand>> response) {
                    if (response.isSuccessful()) {
                        List<DeviceCommand> commands = new ArrayList<DeviceCommand>();
                        commands.addAll(DeviceCommand.createList(response.body()));
                        commandsCallback.onSuccess(commands);
                        if (isSubscribed) {
                            filter.setStartTimestamp(DateTime.now());
                            filter.setEndTimestamp(DateTime.now().plusSeconds(30));
                            pollCommands(deviceId, filter, false, commandsCallback);
                        }
                    } else if (response.code() == 401 && isAuthNeeded) {
                        System.out.println("AUTH");
                        authorize();
                        pollCommands(deviceId, filter, false, commandsCallback);
                    } else {
                        commandsCallback.onFail(createFailData(response.code(), parseErrorMessage(response)));
                        unsubscribeAll();
                    }
                }

                public void onFailure(Call<List<com.devicehive.rest.model.DeviceCommand>> call, Throwable t) {
                    if (!t.getMessage().equals(CANCELED)) {
                        commandsCallback.onFail(new FailureData(FailureData.NO_CODE, t.getMessage()));
                        unsubscribeAll();
                    }

                }
            };
        }
        return pollCommandsCallback;
    }

    public void pollManyCommands(String deviceIds, CommandFilter filter, boolean isAuthNeeded,
                                 DeviceCommandsCallback commandsCallback) {
        isSubscribed = true;
        deviceCommandApi = createService(DeviceCommandApi.class);
        Period period = new Period(filter.getStartTimestamp(), filter.getEndTimestamp());
        if (pollManyCall != null && pollManyCall.isExecuted()) {
            pollManyCall.cancel();
            pollManyCall = null;
        }
        pollManyCall = deviceCommandApi.pollMany(deviceIds, filter.getCommandNames(), filter.getStartTimestamp().toString(), (long) period.toStandardSeconds().getSeconds(), filter.getMaxNumber());

        pollManyCall.enqueue(getCommandsAllCallback(deviceIds, filter, isAuthNeeded, commandsCallback));

    }


    private Callback<List<com.devicehive.rest.model.DeviceCommand>> getCommandsAllCallback(
            final String deviceIds, final CommandFilter filter, final boolean isAuthNeeded,
            final DeviceCommandsCallback commandsCallback) {
        if (pollCommandsCallback == null) {
            pollCommandsCallback = new Callback<List<com.devicehive.rest.model.DeviceCommand>>() {
                public void onResponse(Call<List<com.devicehive.rest.model.DeviceCommand>> call,
                                       Response<List<com.devicehive.rest.model.DeviceCommand>> response) {
                    if (response.isSuccessful()) {
                        List<DeviceCommand> notifications = new ArrayList<DeviceCommand>();
                        notifications.addAll(DeviceCommand.createList(response.body()));
                        commandsCallback.onSuccess(notifications);
                        if (isSubscribed) {
                            filter.setStartTimestamp(DateTime.now());
                            filter.setEndTimestamp(DateTime.now().plusSeconds(30));
                            pollManyCommands(deviceIds, filter, false, commandsCallback);
                        }
                    } else if (response.code() == 401 && isAuthNeeded) {
                        System.out.println("AUTH");
                        authorize();
                        pollManyCommands(deviceIds, filter, false, commandsCallback);
                    } else {
                        commandsCallback.onFail(createFailData(response.code(), parseErrorMessage(response)));
                        unsubscribeAll();
                    }
                }

                public void onFailure(Call<List<com.devicehive.rest.model.DeviceCommand>> call, Throwable t) {
                    if (!t.getMessage().equals(CANCELED)) {
                        commandsCallback.onFail(new FailureData(FailureData.NO_CODE, t.getMessage()));
                        unsubscribeAll();
                    }

                }
            };
        }
        return pollCommandsCallback;
    }

    public void unsubscribeAll() {
        isSubscribed = false;
        if (pollCall != null) {
            pollCall.cancel();
        }
    }
}
