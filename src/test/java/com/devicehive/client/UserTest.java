package com.devicehive.client;

import com.devicehive.client.model.Network;
import com.devicehive.client.model.TokenAuth;
import com.devicehive.client.service.User;
import com.devicehive.rest.model.JsonStringWrapper;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class UserTest {


    private static final String URL = "***REMOVED***/";
    private static final String WS_URL = "ws://playground.dev.devicehive.com/api/websocket";
    private static final String NOTIFICATION_A = "notificationA";
    private static final String NOTIFICATION_B = "notificationB";
    private static final String NOTIFICATION_Z = "notificationZ";
    private static final String COM_A = "comA";
    private static final String COM_B = "comB";
    private static final String COM_Z = "comZ";
    private String accessToken = "***REMOVED***";
    private String refreshToken = "***REMOVED***";

    private DeviceHive deviceHive = DeviceHive.getInstance().init(URL, WS_URL, new TokenAuth(refreshToken, accessToken));

    User currentUser = deviceHive.getCurrentUser().getData();

    @Test
    public void getUserNetworks() {
        List<Network> networks = currentUser.getNetworks();
        Assert.assertTrue(networks.size() > 0);
    }

    @Test
    public void test() {
        JsonStringWrapper data = currentUser.getData();
        JsonStringWrapper newData = new JsonStringWrapper();
        newData.setJsonString("BLABLA");
        currentUser.setData(newData);
        Assert.assertTrue(currentUser.save());
        System.out.println(deviceHive.getCurrentUser());
    }
}
