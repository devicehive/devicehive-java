package com.github.devicehive.websocket;

import com.github.devicehive.websocket.api.ConfigurationWS;
import com.github.devicehive.websocket.listener.ConfigurationListener;
import com.github.devicehive.websocket.model.repsonse.ConfigurationGetResponse;
import com.github.devicehive.websocket.model.repsonse.ConfigurationInsertResponse;
import com.github.devicehive.websocket.model.repsonse.ErrorResponse;
import com.github.devicehive.websocket.model.repsonse.ResponseAction;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

public class ConfigurationWebSocketTest extends Helper {
    private static final String CONFIGURATION_NAME = "TEZT C0NF1G";
    private static final String CONFIGURATION_VALUE = "TEST VALUE";

    @Before
    public void preTest() throws InterruptedException, IOException {
        authenticate();

        RESTHelper restHelper = new RESTHelper();
        restHelper.cleanUpConfigurations(CONFIGURATION_NAME);
    }

    @Test
    public void putConfigurationProperty() throws InterruptedException, IOException {
        final CountDownLatch latch = new CountDownLatch(1);

        authenticate();

        final ConfigurationWS configurationWS = client.createConfigurationWS();
        configurationWS.setListener(new ConfigurationListener() {
            @Override
            public void onGet(ConfigurationGetResponse response) {

            }

            @Override
            public void onPut(ConfigurationInsertResponse response) {
                Assert.assertEquals(ResponseAction.SUCCESS, response.getStatus());
                latch.countDown();
            }

            @Override
            public void onDelete(ResponseAction response) {

            }

            @Override
            public void onError(ErrorResponse error) {

            }

        });

        configurationWS.put(null, CONFIGURATION_NAME, CONFIGURATION_VALUE);
        latch.await(awaitTimeout, awaitTimeUnit);

        deleteConfiguration(CONFIGURATION_NAME);
    }

    @Test
    public void getConfigurationProperty() throws InterruptedException, IOException {
        final CountDownLatch latch = new CountDownLatch(1);

        authenticate();

        final ConfigurationWS configurationWS = client.createConfigurationWS();
        configurationWS.setListener(new ConfigurationListener() {
            @Override
            public void onGet(ConfigurationGetResponse response) {
                Assert.assertEquals(ResponseAction.SUCCESS, response.getStatus());
                Assert.assertEquals(CONFIGURATION_NAME, response.getConfiguration().getName());
                Assert.assertEquals(CONFIGURATION_VALUE, response.getConfiguration().getValue());
                latch.countDown();
            }

            @Override
            public void onPut(ConfigurationInsertResponse response) {
                Assert.assertEquals(ResponseAction.SUCCESS, response.getStatus());
                configurationWS.get(null, CONFIGURATION_NAME);
            }

            @Override
            public void onDelete(ResponseAction response) {

            }

            @Override
            public void onError(ErrorResponse error) {

            }

        });

        configurationWS.put(null, CONFIGURATION_NAME, CONFIGURATION_VALUE);
        latch.await(awaitTimeout, awaitTimeUnit);

        deleteConfiguration(CONFIGURATION_NAME);
    }

    @Test
    public void deleteConfigurationProperty() throws InterruptedException, IOException {
        final CountDownLatch latch = new CountDownLatch(1);

        authenticate();

        final ConfigurationWS configurationWS = client.createConfigurationWS();
        configurationWS.setListener(new ConfigurationListener() {
            @Override
            public void onGet(ConfigurationGetResponse response) {

            }

            @Override
            public void onPut(ConfigurationInsertResponse response) {
                Assert.assertEquals(ResponseAction.SUCCESS, response.getStatus());
                configurationWS.delete(null, CONFIGURATION_NAME);
            }

            @Override
            public void onDelete(ResponseAction response) {
                Assert.assertEquals(ResponseAction.SUCCESS, response.getStatus());
                latch.countDown();
            }

            @Override
            public void onError(ErrorResponse error) {

            }

        });

        configurationWS.put(null, CONFIGURATION_NAME, CONFIGURATION_VALUE);
        latch.await(awaitTimeout, awaitTimeUnit);
    }
}
