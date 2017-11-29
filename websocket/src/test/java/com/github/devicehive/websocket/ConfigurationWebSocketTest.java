package com.github.devicehive.websocket;

import com.github.devicehive.websocket.api.ConfigurationWS;
import com.github.devicehive.websocket.listener.ConfigurationListener;
import com.github.devicehive.websocket.model.repsonse.ConfigurationGetResponse;
import com.github.devicehive.websocket.model.repsonse.ConfigurationInsertResponse;
import com.github.devicehive.websocket.model.repsonse.ErrorResponse;
import com.github.devicehive.websocket.model.repsonse.ResponseAction;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ConfigurationWebSocketTest extends Helper {
    private static final String CONFIGURATION_NAME = "WS T3ZT ";
    private static final String CONFIGURATION_VALUE = "TEST VALUE";
    private static final RESTHelper restHelper = new RESTHelper();
    private CountDownLatch latch;
    private ConfigurationWS configurationWS;
    private String configurationName;
    private boolean configurationDeleted = false;

    @Before
    public void preTest() throws InterruptedException, IOException {
        authenticate();
        latch = new CountDownLatch(1);
        configurationWS = client.createConfigurationWS();
        configurationName = CONFIGURATION_NAME + new Random().nextInt();
    }

    @After
    public void clean() throws InterruptedException {
        if (configurationDeleted) {
            return;
        }
        deleteConfiguration(configurationWS,configurationName);
    }

    @Test
    public void putConfigurationProperty() throws InterruptedException, IOException {
        configurationWS.setListener(new ConfigurationListener() {
            @Override
            public void onGet(ConfigurationGetResponse response) {

            }

            @Override
            public void onPut(ConfigurationInsertResponse response) {
                latch.countDown();
            }

            @Override
            public void onDelete(ResponseAction response) {

            }

            @Override
            public void onError(ErrorResponse error) {
                System.out.println(error);
                Assert.assertTrue(false);
            }

        });
        configurationWS.put(null, configurationName, CONFIGURATION_VALUE);
        latch.await(awaitTimeout, awaitTimeUnit);
        Assert.assertEquals(0, latch.getCount());
    }

    @Test
    public void getConfigurationProperty() throws InterruptedException, IOException {
        configurationWS.setListener(new ConfigurationListener() {
            @Override
            public void onGet(ConfigurationGetResponse response) {
                Assert.assertEquals(configurationName, response.getConfiguration().getName());
                Assert.assertEquals(CONFIGURATION_VALUE, response.getConfiguration().getValue());
                latch.countDown();
            }

            @Override
            public void onPut(ConfigurationInsertResponse response) {
                configurationWS.get(null, configurationName);
            }

            @Override
            public void onDelete(ResponseAction response) {

            }

            @Override
            public void onError(ErrorResponse error) {

            }

        });
        configurationWS.put(null, configurationName, CONFIGURATION_VALUE);
        latch.await(awaitTimeout, awaitTimeUnit);
        Assert.assertEquals(0, latch.getCount());
    }

    @Test
    public void deleteConfigurationProperty() throws InterruptedException, IOException {

        configurationWS.setListener(new ConfigurationListener() {
            @Override
            public void onGet(ConfigurationGetResponse response) {

            }

            @Override
            public void onPut(ConfigurationInsertResponse response) {
                configurationWS.delete(null, configurationName);
            }

            @Override
            public void onDelete(ResponseAction response) {
                configurationDeleted = true;
                latch.countDown();
            }

            @Override
            public void onError(ErrorResponse error) {

            }

        });
        configurationWS.put(null, configurationName, CONFIGURATION_VALUE);
        latch.await(awaitTimeout, awaitTimeUnit);
        Assert.assertEquals(0, latch.getCount());
    }
}
