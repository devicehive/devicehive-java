/*
 *
 *
 *   TokenCreateAction.java
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

package com.github.devicehive.websocket.model.request;

import com.github.devicehive.rest.model.JwtPayload;
import com.github.devicehive.websocket.model.ActionConstant;
import com.google.gson.annotations.SerializedName;

public class TokenCreateAction extends RequestAction {

    @SerializedName("payload")
    private JwtPayload payload;

    public TokenCreateAction() {
        super(ActionConstant.TOKEN_CREATE);
    }

    public JwtPayload getPayload() {
        return payload;
    }

    public void setPayload(JwtPayload payload) {
        this.payload = payload;
    }

    @Override
    public String toString() {
        return "{\n\"TokenCreateAction\":{\n"
                + "\"payload\":" + payload
                + ",\n \"requestId\":\"" + requestId + "\""
                + ",\n \"actionId\":\"" + getAction() + "\""
                + "}\n}";
    }
}
