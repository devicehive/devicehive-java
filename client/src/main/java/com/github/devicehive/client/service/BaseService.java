/*
 *
 *
 *   BaseService.java
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

import com.github.devicehive.client.callback.ResponseCallback;
import com.github.devicehive.client.model.DHResponse;
import com.github.devicehive.client.model.FailureData;
import com.github.devicehive.client.model.Parameter;
import com.github.devicehive.client.model.TokenAuth;
import com.github.devicehive.rest.ApiClient;
import com.github.devicehive.rest.api.JwtTokenApi;
import com.github.devicehive.rest.auth.ApiKeyAuth;
import com.github.devicehive.rest.model.JsonStringWrapper;
import com.github.devicehive.rest.model.JwtAccessToken;
import com.github.devicehive.rest.model.JwtRefreshToken;
import org.json.JSONObject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.IOException;
import java.util.List;

public class BaseService {
    public static final String ERROR_MESSAGE_KEY = "message";
    ApiClient apiClient;

    BaseService() {
        this.apiClient = RestHelper.getInstance().getApiClient();
    }

    public TokenAuth getTokenAuth() {
        return TokenHelper.getInstance().getTokenAuth();
    }


    public void updateAccessToken(JwtAccessToken token) {
        TokenHelper.getInstance().getTokenAuth().setAccessToken(token.getAccessToken());
    }

    void authorize() {
        if (getTokenAuth().canRefresh()) {
            refreshToken();
            authorizeViaToken();
        }
    }

    private void authorizeViaToken() {
        ApiKeyAuth apiKeyAuth = ApiKeyAuth.newInstance();
        apiKeyAuth.setApiKey(getTokenAuth().getAccessToken());
        apiClient.addAuthorization(ApiClient.AUTH_API_KEY, apiKeyAuth);
    }

    private void refreshToken() {
        JwtRefreshToken refreshToken = new JwtRefreshToken();
        refreshToken.setRefreshToken(getTokenAuth().getRefreshToken());
        JwtTokenApi jwtTokenApi = createService(JwtTokenApi.class);
        Response<JwtAccessToken> response = null;
        try {
            response = jwtTokenApi.refreshTokenRequest(refreshToken).execute();
            JwtAccessToken accessToken = response.body();
            getTokenAuth().setAccessToken(accessToken.getAccessToken());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    final <T> DHResponse<T> execute(Call<T> call) {
        try {
            Response<T> response = call.execute();
            if (response.isSuccessful()) {
                return new DHResponse<T>(response.body(), null);
            } else {
                FailureData failureData = createFailData(response.code(), parseErrorMessage(response));
                return new DHResponse<T>(null, failureData);
            }
        } catch (IOException e) {
            FailureData failureData = createFailData(FailureData.NO_CODE, e.getMessage());
            return new DHResponse<T>(null, failureData);
        }
    }

    final <T> void execute(Call<T> call, final ResponseCallback<T> callback) {
        call.enqueue(new Callback<T>() {
            public void onResponse(Call<T> call, Response<T> response) {
                if (response.isSuccessful()) {
                    callback.onResponse(new DHResponse<T>(response.body(), null));
                } else {
                    FailureData failureData = createFailData(response.code(), parseErrorMessage(response));
                    callback.onResponse(new DHResponse<T>(null, failureData));
                }
            }

            public void onFailure(Call<T> call, Throwable t) {
                FailureData failureData = new FailureData();
                failureData.setCode(FailureData.NO_CODE);
                failureData.setMessage(t.getMessage());
                callback.onResponse(new DHResponse<T>(null, failureData));
            }
        });
    }

    FailureData createFailData(int code, String message) {
        return new FailureData(code, message);
    }

    public <S> S createService(Class<S> serviceClass) {
        return apiClient.createService(serviceClass);
    }

    JsonStringWrapper wrapParameters(List<Parameter> parameters) {
        JSONObject object = new JSONObject();
        for (Parameter p :
                parameters) {
            object.put(p.getKey(), p.getValue());
        }
        JsonStringWrapper wrapper = new JsonStringWrapper();
        wrapper.setJsonString(object.toString());
        return wrapper;
    }

    String parseErrorMessage(Response response) {
        try {
            JSONObject error = new JSONObject(response.errorBody().string());
            return error.getString(ERROR_MESSAGE_KEY);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response.message();
    }
}
