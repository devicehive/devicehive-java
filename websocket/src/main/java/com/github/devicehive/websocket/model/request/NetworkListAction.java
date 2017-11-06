/*
 *
 *
 *   NetworkListAction.java
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

package com.github.devicehive.websocket.model.request;

import com.github.devicehive.rest.model.SortField;
import com.github.devicehive.rest.model.SortOrder;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import static com.github.devicehive.websocket.model.ActionConstant.NETWORK_LIST;

@Data
@EqualsAndHashCode(callSuper = false)
public class NetworkListAction extends RequestAction {

    @SerializedName("name")
    private String name;
    @SerializedName("namePattern")
    private String namePattern;
    @SerializedName("sortField")
    private SortField sortField;
    @SerializedName("sortOrder")
    private SortOrder sortOrder;
    @SerializedName("take")
    private Integer take;
    @SerializedName("skip")
    private Integer skip;

    public NetworkListAction() {
        super(NETWORK_LIST);
    }
}
