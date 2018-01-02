/*
 *
 *
 *   AuthApi.java
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

package com.github.devicehive.rest.api;

import com.github.devicehive.rest.model.JwtAccessToken;
import com.github.devicehive.rest.model.JwtPayload;
import com.github.devicehive.rest.model.JwtRefreshToken;
import com.github.devicehive.rest.model.JwtToken;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;


public interface AuthApi {
    /**
     * Login
     * Authenticates a user and returns a session-level JWT token.
     *
     * @param body Access key request (required)
     * @return Call&lt;JwtToken&gt;
     */
    @Headers({
            "Content-Type:application/json"
    })
    @POST( "token")
    Call<JwtToken> login(
            @Body com.github.devicehive.rest.model.JwtRequest body
    );

    /**
     * JWT access token request with refresh token
     *
     * @param refreshToken Refresh token (required)
     * @return Call&lt;JwtAccessToken&gt;
     */
    @Headers({
            "Content-Type:application/json"
    })
    @POST( "token/refresh")
    Call<JwtAccessToken> refreshTokenRequest(
            @Body JwtRefreshToken refreshToken
    );

    /**
     * JWT access and refresh token request
     *
     * @param payload Payload (required)
     * @return Call&lt;JwtToken&gt;
     */
    @Headers({
            "Content-Type:application/json"
    })
    @POST( "token/create")
    Call<JwtToken> tokenRequest(
            @Body JwtPayload payload
    );

}
