package com.devicehive.client.service;

import com.devicehive.client.callback.ResponseCallback;
import com.devicehive.client.model.DHResponse;
import com.devicehive.rest.api.ApiInfoApi;
import com.devicehive.rest.model.ApiInfo;
import com.devicehive.rest.model.ClusterConfig;

public class ApiInfoService extends BaseService {
    private ApiInfoApi apiInfo;


    public DHResponse<ApiInfo> getApiInfo() {
        apiInfo = createService(ApiInfoApi.class);
        return execute(apiInfo.getApiInfo());
    }

    public void getApiInfo(ResponseCallback<ApiInfo> callback) {
        apiInfo = createService(ApiInfoApi.class);
        execute(apiInfo.getApiInfo(), callback);
    }

    public DHResponse<ClusterConfig> getClusterConfig() {
        apiInfo = createService(ApiInfoApi.class);
        return execute(apiInfo.getClusterConfig());
    }

    public void getClusterConfig(ResponseCallback<ClusterConfig> callback) {
        apiInfo = createService(ApiInfoApi.class);
        execute(apiInfo.getClusterConfig(), callback);
    }
}
