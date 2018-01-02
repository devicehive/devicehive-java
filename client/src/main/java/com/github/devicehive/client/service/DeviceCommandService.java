/*
 *
 *
 *   DeviceCommandService.java
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
import com.github.devicehive.client.model.Parameter;
import com.github.devicehive.rest.api.DeviceCommandApi;
import com.github.devicehive.rest.model.DeviceCommandWrapper;

import org.joda.time.DateTime;
import org.joda.time.Period;

import java.util.List;

class DeviceCommandService extends BaseService {

    DHResponse<List<DeviceCommand>> getDeviceCommands(String deviceId, DateTime startTimestamp,
                                                      DateTime endTimestamp, int maxNumber) {
        return getDeviceCommands(deviceId, null, startTimestamp, endTimestamp, maxNumber);
    }

    private DHResponse<List<DeviceCommand>> getDeviceCommands(String deviceId, String deviceIds,
                                                              DateTime startTimestamp, DateTime endTimestamp,
                                                              int maxNumber) {

        DeviceCommandApi deviceCommandApi = createService(DeviceCommandApi.class);

        Period period = new Period(startTimestamp, endTimestamp);

        DHResponse<List<DeviceCommand>> response;

        DHResponse<List<com.github.devicehive.rest.model.DeviceCommand>> result =
                execute(deviceCommandApi.poll(deviceId, deviceIds, startTimestamp.toString(),
                        (long) period.toStandardSeconds().getSeconds(), maxNumber));

        response = new DHResponse<>(
                DeviceCommand.createListFromRest(result.getData()),
                result.getFailureData());
        if (response.isSuccessful()) {
            return response;
        } else if (response.getFailureData().getCode() == 401) {
            refreshAndAuthorize();
            deviceCommandApi = createService(DeviceCommandApi.class);
            result = execute(deviceCommandApi.poll(deviceId, null, startTimestamp.toString(),
                    30L, maxNumber));
            return new DHResponse<>(DeviceCommand.createListFromRest(result.getData()),
                    result.getFailureData());
        } else {
            return response;
        }
    }

    DHResponse<DeviceCommand> sendCommand(String deviceId, long networkId, String command, List<Parameter> parameters) {
        DeviceCommandApi deviceCommandApi = createService(DeviceCommandApi.class);
        DHResponse<DeviceCommand> response;

        DeviceCommandWrapper wrapper = createDeviceCommandWrapper(command, parameters);
        DHResponse<com.github.devicehive.rest.model.CommandInsert> result = execute(deviceCommandApi.insert(deviceId, wrapper));

        response = new DHResponse<>(DeviceCommand.create(result.getData(), command, deviceId, networkId,
                wrapper.getParameters()), result.getFailureData());

        if (response.isSuccessful()) {
            return response;
        } else if (response.getFailureData().getCode() == 401) {
            refreshAndAuthorize();
            deviceCommandApi = createService(DeviceCommandApi.class);
            result = execute(deviceCommandApi.insert(deviceId, wrapper));
            return new DHResponse<>(DeviceCommand.create(result.getData(), command, deviceId, networkId,
                    wrapper.getParameters()), result.getFailureData());

        } else {
            return response;
        }
    }


    private DeviceCommandWrapper createDeviceCommandWrapper(String command, List<Parameter> parameters) {
        DeviceCommandWrapper commandWrapper = new DeviceCommandWrapper();
        commandWrapper.setCommand(command);
        commandWrapper.setTimestamp(DateTime.now());
        if (parameters != null) {
            commandWrapper.setParameters(wrapParameters(parameters));
        }
        return commandWrapper;

    }

    DHResponse<com.github.devicehive.rest.model.DeviceCommand> getCommand(String deviceId, long commandId) {
        DeviceCommandApi deviceCommandApi = createService(DeviceCommandApi.class);
        DHResponse<com.github.devicehive.rest.model.DeviceCommand> response = execute(deviceCommandApi.get(deviceId, String.valueOf(commandId)));
        if (response.isSuccessful()) {
            return new DHResponse<>(response.getData(), response.getFailureData());
        } else if (response.getFailureData().getCode() == 401) {
            refreshAndAuthorize();
            deviceCommandApi = createService(DeviceCommandApi.class);
            return execute(deviceCommandApi.get(deviceId, String.valueOf(commandId)));
        } else {
            return response;
        }
    }

    DHResponse<Void> updateCommand(String deviceId, long commandId, DeviceCommandWrapper body) {
        DeviceCommandApi deviceCommandApi = createService(DeviceCommandApi.class);
        DHResponse<Void> response = execute(deviceCommandApi.update(deviceId, commandId, body));
        if (response.isSuccessful()) {
            return response;
        } else if (response.getFailureData().getCode() == 401) {
            refreshAndAuthorize();
            deviceCommandApi = createService(DeviceCommandApi.class);
            return execute(deviceCommandApi.update(deviceId, commandId, body));
        } else {
            return response;
        }
    }

}
