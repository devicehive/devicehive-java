package com.devicehive.client.service;

import com.devicehive.client.model.DHResponse;
import com.devicehive.rest.api.ConfigurationApi;
import com.devicehive.rest.model.Configuration;

import java.io.IOException;

public class ConfigurationService extends BaseService {

    public DHResponse<Configuration> getProperty(String name) throws IOException {
        authorize();
        ConfigurationApi configurationApi = apiClient.createService(ConfigurationApi.class);
        DHResponse<Configuration> response = execute(configurationApi.get(name));
        if (response.isSuccessful()) {
            return response;
        } else if (response.getFailureData().getCode() == 401) {
            refreshToken();
            authorize();
            configurationApi = apiClient.createService(ConfigurationApi.class);
            return execute(configurationApi.get(name));
        } else {
            return response;
        }
    }
}
