/*
 *
 *
 *   NetworkInsertResponse.java
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

import com.github.devicehive.rest.model.NetworkId;
import com.google.gson.annotations.SerializedName;

public class NetworkInsertResponse extends ResponseAction {

    @SerializedName("network")
    private NetworkId network;

    public NetworkId getNetwork() {
        return network;
    }

    public void setNetwork(NetworkId network) {
        this.network = network;
    }
}
