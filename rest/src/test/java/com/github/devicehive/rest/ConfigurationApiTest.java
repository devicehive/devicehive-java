/*
 *
 *
 *   ConfigurationApiTest.java
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

package com.github.devicehive.rest;

import com.github.devicehive.rest.api.ConfigurationApi;
import com.github.devicehive.rest.model.Configuration;
import com.github.devicehive.rest.model.ValueProperty;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import retrofit2.Response;

import java.io.IOException;
import java.util.Random;

public class ConfigurationApiTest extends Helper{
    private static final String CONFIGURATION_NAME = "HTTP T3ZT ";
    private static final String CONFIGURATION_VALUE = "TEST VALUE";

    private void authorise() throws IOException {
        boolean authenticated = authenticate();
        Assert.assertTrue(authenticated);
    }

    @Before
    public void preTest() throws IOException {
        authorise();
    }

    @Test
    public void setConfigurationProperty() throws IOException {
        ConfigurationApi configurationApi = client.createService(ConfigurationApi.class);
        String configurationName = CONFIGURATION_NAME + new Random().nextLong();
        System.out.println("Configuration name for test: " + configurationName);

        ValueProperty valueProperty = new ValueProperty();
        valueProperty.setValue(CONFIGURATION_VALUE);

        Response<Configuration> putResponse = configurationApi.setProperty(configurationName, valueProperty).execute();
        Assert.assertTrue(putResponse.isSuccessful());

        Assert.assertTrue(deleteConfigurations(configurationName));
    }

    @Test
    public void getConfigurationProperty() throws IOException {
        ConfigurationApi configurationApi = client.createService(ConfigurationApi.class);
        String configurationName = CONFIGURATION_NAME + new Random().nextLong();
        System.out.println("Configuration name for test: " + configurationName);

        ValueProperty valueProperty = new ValueProperty();
        valueProperty.setValue(configurationName);

        Response<Configuration> putResponse = configurationApi.setProperty(configurationName, valueProperty).execute();
        Assert.assertTrue(putResponse.isSuccessful());

        Response<Configuration> getResponse = configurationApi.get(configurationName).execute();
        Assert.assertTrue(getResponse.isSuccessful());
        Assert.assertNotNull(getResponse.body().getValue());
        Assert.assertEquals(configurationName, getResponse.body().getValue());

        Assert.assertTrue(deleteConfigurations(configurationName));
    }

    @Test
    public void deleteConfigurationProperty() throws IOException {
        ConfigurationApi configurationApi = client.createService(ConfigurationApi.class);
        String configurationName = CONFIGURATION_NAME + new Random().nextLong();
        System.out.println("Configuration name for test: " + configurationName);

        ValueProperty valueProperty = new ValueProperty();
        valueProperty.setValue(configurationName);

        Response<Configuration> putResponse = configurationApi.setProperty(configurationName, valueProperty).execute();
        Assert.assertTrue(putResponse.isSuccessful());

        Response<Void> deleteResponse = configurationApi.deleteProperty(configurationName).execute();
        Assert.assertTrue(deleteResponse.isSuccessful());
    }

}
