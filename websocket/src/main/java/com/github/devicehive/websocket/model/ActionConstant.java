/*
 *
 *
 *   ActionConstant.java
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

package com.github.devicehive.websocket.model;

public class ActionConstant {
    public static final String AUTHENTICATE = "authenticate";

    public static final String TOKEN_GET = "token";
    public static final String TOKEN_CREATE = "token/create";
    public static final String TOKEN_REFRESH = "token/refresh";

    public static final String COMMAND_GET = "command/get";
    public static final String COMMAND_LIST = "command/list";
    public static final String COMMAND_INSERT = "command/insert";
    public static final String COMMAND_SUBSCRIBE = "command/subscribe";
    public static final String COMMAND_UNSUBSCRIBE = "command/unsubscribe";
    public static final String COMMAND_UPDATE = "command/update";


    public static final String CONFIGURATION_DELETE = "configuration/delete";
    public static final String CONFIGURATION_GET = "configuration/get";
    public static final String CONFIGURATION_PUT = "configuration/put";

    public static final String DEVICE_GET = "device/get";
    public static final String DEVICE_DELETE = "device/delete";
    public static final String DEVICE_LIST = "device/list";
    public static final String DEVICE_SAVE = "device/save";


    public static final String NETWORK_GET = "network/get";
    public static final String NETWORK_DELETE = "network/delete";
    public static final String NETWORK_LIST = "network/list";
    public static final String NETWORK_UPDATE = "network/update";
    public static final String NETWORK_INSERT = "network/insert";

    public static final String NOTIFICATION_GET = "notification/get";
    public static final String NOTIFICATION_LIST = "notification/list";
    public static final String NOTIFICATION_INSERT = "notification/insert";
    public static final String NOTIFICATION_SUBSCRIBE = "notification/subscribe";
    public static final String NOTIFICATION_UNSUBSCRIBE = "notification/unsubscribe";

    public static final String USER_GET = "user/get";
    public static final String USER_LIST = "user/list";
    public static final String USER_INSERT = "user/insert";
    public static final String USER_UPDATE = "user/update";
    public static final String USER_DELETE = "user/delete";
    public static final String USER_GET_CURRENT = "user/getCurrent";
    public static final String USER_UPDATE_CURRENT = "user/updateCurrent";
    public static final String USER_GET_NETWORK = "user/getNetwork";
    public static final String USER_ASSIGN_NETWORK = "user/assignNetwork";
    public static final String USER_UNASSIGN_NETWORK = "user/unassignNetwork";


}
