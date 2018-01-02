/*
 *
 *
 *   FailureData.java
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

import com.github.devicehive.websocket.model.repsonse.ErrorResponse;

public class FailureData {
    public static final int NO_CODE = -1;

    private int code;
    private String message;

    public FailureData() {
    }

    public FailureData(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public FailureData(String message) {
        this.message = message;
        this.code = NO_CODE;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static FailureData create(ErrorResponse response) {
        return new FailureData(response.getCode(), response.getError());
    }

    @Override
    public String toString() {
        return "FailureData{" +
                "code=" + code +
                ", message='" + message + '\'' +
                '}';
    }
}
