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

import java.io.IOException;

public class BaseService {
    private ApiClient apiClient;
    private JwtTokenApi jwtTokenApi;
    private TokenAuth tokenAuth = new TokenAuth();

    BaseService() {
        this.apiClient = RestHelper.getInstance().getApiClient();
        jwtTokenApi = apiClient.createService(JwtTokenApi.class);
    }


    protected void authorize(TokenAuth tokenAuth) {
        this.tokenAuth = tokenAuth;

        ApiKeyAuth apiKeyAuth = ApiKeyAuth.newInstance();
        apiKeyAuth.setApiKey(tokenAuth.getAccessToken());
        apiClient.addAuthorization(ApiClient.AUTH_API_KEY, apiKeyAuth);

    }

    protected void authorize(BasicAuth basicAuth) throws IOException {
        JwtRequest body = new JwtRequest();
        body.setLogin(basicAuth.getUsername());
        body.setPassword(basicAuth.getPassword());


        JwtToken token = jwtTokenApi.login(body).execute().body();
        TokenAuth tokenAuth = new TokenAuth();
        tokenAuth.setAccessToken(token.getAccessToken());
        tokenAuth.setRefreshToken(token.getRefreshToken());
        this.tokenAuth = tokenAuth;
        authorize(tokenAuth);
    }

    protected void refreshToken() throws IOException {
        JwtRefreshToken refreshToken = new JwtRefreshToken();
        refreshToken.setRefreshToken(tokenAuth.getRefreshToken());
        JwtAccessToken accessToken = jwtTokenApi.refreshTokenRequest(refreshToken).execute().body();
        tokenAuth.setAccessToken(accessToken.getAccessToken());
    }


    FailureData createFailData(int code, String message) {
        return new FailureData(code, message);
    }
}
