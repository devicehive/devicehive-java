/*
 *
 *
 *   ErrorResponse.java
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

public class ErrorResponse extends ResponseAction {
    public static final String ERROR = "error";
    public static final int DEFAULT_CODE = -1;
    @SerializedName("code")
    int code;
    @SerializedName("error")
    String error;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return "{\n\"ErrorResponse\":{\n"
                + " \"action\":\"" + action + "\""
                + ",\n \"requestId\":\"" + requestId + "\""
                + ",\n \"code\":\"" + code + "\""
                + ",\n \"status\":\"" + status + "\""
                + ",\n \"error\":\"" + error + "\""
                + "}\n}";
    }

    public static ErrorResponse create(int code, String message) {
        ErrorResponse response = new ErrorResponse();
        response.setCode(code);
        response.setError(message);
        return response;
    }

    public static ErrorResponse create(String message) {
        ErrorResponse response = new ErrorResponse();
        response.setCode(DEFAULT_CODE);
        response.setError(message);
        return response;
    }
}