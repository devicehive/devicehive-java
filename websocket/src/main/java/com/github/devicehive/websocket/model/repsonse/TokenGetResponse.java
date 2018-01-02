/*
 *
 *
 *   TokenGetResponse.java
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

package com.github.devicehive.websocket.model.repsonse;

import com.google.gson.annotations.SerializedName;

public class TokenGetResponse extends ResponseAction {

    @SerializedName("accessToken")
    String accessToken;
    @SerializedName("refreshToken")
    String refreshToken;

    public TokenGetResponse() {
    }

    public TokenGetResponse(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    @Override
    public String toString() {
        return "{\n\"TokenGetResponse\":{\n"
                + " \"action\":\"" + action + "\""
                + ",\n \"requestId\":\"" + requestId + "\""
                + ",\n \"status\":\"" + status + "\""
                + ",\n \"accessToken\":\"" + accessToken + "\""
                + ",\n \"refreshToken\":\"" + refreshToken + "\""
                + "}\n}";
    }
}
