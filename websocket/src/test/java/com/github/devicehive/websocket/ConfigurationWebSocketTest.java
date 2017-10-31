package com.github.devicehive.websocket;

import com.github.devicehive.websocket.api.ConfigurationWS;
import com.github.devicehive.websocket.listener.ConfigurationListener;
import com.github.devicehive.websocket.model.repsonse.ConfigurationGetResponse;
import com.github.devicehive.websocket.model.repsonse.ConfigurationInsertResponse;
import com.github.devicehive.websocket.model.repsonse.ErrorResponse;
import com.github.devicehive.websocket.model.repsonse.ResponseAction;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class ConfigurationWebSocketTest extends Helper {
    private static final String CONFIGURATION_NAME = "TEZT C0NF1G";
    private static final String CONFIGURATION_VALUE = "TEST VALUE";

    @Test
    public void putConfigurationProperty() throws InterruptedException, IOException {
        final CountDownLatch latch = new CountDownLatch(1);

        authenticate();

        ConfigurationWS configurationWS = client.createConfigurationWS(new ConfigurationListener() {
            @Override
            public void onGet(ConfigurationGetResponse response) {

            }

            @Override
            public void onPut(ConfigurationInsertResponse response) {
                Assert.assertNotNull(response);
                Assert.assertNotNull(response.getStatus());
                Assert.assertEquals("success", response.getStatus());
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
        latch.await(30, TimeUnit.SECONDS);
        Assert.assertTrue(deleteConfigurations(CONFIGURATION_NAME));
    }
}
