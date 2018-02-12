/*
 *
 *
 *   DeviceTypeService.java
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
import com.github.devicehive.client.model.DeviceTypeFilter;
import com.github.devicehive.rest.api.DeviceTypeApi;
import com.github.devicehive.rest.model.DeviceTypeInserted;
import com.github.devicehive.rest.model.DeviceTypeUpdate;

import java.util.List;

public class DeviceTypeService extends BaseService {
    private DeviceTypeApi deviceTypeApi;

    DHResponse<DeviceType> createDeviceType(String deviceTypeName, String description) {
        deviceTypeApi = createService(DeviceTypeApi.class);
        DeviceTypeUpdate deviceTypeUpdate = new DeviceTypeUpdate();
        deviceTypeUpdate.setName(deviceTypeName);
        deviceTypeUpdate.setDescription(description);
        DHResponse<DeviceTypeInserted> response = execute(deviceTypeApi.insert(deviceTypeUpdate));
        if (response.isSuccessful()) {
            return DHResponse.create(DeviceType.create(response.getData(), deviceTypeName, description), null);
        } else if (response.getFailureData().getCode() == 401) {
            refreshAndAuthorize();
            deviceTypeApi = createService(DeviceTypeApi.class);
            response = execute(deviceTypeApi.insert(deviceTypeUpdate));
            return DHResponse.create(DeviceType.create(response.getData(), deviceTypeName, description), null);
        } else {
            return DHResponse.create(null, response.getFailureData());
        }
    }

    DHResponse<Void> updateDeviceType(DeviceType deviceType) {
        deviceTypeApi = createService(DeviceTypeApi.class);
        long id = deviceType.getId();
        DeviceTypeUpdate deviceTypeUpdate = new DeviceTypeUpdate();
        deviceTypeUpdate.setName(deviceType.getName());
        deviceTypeUpdate.setDescription(deviceType.getDescription());

        DHResponse<Void> response = execute(deviceTypeApi.update(deviceTypeUpdate, id));
        if (response.isSuccessful()) {
            return response;
        } else if (response.getFailureData().getCode() == 401) {
            refreshAndAuthorize();
            deviceTypeApi = createService(DeviceTypeApi.class);
            return execute(deviceTypeApi.update(deviceTypeUpdate, id));
        } else {
            return response;
        }
    }

    DHResponse<DeviceType> getDeviceType(long deviceId) {
        deviceTypeApi = createService(DeviceTypeApi.class);
        DHResponse<DeviceType> response;
        DHResponse<com.github.devicehive.rest.model.DeviceType> result;

        result = execute(deviceTypeApi.get(deviceId));
        response = new DHResponse<>(DeviceType.create(result.getData()), result.getFailureData());
        if (response.isSuccessful()) {
            return response;
        } else if (response.getFailureData().getCode() == 401) {
            refreshAndAuthorize();
            deviceTypeApi = createService(DeviceTypeApi.class);
            result = execute(deviceTypeApi.get(deviceId));
            response = new DHResponse<>(DeviceType.create(result.getData()), result.getFailureData());
            return response;
        } else {
            return response;
        }
    }

    DHResponse<List<DeviceType>> getDeviceTypes(DeviceTypeFilter filter) {
        deviceTypeApi = createService(DeviceTypeApi.class);
        DHResponse<List<DeviceType>> response;
        DHResponse<List<com.github.devicehive.rest.model.DeviceTypeListItem>> result;
        String sortField = filter.getSortField() == null ? null : filter.getSortField().sortField();

        result = execute(deviceTypeApi.list(filter.getName(), filter.getNamePattern(),
                sortField, filter.getSortOrder().sortOrder(), filter.getTake(), filter.getSkip()));
        response = new DHResponse<>(DeviceType.list(result.getData()), result.getFailureData());
        if (response.isSuccessful()) {
            return response;

        } else if (response.getFailureData().getCode() == 401) {
            refreshAndAuthorize();
            deviceTypeApi = createService(DeviceTypeApi.class);
            result = execute(deviceTypeApi.list(filter.getName(), filter.getNamePattern(),
                    sortField, filter.getSortOrder().sortOrder(), filter.getTake(), filter.getSkip()));
            response = new DHResponse<>(DeviceType.list(result.getData()), result.getFailureData());
            return response;
        } else {
            return response;
        }
    }

    DHResponse<Void> removeDeviceType(long deviceId) {
        deviceTypeApi = createService(DeviceTypeApi.class);
        DHResponse<Void> response = execute(deviceTypeApi.delete(deviceId));
        if (response.isSuccessful()) {
            return response;
        } else if (response.getFailureData().getCode() == 401) {
            refreshAndAuthorize();
            deviceTypeApi = createService(DeviceTypeApi.class);
            return execute(deviceTypeApi.delete(deviceId));
        } else {
            return response;
        }
    }

}
