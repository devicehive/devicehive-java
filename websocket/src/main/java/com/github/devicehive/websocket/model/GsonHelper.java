/*
 *
 *
 *   GsonHelper.java
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

import com.github.devicehive.rest.adapters.DateTimeTypeAdapter;
import com.github.devicehive.websocket.Const;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.joda.time.DateTime;

public class GsonHelper {

    private final Gson gson;

    private GsonHelper() {
        gson = new GsonBuilder()
                .registerTypeAdapter(DateTime.class, new DateTimeTypeAdapter(Const.TIMESTAMP_FORMAT))
                .create();
    }

    private static class InstanceHolder {
        static final GsonHelper INSTANCE = new GsonHelper();
    }

    public static GsonHelper getInstance() {
        return GsonHelper.InstanceHolder.INSTANCE;
    }

    public Gson getGsonFactory() {
        return gson;
    }
}
