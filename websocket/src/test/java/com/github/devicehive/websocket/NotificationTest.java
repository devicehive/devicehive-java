/*
 *
 *
 *   NotificationTest.java
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

import com.github.devicehive.rest.model.DeviceNotificationWrapper;
import com.github.devicehive.rest.model.JsonStringWrapper;
import com.github.devicehive.websocket.api.NotificationWS;
import com.github.devicehive.websocket.listener.NotificationListener;
import com.github.devicehive.websocket.model.repsonse.*;
import org.junit.Assert;
import org.junit.Test;

import java.util.Collections;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;

public class NotificationTest extends Helper {
    public static final String NOTIFICATION_1 = "notification1";

    @Test
    public void list() throws InterruptedException {
        final String deviceId = UUID.randomUUID().toString();
        authenticate();
        Assert.assertTrue(registerDevice(deviceId));
        final CountDownLatch latch = new CountDownLatch(2);
        final NotificationWS notificationWS = client.createNotificationWS();
        notificationWS.insert(deviceId, getWrapper(NOTIFICATION_1));
        notificationWS.setListener(new NotificationListener() {
            @Override
            public void onList(NotificationListResponse response) {
                Assert.assertTrue(true);
                latch.countDown();
            }

            @Override
            public void onGet(NotificationGetResponse response) {

            }

            @Override
            public void onInsert(NotificationInsertResponse response) {
                Assert.assertTrue(true);
                notificationWS.list(deviceId, NOTIFICATION_1, null, null, null, null, 30, 0);
                latch.countDown();
            }

            @Override
            public void onSubscribe(NotificationSubscribeResponse response) {

            }

            @Override
            public void onUnsubscribe(ResponseAction response) {

            }

            @Override
            public void onError(ErrorResponse error) {

            }
        });
        latch.await(awaitTimeout, awaitTimeUnit);
        deleteDevice(deviceId);
        Assert.assertTrue(latch.getCount() == 0);

    }

    @Test
    public void get() throws InterruptedException {
        final String deviceId = UUID.randomUUID().toString();
        authenticate();
        Assert.assertTrue(registerDevice(deviceId));
        final CountDownLatch latch = new CountDownLatch(2);
        final NotificationWS notificationWS = client.createNotificationWS();
        notificationWS.insert(deviceId, getWrapper(NOTIFICATION_1));
        notificationWS.setListener(new NotificationListener() {
            @Override
            public void onList(NotificationListResponse response) {

            }

            @Override
            public void onGet(NotificationGetResponse response) {
                Assert.assertTrue(true);
                latch.countDown();
            }

            @Override
            public void onInsert(NotificationInsertResponse response) {
                Assert.assertTrue(true);
                notificationWS.get(deviceId, response.getNotification().getId());
                latch.countDown();
            }

            @Override
            public void onSubscribe(NotificationSubscribeResponse response) {

            }

            @Override
            public void onUnsubscribe(ResponseAction response) {

            }

            @Override
            public void onError(ErrorResponse error) {

            }
        });
        latch.await(awaitTimeout, awaitTimeUnit);
        deleteDevice(deviceId);
        Assert.assertTrue(latch.getCount() == 0);

    }

    @Test
    public void insert() throws InterruptedException {
        String deviceId = UUID.randomUUID().toString();
        authenticate();
        Assert.assertTrue(registerDevice(deviceId));
        final CountDownLatch latch = new CountDownLatch(1);
        NotificationWS notificationWS = client.createNotificationWS();
        notificationWS.insert(deviceId, getWrapper(NOTIFICATION_1));
        notificationWS.setListener(new NotificationListener() {
            @Override
            public void onList(NotificationListResponse response) {

            }

            @Override
            public void onGet(NotificationGetResponse response) {

            }

            @Override
            public void onInsert(NotificationInsertResponse response) {
                Assert.assertTrue(true);
                latch.countDown();
            }

            @Override
            public void onSubscribe(NotificationSubscribeResponse response) {

            }

            @Override
            public void onUnsubscribe(ResponseAction response) {

            }

            @Override
            public void onError(ErrorResponse error) {

            }
        });
        latch.await(awaitTimeout, awaitTimeUnit);
        deleteDevice(deviceId);
        Assert.assertTrue(latch.getCount() == 0);
    }

    private DeviceNotificationWrapper getWrapper(String notificationName) {
        return getWrapper(notificationName, null);
    }

    private DeviceNotificationWrapper getWrapper(String notificationName, JsonStringWrapper params) {
        DeviceNotificationWrapper wrapper = new DeviceNotificationWrapper();
        wrapper.setNotification(notificationName);
        wrapper.setParameters(params);
        return wrapper;
    }

    @Test
    public void subscribe() throws InterruptedException {
        final String deviceId = UUID.randomUUID().toString();
        authenticate();
        Assert.assertTrue(registerDevice(deviceId));
        final CountDownLatch latch = new CountDownLatch(2);
        final NotificationWS notificationWS = client.createNotificationWS();
        notificationWS.subscribe(deviceId, null, Collections.singletonList(NOTIFICATION_1));
        notificationWS.setListener(new NotificationListener() {
            @Override
            public void onList(NotificationListResponse response) {

            }

            @Override
            public void onGet(NotificationGetResponse response) {
            }

            @Override
            public void onInsert(NotificationInsertResponse response) {
                Assert.assertTrue(true);
                latch.countDown();
            }

            @Override
            public void onSubscribe(NotificationSubscribeResponse response) {
                Assert.assertTrue(true);
                notificationWS.insert(deviceId, getWrapper(NOTIFICATION_1));
                latch.countDown();
            }

            @Override
            public void onUnsubscribe(ResponseAction response) {

            }

            @Override
            public void onError(ErrorResponse error) {

            }
        });
        latch.await(awaitTimeout, awaitTimeUnit);
        deleteDevice(deviceId);
        Assert.assertTrue(latch.getCount() == 0);

    }

    @Test
    public void unsubscribe() throws InterruptedException {
        final String deviceId = UUID.randomUUID().toString();
        authenticate();
        Assert.assertTrue(registerDevice(deviceId));
        final CountDownLatch latch = new CountDownLatch(2);
        final NotificationWS notificationWS = client.createNotificationWS();
        notificationWS.subscribe(deviceId, null, Collections.singletonList(NOTIFICATION_1));
        notificationWS.setListener(new NotificationListener() {
            @Override
            public void onList(NotificationListResponse response) {

            }

            @Override
            public void onGet(NotificationGetResponse response) {
            }

            @Override
            public void onInsert(NotificationInsertResponse response) {

            }

            @Override
            public void onSubscribe(NotificationSubscribeResponse response) {
                Assert.assertTrue(true);
                notificationWS.unsubscribe(response.getSubscriptionId(), Collections.singletonList(deviceId));
                latch.countDown();
            }

            @Override
            public void onUnsubscribe(ResponseAction response) {
                Assert.assertTrue(true);
                latch.countDown();
            }

            @Override
            public void onError(ErrorResponse error) {

            }
        });
        latch.await(awaitTimeout, awaitTimeUnit);
        deleteDevice(deviceId);
        Assert.assertTrue(latch.getCount() == 0);

    }

    private void createDevice() throws InterruptedException {
        final String deviceId = UUID.randomUUID().toString();
        final CountDownLatch latch = new CountDownLatch(2);
        registerDevice(deviceId);
    }
}
