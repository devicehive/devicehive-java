/*
 *
 *
 *   CommandTest.java
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

import com.github.devicehive.rest.model.DeviceCommandWrapper;
import com.github.devicehive.websocket.api.CommandWS;
import com.github.devicehive.websocket.listener.CommandListener;
import com.github.devicehive.websocket.model.repsonse.CommandGetResponse;
import com.github.devicehive.websocket.model.repsonse.CommandInsertResponse;
import com.github.devicehive.websocket.model.repsonse.CommandListResponse;
import com.github.devicehive.websocket.model.repsonse.CommandSubscribeResponse;
import com.github.devicehive.websocket.model.repsonse.ErrorResponse;
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
public class CommandTest extends Helper {
    private static final String COMMAND = "c0mm4nd";
    private static final RESTHelper restHelper = new RESTHelper();
    private CountDownLatch latch;
    private CommandWS commandWS;
    private String deviceId;


    @Before
    public void preTest() throws InterruptedException, IOException {
        authenticate();
        latch = new CountDownLatch(1);
        deviceId = UUID.randomUUID().toString();
        registerDevice(deviceId);
        commandWS = client.createCommandWS();
    }

    @After
    public void clean() throws IOException {
        restHelper.deleteDevices(deviceId);
    }

    private DeviceCommandWrapper getWrapper(String notificationName) {
        return getWrapper(notificationName, null);
    }

    private DeviceCommandWrapper getWrapper(String notificationName, JsonObject params) {
        DeviceCommandWrapper wrapper = new DeviceCommandWrapper();
        wrapper.setCommand(notificationName);
        wrapper.setParameters(params);
        return wrapper;
    }


    @Test
    public void insertCommand() throws InterruptedException, IOException {
        commandWS.setListener(new CommandListener() {
            @Override
            public void onList(CommandListResponse response) {

            }

            @Override
            public void onGet(CommandGetResponse response) {

            }

            @Override
            public void onInsert(CommandInsertResponse response) {
                Assert.assertEquals(ResponseAction.SUCCESS, response.getStatus());
                latch.countDown();
            }

            @Override
            public void onUpdate(ResponseAction response) {

            }

            @Override
            public void onSubscribe(CommandSubscribeResponse response) {

            }

            @Override
            public void onUnsubscribe(ResponseAction response) {

            }

            @Override
            public void onError(ErrorResponse error) {

            }
        });
        commandWS.insert(deviceId, getWrapper(COMMAND));
        latch.await(awaitTimeout, awaitTimeUnit);
        Assert.assertEquals(0, latch.getCount());
    }

    @Test
    public void getCommand() throws InterruptedException, IOException {
        commandWS.setListener(new CommandListener() {
            @Override
            public void onList(CommandListResponse response) {

            }

            @Override
            public void onGet(CommandGetResponse response) {
                Assert.assertEquals(ResponseAction.SUCCESS, response.getStatus());
                String commandName = response.getCommand().getCommand();
                Assert.assertEquals(COMMAND, commandName);
                latch.countDown();
            }

            @Override
            public void onInsert(CommandInsertResponse response) {
                Assert.assertEquals(ResponseAction.SUCCESS, response.getStatus());
                Long commandId = response.getCommand().getId();
                commandWS.get(null, deviceId, commandId);
            }

            @Override
            public void onUpdate(ResponseAction response) {

            }

            @Override
            public void onSubscribe(CommandSubscribeResponse response) {

            }

            @Override
            public void onUnsubscribe(ResponseAction response) {

            }

            @Override
            public void onError(ErrorResponse error) {

            }
        });
        commandWS.insert(deviceId, getWrapper(COMMAND));
        latch.await(awaitTimeout, awaitTimeUnit);
        Assert.assertEquals(0, latch.getCount());
    }

    @Test
    public void subscribeCommand() throws InterruptedException, IOException {
        commandWS.setListener(new CommandListener() {
            @Override
            public void onList(CommandListResponse response) {

            }

            @Override
            public void onGet(CommandGetResponse response) {

            }

            @Override
            public void onInsert(CommandInsertResponse response) {
                latch.countDown();
            }

            @Override
            public void onUpdate(ResponseAction response) {

            }

            @Override
            public void onSubscribe(CommandSubscribeResponse response) {
                Assert.assertEquals(ResponseAction.SUCCESS, response.getStatus());
                commandWS.insert(null, deviceId, getWrapper(COMMAND));
            }

            @Override
            public void onUnsubscribe(ResponseAction response) {

            }

            @Override
            public void onError(ErrorResponse error) {

            }
        });
        commandWS.subscribe(Collections.singletonList(COMMAND), deviceId, null, null, null);
        latch.await(awaitTimeout, awaitTimeUnit);
        Assert.assertEquals(0, latch.getCount());
    }

    @Test
    public void unsubscribeCommand() throws InterruptedException, IOException {
        commandWS.setListener(new CommandListener() {
            @Override
            public void onList(CommandListResponse response) {

            }

            @Override
            public void onGet(CommandGetResponse response) {

            }

            @Override
            public void onInsert(CommandInsertResponse response) {

            }

            @Override
            public void onUpdate(ResponseAction response) {

            }

            @Override
            public void onSubscribe(CommandSubscribeResponse response) {
                Assert.assertEquals(ResponseAction.SUCCESS, response.getStatus());
                String subscribtionId = response.getSubscriptionId();
                commandWS.unsubscribe(subscribtionId, Collections.singletonList(deviceId));
            }

            @Override
            public void onUnsubscribe(ResponseAction response) {
                latch.countDown();
            }

            @Override
            public void onError(ErrorResponse error) {

            }
        });
        commandWS.subscribe(Collections.singletonList(COMMAND), deviceId, null, null, null);
        latch.await(awaitTimeout, awaitTimeUnit);
        Assert.assertEquals(0, latch.getCount());
    }

    @Test
    public void listCommand() throws InterruptedException, IOException {
        commandWS.setListener(new CommandListener() {
            @Override
            public void onList(CommandListResponse response) {
                Assert.assertEquals(ResponseAction.SUCCESS, response.getStatus());
                Assert.assertEquals(1, response.getCommands().size());
                latch.countDown();
            }

            @Override
            public void onGet(CommandGetResponse response) {

            }

            @Override
            public void onInsert(CommandInsertResponse response) {
                Assert.assertEquals(ResponseAction.SUCCESS, response.getStatus());
                commandWS.list(deviceId, null, null, COMMAND, null, null, 30, 0);
            }

            @Override
            public void onUpdate(ResponseAction response) {

            }

            @Override
            public void onSubscribe(CommandSubscribeResponse response) {

            }

            @Override
            public void onUnsubscribe(ResponseAction response) {

            }

            @Override
            public void onError(ErrorResponse error) {

            }
        });
        commandWS.insert(deviceId, getWrapper(COMMAND));
        latch.await(awaitTimeout, awaitTimeUnit);
        Assert.assertEquals(0, latch.getCount());
    }

}
