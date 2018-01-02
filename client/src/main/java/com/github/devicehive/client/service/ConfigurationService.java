/*
 *
 *
 *   ConfigurationService.java
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
import com.github.devicehive.rest.api.ConfigurationApi;
import com.github.devicehive.rest.model.Configuration;
import com.github.devicehive.rest.model.ValueProperty;

class ConfigurationService extends BaseService {

    DHResponse<Configuration> getProperty(String name) {
        ConfigurationApi configurationApi = createService(ConfigurationApi.class);
        DHResponse<Configuration> response = execute(configurationApi.get(name));
        if (response.isSuccessful()) {
            return response;
        } else if (response.getFailureData().getCode() == 401) {
            refreshAndAuthorize();
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
            refreshAndAuthorize();
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
            refreshAndAuthorize();
            configurationApi = createService(ConfigurationApi.class);
            return execute(configurationApi.deleteProperty(name));
        } else {
            return response;
        }
    }
}
