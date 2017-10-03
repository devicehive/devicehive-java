package com.devicehive.client.service;

import com.devicehive.client.model.DHResponse;
import com.devicehive.rest.api.DeviceApi;
import com.devicehive.rest.model.DeviceUpdate;

public class DeviceService extends BaseService {
    private DeviceApi deviceApi;


    public DHResponse<Void> createDevice(String id, String name) {
        deviceApi = createService(DeviceApi.class);
        DeviceUpdate device = new DeviceUpdate();
        device.setId(id);
        device.setName(name);
        DHResponse<Void> response = execute(deviceApi.register(device, device.getId()));
        if (response.isSuccessful()) {
            return response;
        } else if (response.getFailureData().getCode() == 401) {
            authorize();
            deviceApi = createService(DeviceApi.class);
            return execute(deviceApi.register(device, device.getId()));
        } else {
            return response;
        }
    }

    public Device getDevice(String id) {
        deviceApi = createService(DeviceApi.class);
        DHResponse<Device> response;
        DHResponse<com.devicehive.rest.model.Device> result;

        result = execute(deviceApi.get(id));
        response = new DHResponse<Device>(Device.createDeviceFromRestResponse(result.getData()), result.getFailureData());

        if (response.isSuccessful()) {
            return response.getData();
        } else if (response.getFailureData().getCode() == 401) {
            authorize();
            deviceApi = createService(DeviceApi.class);
            result = execute(deviceApi.get(id));
            response = new DHResponse<Device>(Device.createDeviceFromRestResponse(result.getData()), result.getFailureData());
            if (response.isSuccessful()) {
                return response.getData();
            } else if (response.getFailureData().getCode() == 404) {
                return createAndGetDevice(id);
            } else {
                return null;
            }
        } else if (response.getFailureData().getCode() == 404) {
            return createAndGetDevice(id);
        } else {
            return null;
        }
    }

    private Device createAndGetDevice(String id) {
        createDevice(id, id);
        DHResponse<com.devicehive.rest.model.Device> result = execute(deviceApi.get(id));
        DHResponse<Device> response = new DHResponse<Device>(Device.createDeviceFromRestResponse(result.getData()), result.getFailureData());
        return response.isSuccessful() ? response.getData() : null;

    }
}
