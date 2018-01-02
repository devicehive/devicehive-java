/*
 *
 *
 *   DeviceService.java
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
import com.github.devicehive.client.model.DeviceFilter;
import com.github.devicehive.rest.api.DeviceApi;
import com.github.devicehive.rest.model.DeviceUpdate;

import java.util.List;

class DeviceService extends BaseService {
    private DeviceApi deviceApi;


    DHResponse<Void> createDevice(String deviceId, String name) {
        deviceApi = createService(DeviceApi.class);
        DeviceUpdate device = new DeviceUpdate();
        device.setName(name);
        DHResponse<Void> response = execute(deviceApi.register(device, deviceId));
        if (response.isSuccessful()) {
            return response;
        } else if (response.getFailureData().getCode() == 401) {
            refreshAndAuthorize();
            deviceApi = createService(DeviceApi.class);
            return execute(deviceApi.register(device, deviceId));
        } else {
            return response;
        }
    }

    DHResponse<Void> updateDevice(Device device) {
        deviceApi = createService(DeviceApi.class);
        String id = device.getId();
        DeviceUpdate deviceUpdate = new DeviceUpdate();
        deviceUpdate.setName(device.getName());
        deviceUpdate.setBlocked(device.getBlocked());
        deviceUpdate.setNetworkId(device.getNetworkId());
        deviceUpdate.setData(device.getData());

        DHResponse<Void> response = execute(deviceApi.register(deviceUpdate,id));
        if (response.isSuccessful()) {
            return response;
        } else if (response.getFailureData().getCode() == 401) {
            refreshAndAuthorize();
            deviceApi = createService(DeviceApi.class);
            return execute(deviceApi.register(deviceUpdate,id));
        } else {
            return response;
        }
    }

    DHResponse<Device> getDevice(String deviceId) {
        deviceApi = createService(DeviceApi.class);
        DHResponse<Device> response;
        DHResponse<com.github.devicehive.rest.model.Device> result;

        result = execute(deviceApi.get(deviceId));
        response = new DHResponse<Device>(Device.create(result.getData()), result.getFailureData());
        if (response.isSuccessful()) {
            return response;
        } else if (response.getFailureData().getCode() == 401) {
            refreshAndAuthorize();
            deviceApi = createService(DeviceApi.class);
            result = execute(deviceApi.get(deviceId));
            response = new DHResponse<Device>(Device.create(result.getData()), result.getFailureData());
            if (response.isSuccessful()) {
                return response;
            } else if (response.getFailureData().getCode() == 404 || response.getFailureData().getCode() == 403) {
                return createAndGetDevice(deviceId);
            } else {
                return response;
            }
        } else if (response.getFailureData().getCode() == 404 || response.getFailureData().getCode() == 403) {
            return createAndGetDevice(deviceId);
        } else {
            return response;
        }
    }

    DHResponse<List<Device>> getDevices(DeviceFilter filter) {
        deviceApi = createService(DeviceApi.class);
        DHResponse<List<Device>> response;
        DHResponse<List<com.github.devicehive.rest.model.Device>> result;
        String sortField = filter.getSortField() == null ? null : filter.getSortField().sortField();

        result = execute(deviceApi.list(filter.getName(), filter.getNamePattern(), filter.getNetworkId(), filter.getNetworkName(),
                sortField, filter.getSortOrder().sortOrder(), filter.getTake(), filter.getSkip()));
        response = new DHResponse<List<Device>>(Device.list(result.getData()), result.getFailureData());
        if (response.isSuccessful()) {
            return response;

        } else if (response.getFailureData().getCode() == 401) {
            refreshAndAuthorize();
            deviceApi = createService(DeviceApi.class);
            result = execute(deviceApi.list(filter.getName(), filter.getNamePattern(), filter.getNetworkId(), filter.getNetworkName(),
                    sortField, filter.getSortOrder().sortOrder(), filter.getTake(), filter.getSkip()));
            response = new DHResponse<List<Device>>(Device.list(result.getData()), result.getFailureData());
            return response;
        } else {
            return response;
        }
    }

    DHResponse<Void> removeDevice(String deviceId) {
        deviceApi = createService(DeviceApi.class);
        DHResponse<Void> response = execute(deviceApi.delete(deviceId));
        if (response.isSuccessful()) {
            return response;
        } else if (response.getFailureData().getCode() == 401) {
            refreshAndAuthorize();
            deviceApi = createService(DeviceApi.class);
            return execute(deviceApi.delete(deviceId));
        } else {
            return response;
        }
    }

    private DHResponse<Device> createAndGetDevice(String id) {
        createDevice(id, id);
        DHResponse<com.github.devicehive.rest.model.Device> result = execute(deviceApi.get(id));
        return new DHResponse<>(Device.create(result.getData()), result.getFailureData());

    }
}
