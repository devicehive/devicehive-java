package com.devicehive.client.service;

import com.devicehive.rest.ApiClient;

class RestHelper {
    private final ApiClient apiClient;

    private RestHelper() {
        if (DeviceHive.getInstance().getUrl() == null || DeviceHive.getInstance().getUrl().length() <= 0) {
            throw new NullPointerException("Server URL cannot be null or empty");
        }
        apiClient = new ApiClient(DeviceHive.getInstance().getUrl());
    }

    private static class InstanceHolder {
        static final RestHelper INSTANCE = new RestHelper();
    }

    static RestHelper getInstance() {
        return RestHelper.InstanceHolder.INSTANCE;
    }

    ApiClient getApiClient() {
        return apiClient;
    }
}

