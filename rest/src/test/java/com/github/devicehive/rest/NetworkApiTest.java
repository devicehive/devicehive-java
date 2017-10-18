/*
 *
 *
 *   NetworkApiTest.java
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

package com.github.devicehive.rest;

import com.github.devicehive.rest.model.Network;
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

        com.github.devicehive.rest.api.NetworkApi networkApi = client.createService(com.github.devicehive.rest.api.NetworkApi.class);
        com.github.devicehive.rest.model.NetworkUpdate networkUpdate = new com.github.devicehive.rest.model.NetworkUpdate();
        networkUpdate.setName(NETWORK_NAME);

        Response<com.github.devicehive.rest.model.NetworkId> insertResponse = networkApi.insert(networkUpdate).execute();
        Assert.assertTrue(insertResponse.isSuccessful());
        Long networkId = insertResponse.body().getId();
        Assert.assertNotNull(networkId);

        Assert.assertTrue(deleteNetworks(networkId));
    }

    @Test
    public void getNetwork() throws IOException {
        boolean authenticated = authenticate();
        Assert.assertTrue(authenticated);

        com.github.devicehive.rest.api.NetworkApi networkApi = client.createService(com.github.devicehive.rest.api.NetworkApi.class);
        com.github.devicehive.rest.model.NetworkUpdate networkUpdate = new com.github.devicehive.rest.model.NetworkUpdate();
        networkUpdate.setName(NETWORK_NAME);
        Response<com.github.devicehive.rest.model.NetworkId> insertResponse = networkApi.insert(networkUpdate).execute();
        Assert.assertTrue(insertResponse.isSuccessful());
        Long networkId = insertResponse.body().getId();
        Assert.assertNotNull(networkId);

        Response<com.github.devicehive.rest.model.NetworkVO> getResponse = networkApi.get(networkId).execute();
        Assert.assertTrue(getResponse.isSuccessful());
        Assert.assertEquals(networkId, getResponse.body().getId());

        Assert.assertTrue(deleteNetworks(networkId));
    }

    @Test
    public void deleteNetwork() throws IOException {
        boolean authenticated = authenticate();
        Assert.assertTrue(authenticated);

        com.github.devicehive.rest.api.NetworkApi networkApi = client.createService(com.github.devicehive.rest.api.NetworkApi.class);
        com.github.devicehive.rest.model.NetworkUpdate networkUpdate = new com.github.devicehive.rest.model.NetworkUpdate();
        networkUpdate.setName(NETWORK_NAME);
        Response<com.github.devicehive.rest.model.NetworkId> insertResponse = networkApi.insert(networkUpdate).execute();
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

        com.github.devicehive.rest.api.NetworkApi networkApi = client.createService(com.github.devicehive.rest.api.NetworkApi.class);
        com.github.devicehive.rest.model.NetworkUpdate networkUpdate = new com.github.devicehive.rest.model.NetworkUpdate();
        networkUpdate.setName(NETWORK_NAME);

        Response<com.github.devicehive.rest.model.NetworkId> insertResponse = networkApi.insert(networkUpdate).execute();
        Assert.assertTrue(insertResponse.isSuccessful());
        Long networkId = insertResponse.body().getId();
        Assert.assertNotNull(networkId);

        com.github.devicehive.rest.model.NetworkUpdate networkUpdate1 = new com.github.devicehive.rest.model.NetworkUpdate();
        networkUpdate1.setName(UPDATED_NETWORK_NAME);

        Response<Void> updateResponse = networkApi.update(networkId, networkUpdate1).execute();
        Assert.assertTrue(updateResponse.isSuccessful());

        Response<com.github.devicehive.rest.model.NetworkVO> getResponse = networkApi.get(networkId).execute();
        Assert.assertTrue(getResponse.isSuccessful());
        Assert.assertNotNull(getResponse.body().getName());
        Assert.assertEquals(UPDATED_NETWORK_NAME, getResponse.body().getName());

        Assert.assertTrue(deleteNetworks(networkId));
    }

    @Test
    public void listNetwork() throws IOException {
        boolean authenticated = authenticate();
        Assert.assertTrue(authenticated);

        com.github.devicehive.rest.api.NetworkApi networkApi = client.createService(com.github.devicehive.rest.api.NetworkApi.class);

        int networkAmount = 5;
        Long[] networkIds = new Long[networkAmount];
        for (int j = 0; j < networkAmount; ++j) {
            com.github.devicehive.rest.model.NetworkUpdate networkUpdate = new com.github.devicehive.rest.model.NetworkUpdate();
            String name = String.format("%s %d", NETWORK_NAME, j);
            networkUpdate.setName(name);
            Response<com.github.devicehive.rest.model.NetworkId> insertResponse = networkApi.insert(networkUpdate).execute();
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
