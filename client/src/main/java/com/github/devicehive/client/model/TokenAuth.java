/*
 *
 *
 *   TokenAuth.java
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

package com.github.devicehive.client.model;

public class TokenAuth {

    private String refreshToken;
    private String accessToken;

    public TokenAuth(String refreshToken, String accessToken) {
        this.refreshToken = refreshToken;
        this.accessToken = accessToken;
        System.out.println("TokenAuth:"+accessToken);
    }

    public TokenAuth() {
    }

    public TokenAuth(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public boolean canAccess() {
        return accessToken != null && accessToken.length() > 0;
    }

    public boolean canRefresh() {
        return refreshToken != null && refreshToken.length() > 0;
    }


    public TokenAuth setAccessToken(String accessToken) {
        this.accessToken = accessToken;
        return this;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public TokenAuth setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
        return this;
    }

    @Override
    public String toString() {
        return "{\n\"TokenAuth\":{\n"
                + "\"refreshToken\":\"" + refreshToken + "\""
                + ",\n \"accessToken\":\"" + accessToken + "\""
                + "}\n}";
    }
}
