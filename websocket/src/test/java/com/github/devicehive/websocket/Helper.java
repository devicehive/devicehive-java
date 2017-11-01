package com.github.devicehive.websocket;

import com.github.devicehive.websocket.api.ConfigurationWS;
import com.github.devicehive.websocket.api.WebSocketClient;
import com.github.devicehive.websocket.listener.ConfigurationListener;
import com.github.devicehive.websocket.model.repsonse.*;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

class Helper {
    private static final String URL = "ws://playground.dev.devicehive.com/api/websocket";
    private static final String ACCESS_TOKEN = "***REMOVED***";
    private static final String REFRESH_TOKEN = "***REMOVED***";

    int awaitTimeout = 30;
    TimeUnit awaitTimeUnit = TimeUnit.SECONDS;

    WebSocketClient client = new WebSocketClient
            .Builder()
            .url(URL)
            .build();

    void authenticate() {
        client.authenticate(ACCESS_TOKEN);
    }

    boolean deleteConfigurations(String name) throws InterruptedException {
        final CountDownLatch latch = new CountDownLatch(1);

        final Counter counter = new Counter();
        final ConfigurationWS configurationWS = client.createConfigurationWS();
        configurationWS.setListener(new ConfigurationListener() {
            @Override
            public void onGet(ConfigurationGetResponse response) {

            }

            @Override
            public void onPut(ConfigurationInsertResponse response) {

            }

            @Override
            public void onDelete(ResponseAction response) {
                if (response.getStatus().equals(ResponseAction.SUCCESS)) {
                    counter.increment();
                }
                latch.countDown();
            }

            @Override
            public void onError(ErrorResponse error) {

            }
        });

        configurationWS.delete(null, name);
        latch.await(awaitTimeout, awaitTimeUnit);
        return counter.getCount() == 1;
    }

}
