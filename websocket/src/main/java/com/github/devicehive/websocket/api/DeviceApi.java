/*
 *
 *
 *   DeviceApi.java
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

package com.github.devicehive.websocket.api;

interface DeviceApi {


    void get( Long requestId, String deviceId);

    void list( Long requestId, String name, String namePattern, Long networkId,
              String networkName, String sortField, String sortOrder, int take, int skip);

    void save( Long requestId, com.github.devicehive.websocket.model.repsonse.data.DeviceVO device);

    void delete( Long requestId,  String deviceId);


}
