package com.devicehive.client.service;

import com.devicehive.client.RestHelper;
import com.devicehive.client.callback.ResponseCallback;
import com.devicehive.client.model.DHResponse;
import com.devicehive.rest.api.ApiInfoApi;
import com.devicehive.rest.model.ApiInfo;
import com.devicehive.rest.model.ClusterConfig;

public class ApiInfoService extends BaseService {
    private ApiInfoApi apiInfo;

    public ApiInfoService() {
        super();
        apiClient = RestHelper.getInstance().getApiClient();
        apiInfo = apiClient.createService(ApiInfoApi.class);
    }

    public DHResponse<ApiInfo> getApiInfo() {
        return execute(apiInfo.getApiInfo());
    }

    public void getApiInfo(ResponseCallback<ApiInfo> callback) {
        execute(apiInfo.getApiInfo(), callback);
    }

    public DHResponse<ClusterConfig> getClusterConfig() {
        return execute(apiInfo.getClusterConfig());
    }

    public void getClusterConfig(ResponseCallback<ClusterConfig> callback) {
        execute(apiInfo.getClusterConfig(), callback);
    }
}
