package com.devicehive.rest;

import com.devicehive.rest.api.ApiInfoApi;
import com.devicehive.rest.model.ApiInfo;
import com.devicehive.rest.model.ClusterConfig;
import org.junit.Assert;
import org.junit.Test;
import retrofit2.Response;

import java.io.IOException;

public class ApiInfoApiTest extends Helper {
    @Test
    public void getApiInfoTest() throws IOException {
        ApiInfoApi apiInfoApi = client.createService(ApiInfoApi.class);
        Response<ApiInfo> response = apiInfoApi.getApiInfo().execute();
        Assert.assertTrue(response.isSuccessful());
    }

    @Test
    public void getClusterConfigTest() throws IOException {
        ApiInfoApi apiInfoApi = client.createService(ApiInfoApi.class);
        Response<ClusterConfig> response = apiInfoApi.getClusterConfig().execute();
        Assert.assertTrue(response.isSuccessful());
    }
}
