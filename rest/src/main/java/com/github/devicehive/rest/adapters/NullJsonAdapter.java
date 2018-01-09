/*
 *
 *
 *   NullJsonAdapter.java
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

package com.github.devicehive.rest.adapters;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.util.Objects;

public class NullJsonAdapter implements JsonDeserializer<JsonObject>, JsonSerializer<JsonObject> {
    @Override
    public JsonObject deserialize(JsonElement jsonElement, Type type,
                                  JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        if (Objects.equals(type, JsonNull.class)) {
            return new JsonObject();
        }
        if (jsonElement.isJsonNull()) {
            return new JsonObject();
        }
        return jsonElement.getAsJsonObject();
    }

    @Override
    public JsonElement serialize(JsonObject jsonObject, Type type, JsonSerializationContext jsonSerializationContext) {
        return jsonSerializationContext.serialize(jsonObject);
    }
}