/*
 *
 *
 *   NotificationTest.java
 *
 *   Copyright (C) 2018 DataArt
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
import com.github.devicehive.websocket.api.NotificationWS;
import com.github.devicehive.websocket.listener.NotificationListener;
import com.github.devicehive.websocket.model.repsonse.ErrorResponse;
import com.github.devicehive.websocket.model.repsonse.NotificationGetResponse;
import com.github.devicehive.websocket.model.repsonse.NotificationInsertResponse;
import com.github.devicehive.websocket.model.repsonse.NotificationListResponse;
import com.github.devicehive.websocket.model.repsonse.NotificationSubscribeResponse;
import com.github.devicehive.websocket.model.repsonse.ResponseAction;
import com.google.gson.JsonObject;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.io.IOException;
import java.util.Collections;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class NotificationTest extends Helper {
    private static final String NOTIFICATION_1 = "notification1";
    private NotificationWS notificationWS;
    private CountDownLatch latch;
    private String deviceId;

    @Before
    public void preTest() throws InterruptedException, IOException {
        authenticate();
        latch = new CountDownLatch(1);
        deviceId = UUID.randomUUID().toString();
        registerDevice(deviceId);
        notificationWS = client.createNotificationWS();
    }

    @After
    public void clear() throws InterruptedException, IOException {
        deleteDevice(deviceId);
    }

    @Test
    public void insert() throws InterruptedException {
        latch = new CountDownLatch(1);
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
        notificationWS.insert(deviceId, getWrapper(NOTIFICATION_1));
        latch.await(awaitTimeout, awaitTimeUnit);
        Assert.assertTrue(latch.getCount() == 0);
    }

    @Test
    public void get() throws InterruptedException {
        latch = new CountDownLatch(2);
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
        notificationWS.insert(deviceId, getWrapper(NOTIFICATION_1));
        latch.await(awaitTimeout, awaitTimeUnit);
        Assert.assertTrue(latch.getCount() == 0);

    }

    @Test
    public void list() throws InterruptedException {
        latch = new CountDownLatch(2);
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
        notificationWS.insert(deviceId, getWrapper(NOTIFICATION_1));

        latch.await(awaitTimeout, awaitTimeUnit);
        Assert.assertTrue(latch.getCount() == 0);
    }


    @Test
    public void subscribe() throws InterruptedException {
        latch = new CountDownLatch(2);
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
        notificationWS.subscribe(deviceId, null, Collections.singletonList(NOTIFICATION_1));
        latch.await(awaitTimeout, awaitTimeUnit);
        Assert.assertTrue(latch.getCount() == 0);

    }

    @Test
    public void unsubscribe() throws InterruptedException {
        latch = new CountDownLatch(2);
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
        notificationWS.subscribe(deviceId, null, Collections.singletonList(NOTIFICATION_1));
        latch.await(awaitTimeout, awaitTimeUnit);
        Assert.assertTrue(latch.getCount() == 0);

    }

    private DeviceNotificationWrapper getWrapper(String notificationName) {
        return getWrapper(notificationName, null);
    }

    private DeviceNotificationWrapper getWrapper(String notificationName, JsonObject params) {
        DeviceNotificationWrapper wrapper = new DeviceNotificationWrapper();
        wrapper.setNotification(notificationName);
        wrapper.setParameters(params);
        return wrapper;
    }
}
