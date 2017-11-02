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
import com.github.devicehive.websocket.api.DeviceWS;
import com.github.devicehive.websocket.listener.DeviceListener;
import com.github.devicehive.websocket.model.repsonse.ErrorResponse;
import com.github.devicehive.websocket.model.repsonse.ResponseAction;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static com.github.devicehive.websocket.model.repsonse.ResponseAction.SUCCESS;

public class DeviceWSTest extends Helper {

    @Test
    public void registerDevice() throws IOException, InterruptedException {
        final String deviceId = UUID.randomUUID().toString();
        final CountDownLatch latch = new CountDownLatch(2);
        final DeviceWS deviceWS = client.createDeviceWS();
        deviceWS.setListener(new DeviceListener() {
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
                deviceWS.delete(null, deviceId);
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

    @Test
    public void deleteDevice() throws IOException, InterruptedException {
        final String deviceId = UUID.randomUUID().toString();
        final CountDownLatch latch = new CountDownLatch(2);
        final DeviceWS deviceWS = client.createDeviceWS();
        deviceWS.setListener(new DeviceListener() {
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
                deviceWS.delete(null, deviceId);
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


    @Test
    public void getDeviceList() throws IOException, InterruptedException {
        final String deviceId = UUID.randomUUID().toString();
        final CountDownLatch latch = new CountDownLatch(3);
        final DeviceWS deviceWS = client.createDeviceWS();
        deviceWS.setListener(new DeviceListener() {
            @Override
            public void onList(List<Device> response) {
                latch.countDown();
                deviceWS.delete(null, deviceId);
                Assert.assertTrue(response.size() > 0);
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
                deviceWS.list(null, null, null,
                        null, null, null, null, 30, 0);
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

    @Test
    public void getDevice() throws IOException, InterruptedException {
        final String deviceId = UUID.randomUUID().toString();
        final CountDownLatch latch = new CountDownLatch(3);
        final DeviceWS deviceWS = client.createDeviceWS();
        deviceWS.setListener(new DeviceListener() {
            @Override
            public void onList(List<Device> response) {
            }

            @Override
            public void onGet(Device response) {
                latch.countDown();
                deviceWS.delete(null, deviceId);
                Assert.assertEquals(response.getId(), deviceId);
            }

            @Override
            public void onDelete(ResponseAction response) {
                latch.countDown();
                Assert.assertEquals(response.getStatus(), SUCCESS);
            }

            @Override
            public void onSave(ResponseAction response) {
                latch.countDown();
                deviceWS.get(null, deviceId);
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
}
