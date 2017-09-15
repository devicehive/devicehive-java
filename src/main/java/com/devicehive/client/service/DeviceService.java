package com.devicehive.client.service;

import com.devicehive.rest.ApiClient;
import com.devicehive.rest.api.DeviceApi;
import com.devicehive.rest.auth.ApiKeyAuth;
import com.devicehive.rest.model.DeviceUpdate;
import retrofit2.Response;

import java.io.IOException;

public class DeviceService extends BaseService {
    private DeviceApi deviceApi;


    public boolean createDevice() {
        deviceApi = createService(DeviceApi.class);
        DeviceUpdate device = new DeviceUpdate();
        device.setName("JavaLibDevice");
        device.setId("java-lib-device");
        device.setNetworkId(1L);
        Response<Void> response = null;
        try {
            response = deviceApi.register(device, device.getId()).execute();

            if (response.isSuccessful()) {
                System.out.println("Success");
            } else if (response.code() == 401) {
                authorize();
                deviceApi = apiClient.createService(DeviceApi.class);
                response = deviceApi.register(device, device.getId()).execute();

                ApiKeyAuth auth = (ApiKeyAuth) apiClient.getApiAuthorizations().get(ApiClient.AUTH_API_KEY);
                System.out.println(auth.getApiKey());
            }
            System.out.println(response.code());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response != null && response.isSuccessful();
    }
}
