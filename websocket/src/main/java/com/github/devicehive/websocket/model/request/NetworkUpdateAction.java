/*
 *
 *
 *   NetworkUpdateAction.java
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

import com.github.devicehive.rest.model.NetworkUpdate;
import com.github.devicehive.websocket.model.ActionConstant;
import com.google.gson.annotations.SerializedName;

public class NetworkUpdateAction extends RequestAction {

    @SerializedName("networkId")
    private Long networkId;
    @SerializedName("network")
    private NetworkUpdate network;
    
    public NetworkUpdateAction() {
        super(ActionConstant.NETWORK_UPDATE);
    }

    public Long getNetworkId() {
        return networkId;
    }

    public void setNetworkId(Long networkId) {
        this.networkId = networkId;
    }

    public NetworkUpdate getNetwork() {
        return network;
    }

    public void setNetwork(NetworkUpdate network) {
        this.network = network;
    }
}
