package com.devicehive.client.service;

import com.devicehive.client.callback.ResponseCallback;
import com.devicehive.client.model.DHResponse;
import com.devicehive.client.model.FailureData;
import com.devicehive.client.model.Parameter;
import com.devicehive.client.model.TokenAuth;
import com.devicehive.rest.ApiClient;
import com.devicehive.rest.api.JwtTokenApi;
import com.devicehive.rest.auth.ApiKeyAuth;
import com.devicehive.rest.model.JsonStringWrapper;
import com.devicehive.rest.model.JwtAccessToken;
import com.devicehive.rest.model.JwtRefreshToken;
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
