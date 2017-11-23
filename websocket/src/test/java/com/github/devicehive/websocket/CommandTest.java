package com.github.devicehive.websocket;

import com.github.devicehive.rest.model.JsonStringWrapper;
import com.github.devicehive.websocket.api.CommandWS;
import com.github.devicehive.websocket.listener.CommandListener;
import com.github.devicehive.websocket.model.repsonse.*;
import com.github.devicehive.rest.model.DeviceCommandWrapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Collections;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;

public class CommandTest extends Helper {
    private static final String COMMAND = "c0mm4nd";
    private static final RESTHelper restHelper = new RESTHelper();

    private DeviceCommandWrapper getWrapper(String notificationName) {
        return getWrapper(notificationName, null);
    }

    private DeviceCommandWrapper getWrapper(String notificationName, JsonStringWrapper params) {
        DeviceCommandWrapper wrapper = new DeviceCommandWrapper();
        wrapper.setCommand(notificationName);
        wrapper.setParameters(params);
        return wrapper;
    }

    @Before
    public void preTest() throws InterruptedException, IOException {
        authenticate();
    }

    @Test
    public void insertCommand() throws InterruptedException, IOException {
        String deviceId = UUID.randomUUID().toString();
        Assert.assertTrue(registerDevice(deviceId));

        final CountDownLatch latch = new CountDownLatch(1);

        CommandWS commandWS = client.createCommandWS();
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

        restHelper.deleteDevices(deviceId);
        Assert.assertEquals(0, latch.getCount());
    }

    @Test
    public void getCommand() throws InterruptedException, IOException {
        final String deviceId = UUID.randomUUID().toString();
        Assert.assertTrue(registerDevice(deviceId));

        final CountDownLatch latch = new CountDownLatch(1);

        final CommandWS commandWS = client.createCommandWS();
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

        restHelper.deleteDevices(deviceId);
        Assert.assertEquals(0, latch.getCount());
    }

    @Test
    public void subscribeCommand() throws InterruptedException, IOException {
        final String deviceId = UUID.randomUUID().toString();
        Assert.assertTrue(registerDevice(deviceId));

        final CountDownLatch latch = new CountDownLatch(1);

        final CommandWS commandWS = client.createCommandWS();
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

        restHelper.deleteDevices(deviceId);
        Assert.assertEquals(0, latch.getCount());
    }

    @Test
    public void unsubscribeCommand() throws InterruptedException, IOException {
        final String deviceId = UUID.randomUUID().toString();
        Assert.assertTrue(registerDevice(deviceId));

        final CountDownLatch latch = new CountDownLatch(1);

        final CommandWS commandWS = client.createCommandWS();
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

        restHelper.deleteDevices(deviceId);
        Assert.assertEquals(0, latch.getCount());
    }

    @Test
    public void listCommand() throws InterruptedException, IOException {
        final String deviceId = UUID.randomUUID().toString();
        Assert.assertTrue(registerDevice(deviceId));

        final CountDownLatch latch = new CountDownLatch(1);

        final CommandWS commandWS = client.createCommandWS();
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

        restHelper.deleteDevices(deviceId);
        Assert.assertEquals(0, latch.getCount());
    }

}
