/*
 *
 *
 *   ApiInfoService.java
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

package com.github.devicehive.client.service;

import com.github.devicehive.client.callback.ResponseCallback;
import com.github.devicehive.client.model.DHResponse;
import com.github.devicehive.rest.api.ApiInfoApi;
import com.github.devicehive.rest.model.ApiInfo;
import com.github.devicehive.rest.model.ClusterConfig;

public class ApiInfoService extends BaseService {
    private ApiInfoApi apiInfo;

    public DHResponse<ApiInfo> getApiInfo() {
        apiInfo = createService(ApiInfoApi.class,true);
        return execute(apiInfo.getApiInfo());
    }

    public void getApiInfo(ResponseCallback<ApiInfo> callback) {
        apiInfo = createService(ApiInfoApi.class,true);
        execute(apiInfo.getApiInfo(), callback);
    }

    public DHResponse<ClusterConfig> getClusterConfig() {
        apiInfo = createService(ApiInfoApi.class,true);
        return execute(apiInfo.getClusterConfig());
    }

    public void getClusterConfig(ResponseCallback<ClusterConfig> callback) {
        apiInfo = createService(ApiInfoApi.class,true);
        execute(apiInfo.getClusterConfig(), callback);
    }
}
