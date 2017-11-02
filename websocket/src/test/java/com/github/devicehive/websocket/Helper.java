package com.github.devicehive.websocket;

import com.github.devicehive.websocket.api.ConfigurationWS;
import com.github.devicehive.websocket.api.UserWS;
import com.github.devicehive.websocket.api.WebSocketClient;
import com.github.devicehive.websocket.listener.ConfigurationListener;
import com.github.devicehive.websocket.listener.UserListener;
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

    boolean deleteConfiguration(String name) throws InterruptedException {
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

    boolean deleteUser(Long id) throws InterruptedException {
        final CountDownLatch latch = new CountDownLatch(1);

        authenticate();

        final Counter counter = new Counter();
        final UserWS userWS = client.createUserWS();
        userWS.setListener(new UserListener() {
            @Override
            public void onList(UserListResponse response) {

            }

            @Override
            public void onGet(UserGetResponse response) {

            }

            @Override
            public void onInsert(UserInsertResponse response) {

            }

            @Override
            public void onUpdate(ResponseAction response) {

            }

            @Override
            public void onDelete(ResponseAction response) {
                if (response.getStatus().equals(ResponseAction.SUCCESS)) {
                    counter.increment();
                }
                latch.countDown();
            }

            @Override
            public void onGetCurrent(UserGetCurrentResponse response) {

            }

            @Override
            public void onUpdateCurrent(ResponseAction response) {

            }

            @Override
            public void onGetNetwork(UserGetNetworkResponse response) {

            }

            @Override
            public void onAssignNetwork(ResponseAction response) {

            }

            @Override
            public void onUnassignNetwork(ResponseAction response) {

            }

            @Override
            public void onError(ErrorResponse error) {

            }

        });

        userWS.delete(null, id);
        latch.await(awaitTimeout, awaitTimeUnit);
        return counter.getCount() == 1;
    }

}
