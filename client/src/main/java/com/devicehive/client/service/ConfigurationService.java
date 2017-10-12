package com.devicehive.client.service;

import com.devicehive.client.model.DHResponse;
import com.devicehive.rest.api.ConfigurationApi;
import com.devicehive.rest.model.Configuration;
import com.devicehive.rest.model.ValueProperty;

class ConfigurationService extends BaseService {

    DHResponse<Configuration> getProperty(String name) {
        ConfigurationApi configurationApi = createService(ConfigurationApi.class);
        DHResponse<Configuration> response = execute(configurationApi.get(name));
        if (response.isSuccessful()) {
            return response;
        } else if (response.getFailureData().getCode() == 401) {
            authorize();
            configurationApi = createService(ConfigurationApi.class);
            return execute(configurationApi.get(name));
        } else {
            return response;
        }
    }

    DHResponse<Configuration> setProperty(String name, String value) {
        ConfigurationApi configurationApi = apiClient.createService(ConfigurationApi.class);
        ValueProperty body = new ValueProperty();
        body.setValue(value);
        DHResponse<Configuration> response = execute(configurationApi.setProperty(name, body));
        if (response.isSuccessful()) {
            return response;
        } else if (response.getFailureData().getCode() == 401) {
            authorize();
            configurationApi = createService(ConfigurationApi.class);
            return execute(configurationApi.setProperty(name, body));
        } else {
            return response;
        }
    }

    DHResponse<Void> removeProperty(String name) {
        ConfigurationApi configurationApi = createService(ConfigurationApi.class);

        DHResponse<Void> response = execute(configurationApi.deleteProperty(name));

        if (response.isSuccessful()) {
            return response;
        } else if (response.getFailureData() != null && response.getFailureData().getCode() == 401) {
            authorize();
            configurationApi = createService(ConfigurationApi.class);
            return execute(configurationApi.deleteProperty(name));
        } else {
            return response;
        }
    }
}
