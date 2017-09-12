package com.devicehive.client;

import com.devicehive.rest.ApiClient;

public class RestHelper {
    private final ApiClient apiClient;

    private RestHelper() {
        if (DeviceHive.URL == null || DeviceHive.URL.length() <= 0) {
            throw new NullPointerException("Server URL cannot be null or empty");
        }
        apiClient = new ApiClient(DeviceHive.URL);
    }

    private static class InstanceHolder {
        static final RestHelper INSTANCE = new RestHelper();
    }

    public static RestHelper getInstance() {
        return RestHelper.InstanceHolder.INSTANCE;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }
}

