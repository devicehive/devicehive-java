/*
 *
 *
 *   DeviceTest.java
 *
 *   Copyright (C) 2017 DataArt
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

package com.github.devicehive.websocket;

import com.github.devicehive.rest.model.Device;
import com.github.devicehive.rest.model.DeviceUpdate;
import com.github.devicehive.websocket.api.DeviceWS;
import com.github.devicehive.websocket.api.TokenWS;
import com.github.devicehive.websocket.api.WebSocketClient;
import com.github.devicehive.websocket.listener.DeviceListener;
import com.github.devicehive.websocket.listener.TokenListener;
import com.github.devicehive.websocket.model.repsonse.ErrorResponse;
import com.github.devicehive.websocket.model.repsonse.ResponseAction;
import com.github.devicehive.websocket.model.repsonse.TokenGetResponse;
import com.github.devicehive.websocket.model.repsonse.TokenRefreshResponse;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static com.github.devicehive.websocket.model.repsonse.ResponseAction.SUCCESS;

public class DeviceWSTest {
    private static final String URL = "ws://playground.dev.devicehive.com/api/websocket";
    private static final String LOGIN = "dhadmin";
    private static final String PASSWORD = "dhadmin_#911";
    private String refreshToken = "eyJhbGciOiJIUzI1NiJ9.eyJwYXlsb2FkIjp7InUiOjEsImEiOlswXSwibiI6WyIqIl0sImQiOlsiKiJdLCJlIjoxNTI0MTUxMjAxMTA4LCJ0IjowfX0.2wfpmIjrHRtGBoSF3-T77aSAiUYPFSGtgBuGoVZtSxc";

    private WebSocketClient client = new WebSocketClient
            .Builder()
            .url(URL)
            .build();
    private DeviceWS deviceWS;


    private void authenticate() throws InterruptedException {
        final CountDownLatch latch = new CountDownLatch(1);

        TokenWS tokenWS = client.createTokenWS(new TokenListener() {
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
        tokenWS.refresh(null, refreshToken);
        latch.await(2, TimeUnit.SECONDS);
    }

    @Test
    public void registerDevice() throws IOException, InterruptedException {
        final String deviceId = UUID.randomUUID().toString();
        final CountDownLatch latch = new CountDownLatch(2);
        deviceWS = client.createDeviceWS(new DeviceListener() {
            @Override
            public void onList(List<Device> response) {

            }

            @Override
            public void onGet(Device response) {
            }

            @Override
            public void onDelete(ResponseAction response) {
                latch.countDown();
                Assert.assertEquals(response.getStatus(), SUCCESS);
            }

            @Override
            public void onSave(ResponseAction response) {
                latch.countDown();
                deviceWS.delete(null,deviceId);
                Assert.assertEquals(response.getStatus(), SUCCESS);
            }

            @Override
            public void onError(ErrorResponse error) {
                if (error.getCode() == 401) {
                    try {
                        authenticate();
                        Assert.assertNotNull(deviceWS);
                        registerDevice(deviceId, deviceWS);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        Assert.assertTrue(false);
                    }
                    return;
                }
                Assert.assertTrue(false);
            }
        });
        registerDevice(deviceId, deviceWS);
        latch.await(10, TimeUnit.SECONDS);
        Assert.assertEquals(latch.getCount(), 0);
    }

    private void registerDevice(String deviceId, DeviceWS deviceWS) {
        DeviceUpdate deviceUpdate = new DeviceUpdate();
        deviceUpdate.setName(deviceId);
        deviceWS.save(null, deviceId, deviceUpdate);
    }

    @Test
    public void deleteDevice() throws IOException, InterruptedException {
        String deviceId = UUID.randomUUID().toString();
        authenticate();


//        DeviceApi api = client.createService(DeviceApi.class);
//        Response<Void> response = api.delete(deviceId).execute();
//        Assert.assertTrue(response.isSuccessful());
    }


    @Test
    public void getDeviceList() throws IOException {
//        String deviceId = UUID.randomUUID().toString();
//        boolean authenticated = authenticate();
//        boolean deviceCreated = createDevice(deviceId);
//        Assert.assertTrue(authenticated && deviceCreated);
//
//        DeviceApi api = client.createService(DeviceApi.class);
//        Response<List<Device>> response = api.list(null, null, null, null,
//                null, null, 0, null).execute();
//        Assert.assertTrue(response.isSuccessful());
    }

    @Test
    public void getDevice() throws IOException {
//        String deviceId = UUID.randomUUID().toString();
//        boolean authenticated = authenticate();
//        boolean deviceCreated = createDevice(deviceId);
//        Assert.assertTrue(authenticated && deviceCreated);
//
//        DeviceApi api = client.createService(DeviceApi.class);
//        Response<Device> response = api.get(deviceId).execute();
//        Assert.assertTrue(response.isSuccessful());
//        Assert.assertTrue(deleteDevices(deviceId));
    }
}
