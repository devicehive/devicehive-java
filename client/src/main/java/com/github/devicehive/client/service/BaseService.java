/*
 *
 *
 *   BaseService.java
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
import com.github.devicehive.client.model.ErrorBody;
import com.github.devicehive.client.model.FailureData;
import com.github.devicehive.client.model.Parameter;
import com.github.devicehive.client.model.TokenAuth;
import com.github.devicehive.rest.ApiClient;
import com.github.devicehive.rest.api.AuthApi;
import com.github.devicehive.rest.auth.ApiKeyAuth;
import com.github.devicehive.rest.model.JwtAccessToken;
import com.github.devicehive.rest.model.JwtRefreshToken;
import com.github.devicehive.websocket.model.repsonse.ErrorResponse;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BaseService {
    public static final String ERROR_MESSAGE_KEY = "message";
    public static final String FAILED_CONNECT_TO_SERVER = "Failed connect to server";
    ApiClient apiClient;

    BaseService() {
        this.apiClient = RestHelper.getInstance().getApiClient();
    }

    TokenAuth getTokenAuth() {
        return TokenHelper.getInstance().getTokenAuth();
    }


    private void updateAccessToken(JwtAccessToken token) {
        TokenHelper.getInstance().getTokenAuth().setAccessToken(token.getAccessToken());
    }


    void refreshAndAuthorize() {
        if (getTokenAuth().canRefresh()) {
            clearAccessToken();
            refreshToken();
            authorizeViaToken();
        }
    }

    private void authorizeViaToken() {
        if (getTokenAuth().canAccess()) {
            ApiKeyAuth apiKeyAuth = ApiKeyAuth.newInstance();
            apiKeyAuth.setApiKey(getTokenAuth().getAccessToken());
            apiClient.addAuthorization(ApiClient.AUTH_API_KEY, apiKeyAuth);
        }
    }

    private void clearAccessToken() {
        apiClient.clearAuthorizations();
        TokenHelper.getInstance().getTokenAuth().setAccessToken(null);
    }

    private void refreshToken() {
        JwtRefreshToken refreshToken = new JwtRefreshToken();
        refreshToken.setRefreshToken(getTokenAuth().getRefreshToken());
        AuthApi authApi = createService(AuthApi.class);
        Response<JwtAccessToken> response = null;
        try {
            response = authApi.refreshTokenRequest(refreshToken).execute();
            if (response.isSuccessful()) {
                JwtAccessToken accessToken = response.body();
                updateAccessToken(accessToken);
            }
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
                if (failureData.getCode() == 401) {
                    clearAccessToken();
                }
                return new DHResponse<T>(null, failureData);
            }
        } catch (IOException e) {
            FailureData failureData = createFailData(FailureData.NO_CODE, e.getMessage());
            return new DHResponse<T>(null, failureData);
        } catch (NullPointerException e) {
            return new DHResponse<T>(null, FailureData.create(
                    ErrorResponse.create(FAILED_CONNECT_TO_SERVER)));
        }
    }

    final <T> void execute(Call<T> call, final ResponseCallback<T> callback) {
        call.enqueue(new Callback<T>() {
            public void onResponse(Call<T> call, Response<T> response) {
                if (response.isSuccessful()) {
                    callback.onResponse(new DHResponse<T>(response.body(), null));
                } else {
                    FailureData failureData = createFailData(response.code(),
                            BaseService.parseErrorMessage(response));
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
        return createService(serviceClass, false);
    }
    //FIXME Delete this method after the fix of Headers filter on backend;
    public <S> S createService(Class<S> serviceClass, boolean clearInterceptors) {
        if (clearInterceptors) {
            clearAccessToken();
        }
        return apiClient.createService(serviceClass);
    }

    JsonObject wrapParameters(List<Parameter> parameters) {
        JsonObject object = new JsonObject();
        for (Parameter p :
                parameters) {
            object.addProperty(p.getKey(), p.getValue());
        }
        return object;
    }

    static String parseErrorMessage(Response response) {
        try {
            Gson gson = new Gson();
            ErrorBody errorBody = gson.fromJson(response.errorBody().string(),
                    ErrorBody.class);
            return errorBody.getMessage();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response.message();
    }
}
