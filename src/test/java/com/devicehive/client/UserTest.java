package com.devicehive.client;

import com.devicehive.client.model.DHResponse;
import com.devicehive.client.service.Network;
import com.devicehive.client.model.NetworkFilter;
import com.devicehive.client.model.TokenAuth;
import com.devicehive.client.service.DeviceHive;
import com.devicehive.client.service.User;
import com.devicehive.rest.model.JsonStringWrapper;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class UserTest {


    private static final String URL = "http://playground.dev.devicehive.com/api/rest/";
    private static final String WS_URL = "ws://playground.dev.devicehive.com/api/websocket";
    private static final String NOTIFICATION_A = "notificationA";
    private static final String NOTIFICATION_B = "notificationB";
    private static final String NOTIFICATION_Z = "notificationZ";
    private static final String COM_A = "comA";
    private static final String COM_B = "comB";
    private static final String COM_Z = "comZ";
    private String accessToken = "eyJhbGciOiJIUzI1NiJ9.eyJwYXlsb2FkIjp7InVzZXJJZCI6MSwiYWN0aW9ucyI6WyIqIl0sIm5ldHdvcmtJZHMiOlsiKiJdLCJkZXZpY2VJZHMiOlsiKiJdLCJleHBpcmF0aW9uIjoxNTM2OTI1MTA2NDM1LCJ0b2tlblR5cGUiOiJBQ0NFU1MifX0.DVRKVgrtnv35MWwxR1T8bLm83-RJCfloYuoEjvYPQ4s";
    private String refreshToken = "eyJhbGciOiJIUzI1NiJ9.eyJwYXlsb2FkIjp7InVzZXJJZCI6MSwiYWN0aW9ucyI6WyIqIl0sIm5ldHdvcmtJZHMiOlsiKiJdLCJkZXZpY2VJZHMiOlsiKiJdLCJleHBpcmF0aW9uIjoxNTM2OTI1MTA2NDM1LCJ0b2tlblR5cGUiOiJSRUZSRVNIIn19.7alYTD5kb_imglE7NyRhjQBFqXhqpfJJs-ZA68yJZiQ";

    private DeviceHive deviceHive = DeviceHive.getInstance().init(URL, WS_URL, new TokenAuth(refreshToken, accessToken));

    User currentUser = deviceHive.getCurrentUser().getData();

    @Test
    public void getUserNetworks() {
        List<Network> networks = currentUser.getNetworks();
        Assert.assertTrue(networks.size() > 0);
    }

    @Test
    public void updateUser() {
        JsonStringWrapper data = currentUser.getData();
        JsonStringWrapper newData = new JsonStringWrapper();
        newData.setJsonString("BLABLA");
        currentUser.setData(newData);
        Assert.assertTrue(currentUser.save());
    }

    @Test
    public void assignNetwork() {
        DHResponse<List<Network>> listDHResponse = deviceHive.listNetworks(new NetworkFilter());
        Assert.assertTrue(listDHResponse.isSuccessful());
        Assert.assertTrue(currentUser.assignNetwork(listDHResponse.getData().get(1).getId()));
        Assert.assertTrue(currentUser.unassignNetwork(listDHResponse.getData().get(1).getId()));
    }
}
