package com.devicehive.client.service;

import com.devicehive.client.RestHelper;
import com.devicehive.client.callback.ResponseCallback;
import com.devicehive.client.model.BasicAuth;
import com.devicehive.client.model.DHResponse;
import com.devicehive.client.model.FailureData;
import com.devicehive.client.model.TokenAuth;
import com.devicehive.rest.ApiClient;
import com.devicehive.rest.api.JwtTokenApi;
import com.devicehive.rest.auth.ApiKeyAuth;
import com.devicehive.rest.model.*;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.IOException;

public class BaseService {
    ApiClient apiClient;
    private JwtTokenApi jwtTokenApi;
    private TokenAuth tokenAuth = new TokenAuth();
    private BasicAuth basicAuth = new BasicAuth();

    BaseService() {
        this.apiClient = RestHelper.getInstance().getApiClient();
        jwtTokenApi = apiClient.createService(JwtTokenApi.class);
        tokenAuth = new TokenAuth();
        basicAuth = new BasicAuth();
    }

    BaseService(BasicAuth basicAuth) {
        this.apiClient = RestHelper.getInstance().getApiClient();
        this.basicAuth = basicAuth;
        jwtTokenApi = apiClient.createService(JwtTokenApi.class);
    }

    BaseService(TokenAuth tokenAuth) {
        this.tokenAuth = tokenAuth;
        this.apiClient = RestHelper.getInstance().getApiClient();
        jwtTokenApi = apiClient.createService(JwtTokenApi.class);
    }

    BaseService(TokenAuth tokenAuth, BasicAuth basicAuth) {
        if (tokenAuth != null) {
            this.tokenAuth = tokenAuth;
        }
        if (basicAuth != null) {
            this.basicAuth = basicAuth;
        }
        this.apiClient = RestHelper.getInstance().getApiClient();
        jwtTokenApi = apiClient.createService(JwtTokenApi.class);
    }

    public TokenAuth getTokenAuth() {
        return tokenAuth;
    }

    public BasicAuth getBasicAuth() {
        return basicAuth;
    }

    public void setTokenAuth(TokenAuth tokenAuth) {
        this.tokenAuth = tokenAuth;
    }

    public void setBasicAuth(BasicAuth basicAuth) {
        this.basicAuth = basicAuth;
    }

    public void updateAccessToken(JwtAccessToken token) {
        tokenAuth.setAccessToken(token.getAccessToken());
    }

    public void authorize() throws IOException {
        if (tokenAuth.canAccess()) {
            authorizeViaToken();
        } else if (tokenAuth.canRefresh()) {
            refreshToken();
            authorize();
        } else if (basicAuth.isValid()) {
            getToken();
            authorize();
        } else {
            throw new IOException("Cannot authorize. Nor username/password data nor " +
                    "accessToken/refreshToken data was provided");
        }
    }

    public void clearAccessToken() {
        tokenAuth.setAccessToken(null);
    }

    public void clearRefreshToken() {
        tokenAuth.setRefreshToken(null);
    }

    private void authorizeViaToken() {
        ApiKeyAuth apiKeyAuth = ApiKeyAuth.newInstance();
        apiKeyAuth.setApiKey(tokenAuth.getAccessToken());
        apiClient.addAuthorization(ApiClient.AUTH_API_KEY, apiKeyAuth);
    }

    void getToken() throws IOException {
        JwtRequest body = new JwtRequest();
        body.setLogin(basicAuth.getUsername());
        body.setPassword(basicAuth.getPassword());


        JwtToken token = jwtTokenApi.login(body).execute().body();
        TokenAuth tokenAuth = new TokenAuth();
        tokenAuth.setAccessToken(token.getAccessToken());
        tokenAuth.setRefreshToken(token.getRefreshToken());
        this.tokenAuth = tokenAuth;
    }

    protected void refreshToken() throws IOException {
        JwtRefreshToken refreshToken = new JwtRefreshToken();
        refreshToken.setRefreshToken(tokenAuth.getRefreshToken());
        Response<JwtAccessToken> response = jwtTokenApi.refreshTokenRequest(refreshToken).execute();
        JwtAccessToken accessToken = response.body();
        tokenAuth.setAccessToken(accessToken.getAccessToken());
    }


    final <T> DHResponse<T> execute(Call<T> call) {
        try {
            Response<T> response = call.execute();
            if (response.isSuccessful()) {
                return new DHResponse<T>(response.body(), null);
            } else {
                FailureData failureData = createFailData(response.code(), response.message());
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
                    FailureData failureData = createFailData(response.code(), response.message());
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
}
