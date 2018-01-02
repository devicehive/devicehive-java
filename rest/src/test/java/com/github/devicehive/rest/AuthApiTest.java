/*
 *
 *
 *   AuthApiTest.java
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

package com.github.devicehive.rest;

import com.github.devicehive.rest.api.AuthApi;
import com.github.devicehive.rest.model.JwtAccessToken;
import com.github.devicehive.rest.model.JwtPayload;
import com.github.devicehive.rest.model.JwtRefreshToken;
import com.github.devicehive.rest.model.JwtRequest;
import com.github.devicehive.rest.model.JwtToken;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;

public class AuthApiTest extends Helper {


    @Test
    public void getToken() throws IOException {
        AuthApi api = client.createService(AuthApi.class);
        JwtRequest requestBody = new JwtRequest();
        requestBody.setLogin(System.getProperty("login"));
        requestBody.setPassword(System.getProperty("password"));

        Response<JwtToken> response = api.login(requestBody).execute();

        Assert.assertTrue(response.isSuccessful());
        JwtToken tokenVO = response.body();
        Assert.assertTrue(tokenVO != null);
        Assert.assertTrue(tokenVO.getAccessToken() != null);
        Assert.assertTrue(tokenVO.getAccessToken().length() > 0);
    }

    @Test
    public void getTokenIncorrectCredentials() throws IOException {
        AuthApi api = client.createService(AuthApi.class);
        com.github.devicehive.rest.model.JwtRequest requestBody = new com.github.devicehive.rest.model.JwtRequest();
        requestBody.setLogin("incorrectLogin");
        requestBody.setPassword("incorrectPassword");
        Response<JwtToken> response = api.login(requestBody).execute();
        Assert.assertTrue(!response.isSuccessful());
        Assert.assertTrue(response.body() == null);
    }

    @Test
    public void refreshToken() throws IOException {
        AuthApi api = client.createService(AuthApi.class);
        JwtRequest requestBody = new JwtRequest();
        requestBody.setLogin(System.getProperty("login"));
        requestBody.setPassword(System.getProperty("password"));

        Response<JwtToken> loginResponse = api.login(requestBody).execute();

        Assert.assertTrue(loginResponse.isSuccessful());
        JwtToken tokenVO = loginResponse.body();
        Assert.assertNotNull(tokenVO);
        Assert.assertNotNull(tokenVO.getAccessToken());
        Assert.assertNotEquals(0, tokenVO.getAccessToken().length());
        Assert.assertNotNull(tokenVO.getRefreshToken());
        Assert.assertNotEquals(0, tokenVO.getRefreshToken().length());

        JwtRefreshToken jwtRefreshToken = new JwtRefreshToken();
        jwtRefreshToken.setRefreshToken(tokenVO.getRefreshToken());
        Response<JwtAccessToken> refreshResponse = api.refreshTokenRequest(jwtRefreshToken).execute();

        JwtAccessToken refreshedToken = refreshResponse.body();
        Assert.assertNotNull(refreshedToken);
        Assert.assertNotNull(refreshedToken.getAccessToken());
        Assert.assertNotEquals(0, refreshedToken.getAccessToken().length());
    }

    @Test
    public void requestToken() throws IOException {
        boolean authenticated = authenticate();
        Assert.assertTrue(authenticated);

        AuthApi api = client.createService(AuthApi.class);
        JwtPayload jwtPayload = createAdminJwtPayload(1L);

        Response<JwtToken> putResponse = api.tokenRequest(jwtPayload).execute();
        Assert.assertTrue(putResponse.isSuccessful());
        JwtToken tokenVO = putResponse.body();
        Assert.assertNotNull(tokenVO);
        Assert.assertNotNull(tokenVO.getAccessToken());
        Assert.assertNotEquals(0, tokenVO.getAccessToken().length());
        Assert.assertNotNull(tokenVO.getRefreshToken());
        Assert.assertNotEquals(0, tokenVO.getRefreshToken().length());
    }

    private JwtPayload createAdminJwtPayload(Long userId) {
        JwtPayload jwtPayload = new JwtPayload();
        List<String> actions = new ArrayList<String>();
        actions.add("*");
        List<String> networkIds = new ArrayList<String>();
        networkIds.add("*");
        List<String> deviceIds = new ArrayList<String>();
        deviceIds.add("*");
        DateTime dateTime = DateTime.now().plusYears(1);
        jwtPayload.setUserId(userId);
        jwtPayload.setActions(actions);
        jwtPayload.setNetworkIds(networkIds);
        jwtPayload.setDeviceIds(deviceIds);
        jwtPayload.setExpiration(dateTime);
        return jwtPayload;
    }

}
