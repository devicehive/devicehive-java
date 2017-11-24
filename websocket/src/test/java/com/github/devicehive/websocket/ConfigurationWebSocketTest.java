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
import java.util.Random;
import java.util.concurrent.CountDownLatch;

public class ConfigurationWebSocketTest extends Helper {
    private static final String CONFIGURATION_NAME = "WS T3ZT ";
    private static final String CONFIGURATION_VALUE = "TEST VALUE";
    private static final RESTHelper restHelper = new RESTHelper();

    @Before
    public void preTest() throws InterruptedException, IOException {
        authenticate();
    }

    @Test
    public void putConfigurationProperty() throws InterruptedException, IOException {
        final String configurationName = CONFIGURATION_NAME + new Random().nextLong();
        System.out.println("Configuration name for test: " + configurationName);

        final CountDownLatch latch = new CountDownLatch(1);

        final ConfigurationWS configurationWS = client.createConfigurationWS();
        configurationWS.put(null, configurationName, CONFIGURATION_VALUE);
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

        latch.await(awaitTimeout, awaitTimeUnit);

        Assert.assertEquals(0, latch.getCount());

        restHelper.deleteConfigurations(configurationName);
    }

    @Test
    public void getConfigurationProperty() throws InterruptedException, IOException {
        final String configurationName = CONFIGURATION_NAME + new Random().nextLong();
        System.out.println("Configuration name for test: " + configurationName);

        final CountDownLatch latch = new CountDownLatch(1);

        final ConfigurationWS configurationWS = client.createConfigurationWS();
        configurationWS.put(null, configurationName, CONFIGURATION_VALUE);
        configurationWS.setListener(new ConfigurationListener() {
            @Override
            public void onGet(ConfigurationGetResponse response) {
                Assert.assertEquals(ResponseAction.SUCCESS, response.getStatus());
                Assert.assertEquals(configurationName, response.getConfiguration().getName());
                Assert.assertEquals(CONFIGURATION_VALUE, response.getConfiguration().getValue());
                latch.countDown();
            }

            @Override
            public void onPut(ConfigurationInsertResponse response) {
                Assert.assertEquals(ResponseAction.SUCCESS, response.getStatus());
                configurationWS.get(null, configurationName);
            }

            @Override
            public void onDelete(ResponseAction response) {

            }

            @Override
            public void onError(ErrorResponse error) {

            }

        });
        latch.await(awaitTimeout, awaitTimeUnit);

        Assert.assertEquals(0, latch.getCount());

        restHelper.deleteConfigurations(configurationName);
    }

    @Test
    public void deleteConfigurationProperty() throws InterruptedException, IOException {
        final String configurationName = CONFIGURATION_NAME + new Random().nextLong();
        System.out.println("Configuration name for test: " + configurationName);

        final CountDownLatch latch = new CountDownLatch(1);

        final ConfigurationWS configurationWS = client.createConfigurationWS();
        configurationWS.put(null, configurationName, CONFIGURATION_VALUE);
        configurationWS.setListener(new ConfigurationListener() {
            @Override
            public void onGet(ConfigurationGetResponse response) {

            }

            @Override
            public void onPut(ConfigurationInsertResponse response) {
                Assert.assertEquals(ResponseAction.SUCCESS, response.getStatus());
                configurationWS.delete(null, configurationName);
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
        latch.await(awaitTimeout, awaitTimeUnit);

        Assert.assertEquals(0, latch.getCount());
    }
}
