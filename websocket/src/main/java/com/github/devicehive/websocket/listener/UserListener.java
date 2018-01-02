/*
 *
 *
 *   UserListener.java
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

package com.github.devicehive.websocket.listener;

import com.github.devicehive.websocket.model.repsonse.UserGetResponse;

public interface UserListener extends ErrorListener {

    void onList(com.github.devicehive.websocket.model.repsonse.UserListResponse response);

    void onGet(UserGetResponse response);

    void onInsert(com.github.devicehive.websocket.model.repsonse.UserInsertResponse response);

    void onUpdate(com.github.devicehive.websocket.model.repsonse.ResponseAction response);

    void onDelete(com.github.devicehive.websocket.model.repsonse.ResponseAction response);

    void onGetCurrent(com.github.devicehive.websocket.model.repsonse.UserGetCurrentResponse response);

    void onUpdateCurrent(com.github.devicehive.websocket.model.repsonse.ResponseAction response);

    void onGetNetwork(com.github.devicehive.websocket.model.repsonse.UserGetNetworkResponse response);

    void onAssignNetwork(com.github.devicehive.websocket.model.repsonse.ResponseAction response);

    void onUnassignNetwork(com.github.devicehive.websocket.model.repsonse.ResponseAction response);
}
