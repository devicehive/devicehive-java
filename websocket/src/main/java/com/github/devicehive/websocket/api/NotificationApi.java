/*
 *
 *
 *   NotificationApi.java
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

package com.github.devicehive.websocket.api;

import com.github.devicehive.rest.model.DeviceNotificationWrapper;
import com.github.devicehive.rest.model.SortOrder;
import org.joda.time.DateTime;

import java.util.List;

interface NotificationApi {

    void list(Long requestId, String deviceId, String notification, DateTime start, DateTime end,
              String sortField, SortOrder sortOrder, int take, int skip);

    void get(Long requestId, String deviceId, Long notificationId);

    void insert(Long requestId, String deviceId, DeviceNotificationWrapper notification);

    void subscribe(Long requestId, String deviceId, List<String> deviceIds, List<String> names);

    void unsubscribe(Long requestId, String subscriptionId, List<String> deviceIds);
}
