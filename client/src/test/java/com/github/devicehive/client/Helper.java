/*
 *
 *
 *   Helper.java
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

package com.github.devicehive.client;

import com.github.devicehive.rest.ApiClient;
import com.github.devicehive.rest.api.AuthApi;
import com.github.devicehive.rest.model.JwtRequest;
import com.github.devicehive.rest.model.JwtToken;

import java.io.IOException;

public class Helper {

    JwtToken login(String url) throws IOException {
        String login = System.getProperty("login");
        String password = System.getProperty("password");
        ApiClient apiClient = new ApiClient(url);

        AuthApi authApi = apiClient.createService(AuthApi.class);
        JwtRequest body = new JwtRequest();
        body.setLogin(login);
        body.setPassword(password);

        return authApi.login(body).execute().body();
    }
}
