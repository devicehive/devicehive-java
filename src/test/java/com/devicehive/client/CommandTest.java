package com.devicehive.client;

import com.devicehive.client.model.DeviceCommand;
import com.devicehive.client.model.DeviceCommandCallback;
import com.devicehive.client.model.FailureData;
import com.devicehive.client.model.TokenAuth;
import com.devicehive.client.service.Device;
import com.devicehive.rest.model.JsonStringWrapper;
import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class CommandTest {
    private static final String DEVICE_ID = "271990123";

    private static final String URL = "***REMOVED***/";
    private static final String WS_URL = "ws://playground.dev.devicehive.com/api/websocket";
    private static final String COM_A = "comA";
    private String accessToken = "***REMOVED***";
    private String refreshToken = "***REMOVED***";
    private DeviceHive deviceHive = DeviceHive.getInstance().init(URL, WS_URL, new TokenAuth(refreshToken, accessToken));

    private Device device = deviceHive.getDevice(DEVICE_ID);

    @Test
    public void createAndUpdate() throws InterruptedException {
        final CountDownLatch latch = new CountDownLatch(1);
        device.sendCommand(COM_A, null, new DeviceCommandCallback() {
            @Override
            public void onSuccess(DeviceCommand command) {
                command.setResult(new JsonStringWrapper("SUCCESS"));
                command.updateCommand();
                Assert.assertTrue(command.fetchCommandResult().getData() != null);
                latch.countDown();
            }

            @Override
            public void onFail(FailureData failureData) {

            }
        });
        latch.await(30, TimeUnit.SECONDS);
    }
}
