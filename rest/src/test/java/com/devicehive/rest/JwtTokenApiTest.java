package com.devicehive.rest;

import com.devicehive.rest.api.JwtTokenApi;
import com.devicehive.rest.model.JwtRequest;
import com.devicehive.rest.model.JwtToken;
import org.junit.Assert;
import org.junit.Test;
import retrofit2.Response;

import java.io.IOException;

public class JwtTokenApiTest extends Helper {


    @Test
    public void getToken() throws IOException {
        JwtTokenApi api = client.createService(JwtTokenApi.class);
        JwtRequest requestBody = new JwtRequest();
        requestBody.setLogin("***REMOVED***");
        requestBody.setPassword("***REMOVED***");

        Response<JwtToken> response = api.login(requestBody).execute();
        Assert.assertTrue(response.isSuccessful());
        JwtToken tokenVO = response.body();
        Assert.assertTrue(tokenVO != null);
        Assert.assertTrue(tokenVO.getAccessToken() != null);
        Assert.assertTrue(tokenVO.getAccessToken().length() > 0);
    }

    @Test
    public void getTokenIncorrectCredentials() throws IOException {
        JwtTokenApi api = client.createService(JwtTokenApi.class);
        JwtRequest requestBody = new JwtRequest();
        requestBody.setLogin("incorrectLogin");
        requestBody.setPassword("incorrectPassword");
        Response<JwtToken> response = api.login(requestBody).execute();
        Assert.assertTrue(!response.isSuccessful());
        Assert.assertTrue(response.body() == null);
    }


}
