package com.github.devicehive.websocket;

import com.github.devicehive.rest.model.Device;
import com.github.devicehive.rest.model.DeviceUpdate;
import com.github.devicehive.websocket.api.*;
import com.github.devicehive.websocket.listener.ConfigurationListener;
import com.github.devicehive.websocket.listener.DeviceListener;
import com.github.devicehive.websocket.listener.TokenListener;
import com.github.devicehive.websocket.listener.UserListener;
import com.github.devicehive.websocket.model.repsonse.*;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

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
    TokenWS tokenWS = client.createTokenWS();
    DeviceWS deviceWS = client.createDeviceWS();

    void authenticate() throws InterruptedException {
        final CountDownLatch latch = new CountDownLatch(1);
        client.setListener(
                new AuthListener() {
                    @Override
                    public void onSuccess(ResponseAction responseAction) {
                        latch.countDown();
                    }

                    @Override
                    public void onError(ErrorResponse error) {
                        tokenWS.refresh(null, REFRESH_TOKEN);
                        tokenWS.setListener(new TokenListener() {
                            @Override
                            public void onGet(TokenGetResponse response) {

                            }

                            @Override
                            public void onCreate(TokenGetResponse response) {

                            }

                            @Override
                            public void onRefresh(TokenRefreshResponse response) {
                                latch.countDown();
                                client.authenticate(response.getAccessToken());
                            }

                            @Override
                            public void onError(ErrorResponse error) {
                                latch.countDown();
                            }
                        });
                    }
                }
        );
        client.authenticate(ACCESS_TOKEN);
        latch.await(awaitTimeout, awaitTimeUnit);
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

    void registerDevice(String deviceId, DeviceWS deviceWS) {
        DeviceUpdate deviceUpdate = new DeviceUpdate();
        deviceUpdate.setName(deviceId);
        deviceWS.save(null, deviceId, deviceUpdate);
    }

    boolean registerDevice(String deviceId) throws InterruptedException {
        authenticate();
        final CountDownLatch latch = new CountDownLatch(1);
        final AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        DeviceUpdate deviceUpdate = new DeviceUpdate();
        deviceUpdate.setName(deviceId);
        deviceWS.save(null, deviceId, deviceUpdate);
        deviceWS.setListener(new DeviceListener() {
            @Override
            public void onList(List<Device> response) {

            }

            @Override
            public void onGet(Device response) {

            }

            @Override
            public void onDelete(ResponseAction response) {

            }

            @Override
            public void onSave(ResponseAction response) {
                atomicBoolean.set(true);
                latch.countDown();
            }

            @Override
            public void onError(ErrorResponse error) {

            }
        });
        latch.await(awaitTimeout, TimeUnit.SECONDS);
        deviceWS.setListener(null);
        return atomicBoolean.get();
    }

    void deleteDevice(String id) {
        deviceWS.delete(null, id);
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
