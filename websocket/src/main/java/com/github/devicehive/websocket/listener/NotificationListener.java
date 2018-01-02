/*
 *
 *
 *   NotificationListener.java
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

import com.github.devicehive.websocket.model.repsonse.ErrorResponse;

public interface NotificationListener extends ErrorListener {

    void onList(com.github.devicehive.websocket.model.repsonse.NotificationListResponse response);

    void onGet(com.github.devicehive.websocket.model.repsonse.NotificationGetResponse response);

    void onInsert(com.github.devicehive.websocket.model.repsonse.NotificationInsertResponse response);

    void onSubscribe(com.github.devicehive.websocket.model.repsonse.NotificationSubscribeResponse response);

    void onUnsubscribe(com.github.devicehive.websocket.model.repsonse.ResponseAction response);

    void onError(ErrorResponse error);
}
