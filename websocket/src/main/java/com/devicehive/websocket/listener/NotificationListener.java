/*
 *
 *
 *   NotificationListener.java
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

package com.devicehive.websocket.listener;

import com.devicehive.websocket.model.repsonse.*;

public class NotificationListener implements ErrorListener {

    public  void onList(NotificationListResponse response){}

    public void onGet(NotificationGetResponse response){}

    public void onInsert(NotificationInsertResponse response){}

    public void onSubscribe(NotificationSubscribeResponse response){}

    public void onUnsubscribe(ResponseAction response){}

    @Override
    public void onError(ErrorResponse error) {

    }
}
