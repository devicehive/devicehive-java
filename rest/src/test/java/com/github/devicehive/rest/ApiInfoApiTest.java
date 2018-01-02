/*
 *
 *
 *   ApiInfoApiTest.java
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

package com.github.devicehive.rest;

import com.github.devicehive.rest.api.ApiInfoApi;
import com.github.devicehive.rest.model.ApiInfo;
import com.github.devicehive.rest.model.ClusterConfig;
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
