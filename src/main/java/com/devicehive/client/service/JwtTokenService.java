package com.devicehive.client.service;

import com.devicehive.client.model.BasicAuth;
import com.devicehive.client.model.DHResponse;
import com.devicehive.client.model.TokenAuth;
import com.devicehive.rest.api.JwtTokenApi;
import com.devicehive.rest.model.JwtAccessToken;
import com.devicehive.rest.model.JwtPayload;
import com.devicehive.rest.model.JwtRefreshToken;
import com.devicehive.rest.model.JwtToken;
import org.joda.time.DateTime;

import java.io.IOException;
import java.util.List;

public class JwtTokenService extends BaseService {
    private JwtTokenService() {
    }

    public JwtTokenService(BasicAuth basicAuth) {
        super(basicAuth);
    }

    public JwtTokenService(TokenAuth tokenAuth, BasicAuth basicAuth) {
        super(tokenAuth, basicAuth);
    }

    public JwtTokenService(TokenAuth tokenAuth) {
        super(tokenAuth);
    }

    public DHResponse<JwtToken> createToken(List<String> actions, Long userId, List<String> networkIds, List<String> deviceIds, DateTime expiration) throws IOException {
        authorize();
        JwtTokenApi jwtService = createService(JwtTokenApi.class);
        JwtPayload payload = new JwtPayload();
        payload.setActions(actions);
        payload.setUserId(userId);
        payload.setNetworkIds(networkIds);
        payload.setDeviceIds(deviceIds);
        payload.setExpiration(expiration);

        DHResponse<JwtToken> response = execute(jwtService.tokenRequest(payload));
        if (response.isSuccessful()) {
            return response;
        } else if (response.getFailureData().getCode() == 401) {
            authorize();
            jwtService = apiClient.createService(JwtTokenApi.class);
            response = execute(jwtService.tokenRequest(payload));
            if (response.isSuccessful()) {
                TokenAuth tokenAuth = new TokenAuth();
                tokenAuth.setRefreshToken(response.getData().getRefreshToken());
                tokenAuth.setAccessToken(response.getData().getAccessToken());
                setTokenAuth(tokenAuth);
            }
            return response;
        } else {
            return response;
        }
    }

    public DHResponse<JwtAccessToken> getRefreshToken() throws IOException {
        JwtTokenApi jwtService = createService(JwtTokenApi.class);

        JwtRefreshToken refreshToken = new JwtRefreshToken();
        refreshToken.setRefreshToken(getTokenAuth().getRefreshToken());

        DHResponse<JwtAccessToken> response = execute(jwtService.refreshTokenRequest(refreshToken));
        return response;
    }
}
