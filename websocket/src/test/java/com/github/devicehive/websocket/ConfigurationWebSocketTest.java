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

    @Before
    public void preTest() throws InterruptedException, IOException {
        authenticate();
        latch = new CountDownLatch(1);
        configurationWS = client.createConfigurationWS();
        configurationName = CONFIGURATION_NAME + new Random().nextInt();
    }

    @After
    public void clean() {
        configurationWS.delete(null, configurationName);
    }

    @Test
    public void A_putConfigurationProperty() throws InterruptedException, IOException {
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
                Assert.assertTrue(false);
            }

        });
        configurationWS.put(null, configurationName, CONFIGURATION_VALUE);
        latch.await(awaitTimeout, awaitTimeUnit);
        Assert.assertEquals(0, latch.getCount());
    }

    @Test
    public void B_getConfigurationProperty() throws InterruptedException, IOException {
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
        configurationWS.put(null, configurationName, CONFIGURATION_VALUE);
        latch.await(awaitTimeout, awaitTimeUnit);
        Assert.assertEquals(0, latch.getCount());
    }

    @Test
    public void C_deleteConfigurationProperty() throws InterruptedException, IOException {
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
        configurationWS.put(null, configurationName, CONFIGURATION_VALUE);
        latch.await(awaitTimeout, awaitTimeUnit);
        Assert.assertEquals(0, latch.getCount());
    }
}
