/*
 *
 *
 *   UserGetNetworkAction.java
 *
 *   Copyright (C) 2017 DataArt
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

package com.devicehive.websocket.model.request;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import static com.devicehive.websocket.model.ActionConstant.USER_GET_NETWORK;

@Data
public class UserGetNetworkAction extends RequestAction {

    @SerializedName("userId")
    private Long userId;
    @SerializedName("networkId")
    private Long networkId;

    public UserGetNetworkAction() {
        super(USER_GET_NETWORK);
    }
}
