package com.devicehive.client.service;

import com.devicehive.client.model.DHResponse;
import com.devicehive.client.model.Device;
import com.devicehive.rest.api.DeviceApi;
import com.devicehive.rest.model.DeviceUpdate;

import java.io.IOException;

public class DeviceService extends BaseService {
    private DeviceApi deviceApi;


    public DHResponse<Void> createDevice(String id, String name) throws IOException {
        deviceApi = createService(DeviceApi.class);
        DeviceUpdate device = new DeviceUpdate();
        device.setName(name);
        device.setId(id);
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

    public DHResponse<Device> getDevice(String id) throws IOException {
        deviceApi = createService(DeviceApi.class);
        DHResponse<Device> response;

        DHResponse<com.devicehive.rest.model.Device> result = execute(deviceApi.get(id));
        response = new DHResponse<Device>(Device.createDeviceFromRestResponse(result.getData()), result.getFailureData());
        if (response.isSuccessful()) {
            return response;
        } else if (response.getFailureData().getCode() == 401) {
            authorize();
            deviceApi = createService(DeviceApi.class);
            result = execute(deviceApi.get(id));
            return new DHResponse<Device>(Device.createDeviceFromRestResponse(result.getData()), result.getFailureData());
        } else {
            return response;
        }
    }
}
