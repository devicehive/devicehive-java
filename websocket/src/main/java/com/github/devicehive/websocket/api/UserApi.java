/*
 *
 *
 *   UserApi.java
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

import com.github.devicehive.rest.model.*;

interface UserApi {

    void list(Long requestId, String login, String loginPattern, StatusEnum status, RoleEnum role,
              String sortField, SortOrder sortOrder, Integer take, Integer skip);

    void get( Long requestId, Long userId);

    void insert( Long requestId, UserUpdate user);

    void update( Long requestId, Long userId, UserUpdate user);

    void delete( Long requestId, Long userId);

    void getCurrent( Long requestId);

    void updateCurrent( Long requestId, UserUpdate user);

    void getNetwork( Long requestId, Long userId, Long networkId);

    void assignNetwork( Long requestId, Long userId, Long networkId);

    void unassignNetwork( Long requestId, Long userId, Long networkId);

}
