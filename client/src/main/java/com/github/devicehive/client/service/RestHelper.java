/*
 *
 *
 *   RestHelper.java
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

package com.github.devicehive.client.service;

import com.github.devicehive.rest.ApiClient;
import com.github.devicehive.rest.api.AuthApi;
import com.github.devicehive.rest.model.JwtAccessToken;
import com.github.devicehive.rest.model.JwtRefreshToken;

import java.util.concurrent.TimeUnit;

import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Callback;

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

    void authorize(Callback<JwtAccessToken> callback) {
        AuthApi authApi = RestHelper.getInstance().getApiClient().createService(AuthApi.class);
        JwtRefreshToken token = new JwtRefreshToken();
        token.setRefreshToken(TokenHelper.getInstance().getTokenAuth().getRefreshToken());
        authApi.refreshTokenRequest(token).enqueue(callback);
    }
    void enableDebug(boolean enable) {
        enableDebug(enable, HttpLoggingInterceptor.Level.BODY);
    }

    void enableDebug(boolean enable, HttpLoggingInterceptor.Level level) {
        if (enable) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
//         set your desired log level
            logging.setLevel(level);
            getInstance().getApiClient()
                    .getOkClient()
                    .newBuilder()
                    .addInterceptor(logging)
                    .readTimeout(35, TimeUnit.SECONDS)
                    .connectTimeout(35, TimeUnit.SECONDS)
                    .build();
        } else {
            getInstance().getApiClient().getOkClient().newBuilder()
                    .readTimeout(35, TimeUnit.SECONDS)
                    .connectTimeout(35, TimeUnit.SECONDS)
                    .build();
        }
    }
}

