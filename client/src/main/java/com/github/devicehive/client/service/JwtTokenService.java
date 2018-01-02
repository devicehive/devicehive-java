/*
 *
 *
 *   JwtTokenService.java
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

import com.github.devicehive.client.model.DHResponse;
import com.github.devicehive.rest.api.AuthApi;
import com.github.devicehive.rest.model.JwtAccessToken;
import com.github.devicehive.rest.model.JwtPayload;
import com.github.devicehive.rest.model.JwtRefreshToken;
import com.github.devicehive.rest.model.JwtToken;
import org.joda.time.DateTime;

import java.util.List;

class JwtTokenService extends BaseService {


    DHResponse<JwtToken> createToken(List<String> actions, Long userId, List<String> networkIds, List<String> deviceIds, DateTime expiration) {
        AuthApi jwtService = createService(AuthApi.class);
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
            refreshAndAuthorize();
            jwtService = createService(AuthApi.class);
            response = execute(jwtService.tokenRequest(payload));
            if (response.isSuccessful()) {
                TokenHelper.getInstance().getTokenAuth().setRefreshToken(response.getData().getRefreshToken());
                TokenHelper.getInstance().getTokenAuth().setAccessToken(response.getData().getAccessToken());
            }
            return response;
        } else {
            return response;
        }
    }

    DHResponse<JwtAccessToken> getRefreshToken() {
        AuthApi jwtService = createService(AuthApi.class);

        JwtRefreshToken refreshToken = new JwtRefreshToken();
        refreshToken.setRefreshToken(getTokenAuth().getRefreshToken());
        DHResponse<JwtAccessToken> response = execute(jwtService.refreshTokenRequest(refreshToken));
        if (response.isSuccessful()) {
            return response;
        } else if (response.getFailureData().getCode() == 401) {
            refreshAndAuthorize();
            jwtService = createService(AuthApi.class);
            response = execute(jwtService.refreshTokenRequest(refreshToken));
            return response;
        } else {
            return response;
        }
    }
}
