package com.devicehive.client.service;

import com.devicehive.client.RestHelper;
import com.devicehive.client.model.BasicAuth;
import com.devicehive.client.model.FailureData;
import com.devicehive.client.model.TokenAuth;
import com.devicehive.rest.ApiClient;
import com.devicehive.rest.api.JwtTokenApi;
import com.devicehive.rest.auth.ApiKeyAuth;
import com.devicehive.rest.model.JwtAccessToken;
import com.devicehive.rest.model.JwtRefreshToken;
import com.devicehive.rest.model.JwtRequest;
import com.devicehive.rest.model.JwtToken;
import retrofit2.Response;

import java.io.IOException;

public class BaseService {
    private ApiClient apiClient;
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
        tokenAuth = new TokenAuth();
        jwtTokenApi = apiClient.createService(JwtTokenApi.class);
    }

    BaseService(TokenAuth tokenAuth) {
        this.tokenAuth = tokenAuth;
        basicAuth = new BasicAuth();
        this.apiClient = RestHelper.getInstance().getApiClient();
        jwtTokenApi = apiClient.createService(JwtTokenApi.class);
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


    FailureData createFailData(int code, String message) {
        return new FailureData(code, message);
    }
}
