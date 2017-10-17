/*
 *
 *
 *   NetworkApi.java
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

package com.devicehive.websocket.api;

import com.devicehive.websocket.model.request.data.NetworkUpdate;

import javax.annotation.Nullable;

interface NetworkApi {

    void list( Long requestId, String name, String namePattern, String sortField, Boolean sortOrderAsc, Integer take, Integer skip);

    void get( Long requestId, Long id);

    void insert( Long requestId, NetworkUpdate networkUpdate);

    void update( Long requestId, NetworkUpdate networkUpdate);

    void delete( Long requestId, Long id);
}
