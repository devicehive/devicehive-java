package com.devicehive.rest;

import com.devicehive.rest.api.NetworkApi;
import com.devicehive.rest.model.Network;
import com.devicehive.rest.model.NetworkId;
import com.devicehive.rest.model.NetworkUpdate;
import com.devicehive.rest.model.NetworkVO;
import org.junit.Assert;
import org.junit.Test;
import retrofit2.Response;

import java.io.IOException;
import java.util.List;

public class NetworkApiTest extends Helper {
    private static final String NETWORK_NAME = "TEST NETWORK";
    private static final String UPDATED_NETWORK_NAME = "UPDATED TEST NETWORK";

    @Test
    public void insertNetwork() throws IOException {
        boolean authenticated = authenticate();
        Assert.assertTrue(authenticated);

        NetworkApi networkApi = client.createService(NetworkApi.class);
        NetworkUpdate networkUpdate = new NetworkUpdate();
        networkUpdate.setName(NETWORK_NAME);

        Response<NetworkId> insertResponse = networkApi.insert(networkUpdate).execute();
        Assert.assertTrue(insertResponse.isSuccessful());
        Long networkId = insertResponse.body().getId();
        Assert.assertNotNull(networkId);

        Assert.assertTrue(deleteNetworks(networkId));
    }

    @Test
    public void getNetwork() throws IOException {
        boolean authenticated = authenticate();
        Assert.assertTrue(authenticated);

        NetworkApi networkApi = client.createService(NetworkApi.class);
        NetworkUpdate networkUpdate = new NetworkUpdate();
        networkUpdate.setName(NETWORK_NAME);
        Response<NetworkId> insertResponse = networkApi.insert(networkUpdate).execute();
        Assert.assertTrue(insertResponse.isSuccessful());
        Long networkId = insertResponse.body().getId();
        Assert.assertNotNull(networkId);

        Response<NetworkVO> getResponse = networkApi.get(networkId).execute();
        Assert.assertTrue(getResponse.isSuccessful());
        Assert.assertEquals(networkId, getResponse.body().getId());

        Assert.assertTrue(deleteNetworks(networkId));
    }

    @Test
    public void deleteNetwork() throws IOException {
        boolean authenticated = authenticate();
        Assert.assertTrue(authenticated);

        NetworkApi networkApi = client.createService(NetworkApi.class);
        NetworkUpdate networkUpdate = new NetworkUpdate();
        networkUpdate.setName(NETWORK_NAME);
        Response<NetworkId> insertResponse = networkApi.insert(networkUpdate).execute();
        Assert.assertTrue(insertResponse.isSuccessful());
        Long networkId = insertResponse.body().getId();
        Assert.assertNotNull(networkId);

        Response<Void> deleteResponse = networkApi.delete(networkId).execute();
        Assert.assertTrue(deleteResponse.isSuccessful());
    }

    @Test
    public void updateNetwork() throws IOException {
        boolean authenticated = authenticate();
        Assert.assertTrue(authenticated);

        NetworkApi networkApi = client.createService(NetworkApi.class);
        NetworkUpdate networkUpdate = new NetworkUpdate();
        networkUpdate.setName(NETWORK_NAME);

        Response<NetworkId> insertResponse = networkApi.insert(networkUpdate).execute();
        Assert.assertTrue(insertResponse.isSuccessful());
        Long networkId = insertResponse.body().getId();
        Assert.assertNotNull(networkId);

        NetworkUpdate networkUpdate1 = new NetworkUpdate();
        networkUpdate1.setName(UPDATED_NETWORK_NAME);

        Response<Void> updateResponse = networkApi.update(networkId, networkUpdate1).execute();
        Assert.assertTrue(updateResponse.isSuccessful());

        Response<NetworkVO> getResponse = networkApi.get(networkId).execute();
        Assert.assertTrue(getResponse.isSuccessful());
        Assert.assertNotNull(getResponse.body().getName());
        Assert.assertEquals(UPDATED_NETWORK_NAME, getResponse.body().getName());

        Assert.assertTrue(deleteNetworks(networkId));
    }

    @Test
    public void listNetwork() throws IOException {
        boolean authenticated = authenticate();
        Assert.assertTrue(authenticated);

        NetworkApi networkApi = client.createService(NetworkApi.class);

        int networkAmount = 5;
        Long[] networkIds = new Long[networkAmount];
        for (int j = 0; j < networkAmount; ++j) {
            NetworkUpdate networkUpdate = new NetworkUpdate();
            String name = String.format("%s %d", NETWORK_NAME, j);
            networkUpdate.setName(name);
            Response<NetworkId> insertResponse = networkApi.insert(networkUpdate).execute();
            Assert.assertTrue(insertResponse.isSuccessful());
            Long networkId = insertResponse.body().getId();
            Assert.assertNotNull(networkId);
            networkIds[j] = networkId;
        }

        Response<List<Network>> listResponse = networkApi.list(null, NETWORK_NAME + "%",
                null, null, 2 * networkAmount, 0).execute();
        Assert.assertTrue(listResponse.isSuccessful());
        Assert.assertEquals(networkAmount, listResponse.body().size());

        Assert.assertTrue(deleteNetworks(networkIds));
    }

}
