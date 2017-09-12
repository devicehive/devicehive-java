package com.devicehive.client.service;

import com.devicehive.client.RestHelper;
import com.devicehive.client.callback.ApiInfoCallback;
import com.devicehive.client.model.DHResponse;
import com.devicehive.client.model.FailureData;
import com.devicehive.rest.ApiClient;
import com.devicehive.rest.api.ApiInfoApi;
import com.devicehive.rest.model.ApiInfo;
import com.devicehive.rest.model.ClusterConfig;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.IOException;

public class ApiInfoService extends BaseService {
    private ApiInfoApi apiInfo;
    private ApiClient apiClient;

    public ApiInfoService() {
        super();
        apiClient = RestHelper.getInstance().getApiClient();
        apiInfo = apiClient.createService(ApiInfoApi.class);
    }

    public DHResponse<ApiInfo> getApiInfo() {
        Call<ApiInfo> apiInfoApi = apiClient.createService(ApiInfoApi.class).getApiInfo();

        try {
            Response<ApiInfo> response = apiInfoApi.execute();
            if (response.isSuccessful()) {
                return new DHResponse<ApiInfo>(response.body(), null);
            } else {
                FailureData failureData = createFailData(response.code(), response.message());
                return new DHResponse<ApiInfo>(null, failureData);
            }
        } catch (IOException e) {
            FailureData failureData = createFailData(FailureData.NO_CODE, e.getMessage());
            return new DHResponse<ApiInfo>(null, failureData);
        }
    }

    public void getApiInfo(ApiInfoCallback callback) {
        final ApiInfoCallback apiInfoCallback = callback;
        apiClient.createService(ApiInfoApi.class).getApiInfo().enqueue(new Callback<ApiInfo>() {
            public void onResponse(Call<ApiInfo> call, Response<ApiInfo> response) {
                if (response.isSuccessful()) {
                    apiInfoCallback.apiInfo(new DHResponse<ApiInfo>(response.body(), null));
                } else {
                    FailureData failureData = createFailData(response.code(), response.message());
                    apiInfoCallback.apiInfo(new DHResponse<ApiInfo>(null, failureData));
                }
            }

            public void onFailure(Call<ApiInfo> call, Throwable t) {
                FailureData failureData = new FailureData();
                failureData.setCode(FailureData.NO_CODE);
                failureData.setMessage(t.getMessage());
                apiInfoCallback.apiInfo(new DHResponse<ApiInfo>(null, failureData));
            }
        });
    }


    //TODO Create Cluster config getting logic
    public DHResponse<ClusterConfig> getClusterConfig() {
        return null;
    }
}
