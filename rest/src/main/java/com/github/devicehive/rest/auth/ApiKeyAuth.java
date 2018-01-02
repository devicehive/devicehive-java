/*
 *
 *
 *   ApiKeyAuth.java
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

package com.github.devicehive.rest.auth;


import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class ApiKeyAuth implements Interceptor {
    private static final String HEADER = "header";
    private final String location;
    private final String paramName;
    private static final String KEY_AUTH_SCHEMA = "Bearer ";
    private static final String AUTH_NAME = "Authorization";


    private String apiKey;

    public static ApiKeyAuth newInstance(String apiKey) {
        ApiKeyAuth apiKeyAuth = new ApiKeyAuth(HEADER, AUTH_NAME);
        apiKeyAuth.setApiKey(apiKey);
        return apiKeyAuth;
    }

    public static ApiKeyAuth newInstance() {
        return new ApiKeyAuth(HEADER, AUTH_NAME);
    }

    private ApiKeyAuth(String location, String paramName) {
        this.location = location;
        this.paramName = paramName;
    }

    public String getLocation() {
        return location;
    }

    public String getParamName() {
        return paramName;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        String paramValue;
        Request request = chain.request();
        if (location.equals("query")) {
            String newQuery = request.url().uri().getQuery();
            paramValue = paramName + "=" + apiKey;
            if (newQuery == null) {
                newQuery = paramValue;
            } else {
                newQuery += "&" + paramValue;
            }

            URI newUri;

            try {
                newUri = new URI(request.url().uri().getScheme(), request.url().uri().getAuthority(),
                        request.url().uri().getPath(), newQuery, request.url().uri().getFragment());
            } catch (URISyntaxException e) {
                throw new IOException(e);
            }
            System.out.println(newUri);
            request = request.newBuilder().url(newUri.toURL()).build();
        } else if (location.equals("header")) {
            request = request.newBuilder()
                    .addHeader(paramName, KEY_AUTH_SCHEMA + apiKey)
                    .addHeader("Accept", "application/json")
                    .build();
        }
        return chain.proceed(request);
    }

    @Override
    public String toString() {
        return location + " " + paramName;
    }
}
