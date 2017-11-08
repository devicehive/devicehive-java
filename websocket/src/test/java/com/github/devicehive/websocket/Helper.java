package com.github.devicehive.websocket;

import com.github.devicehive.rest.model.Device;
import com.github.devicehive.rest.model.DeviceUpdate;
import com.github.devicehive.rest.model.NetworkUpdate;
import com.github.devicehive.websocket.api.*;
import com.github.devicehive.websocket.listener.*;
import com.github.devicehive.websocket.model.repsonse.*;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

class Helper {
    private static final String URL = "ws://playground.dev.devicehive.com/api/websocket";
    private String accessToken = "eyJhbGciOiJIUzI1NiJ9.eyJwYXlsb2FkIjp7InUiOjEsImEiOlswXSwibiI6WyIqIl0sImQiOlsiKiJdLCJlIjoxNTA5NzIwMDQwNzE4LCJ0IjoxfX0.G_u8MiEKDKnWJdplfBo6_MI5BNyupaOLsg46PsdNRa8";
    private static final String REFRESH_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJwYXlsb2FkIjp7InUiOjEsImEiOlswXSwibiI6WyIqIl0sImQiOlsiKiJdLCJlIjoxNTI0MTUxMjAxMTA4LCJ0IjowfX0.2wfpmIjrHRtGBoSF3-T77aSAiUYPFSGtgBuGoVZtSxc";

    int awaitTimeout = 30;
    TimeUnit awaitTimeUnit = TimeUnit.SECONDS;

    WebSocketClient client = new WebSocketClient
            .Builder()
            .url(URL)
            .build();

    private TokenWS tokenWS = client.createTokenWS();
    private DeviceWS deviceWS = client.createDeviceWS();
    private NetworkWS networkWS = client.createNetworkWS();

    private static Long networkId;

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
                        refresh(latch);
                    }
                }
        );

        if (accessToken != null && !accessToken.isEmpty()) {
            client.authenticate(accessToken);
        } else {
            refresh(latch);
        }
        latch.await(awaitTimeout, awaitTimeUnit);
    }

    private void refresh(final CountDownLatch latch) {
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
                accessToken = response.getAccessToken();
                client.authenticate(accessToken);
            }

            @Override
            public void onError(ErrorResponse error) {
                latch.countDown();
            }
        });
        tokenWS.refresh(null, REFRESH_TOKEN);
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


    long registerNetwork(String networkName) throws InterruptedException {
        authenticate();
        final CountDownLatch latch = new CountDownLatch(1);
        final AtomicLong atomicLong = new AtomicLong(-1);
        NetworkUpdate networkUpdate = new NetworkUpdate();
        networkUpdate.setName(networkName);
        networkWS.insert(null, networkUpdate);
        networkWS.setListener(new NetworkListener() {
            @Override
            public void onList(NetworkListResponse response) {

            }

            @Override
            public void onGet(NetworkGetResponse response) {

            }

            @Override
            public void onInsert(NetworkInsertResponse response) {
                atomicLong.set(response.getNetwork().getId());
                latch.countDown();
            }

            @Override
            public void onDelete(ResponseAction response) {

            }

            @Override
            public void onUpdate(ResponseAction response) {

            }

            @Override
            public void onError(ErrorResponse error) {
                latch.countDown();
                System.out.println(error);
            }
        });
        latch.await(awaitTimeout, TimeUnit.SECONDS);
        networkWS.setListener(null);
        return atomicLong.get();
    }

    void deleteNetwork(long id) {
        networkWS.delete(null, id);
    }

    boolean safeDeleteNetwork(Long id) throws InterruptedException {
        final CountDownLatch latch = new CountDownLatch(1);
        authenticate();

        final Counter counter = new Counter();
        final NetworkWS localNetworkWS = client.createNetworkWS();
        localNetworkWS.setListener(new NetworkListener() {
            @Override
            public void onList(NetworkListResponse response) {

            }

            @Override
            public void onGet(NetworkGetResponse response) {

            }

            @Override
            public void onInsert(NetworkInsertResponse response) {
            }

            @Override
            public void onDelete(ResponseAction response) {
                if (response.getStatus().equals(ResponseAction.SUCCESS)) {
                    counter.increment();
                }
                latch.countDown();
            }

            @Override
            public void onUpdate(ResponseAction response) {

            }

            @Override
            public void onError(ErrorResponse error) {

            }
        });
        localNetworkWS.delete(null, id);
        latch.await(awaitTimeout, awaitTimeUnit);
        return counter.getCount() == 1;
    }

    boolean safeDeleteUser(Long id) throws InterruptedException {
        final CountDownLatch latch = new CountDownLatch(1);

        authenticate();

        final Counter counter = new Counter();
        final UserWS localUserWS = client.createUserWS();
        localUserWS.setListener(new UserListener() {
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

        localUserWS.delete(null, id);
        latch.await(awaitTimeout, awaitTimeUnit);
        return counter.getCount() == 1;
    }

}