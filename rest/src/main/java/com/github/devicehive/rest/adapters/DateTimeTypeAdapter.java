/*
 *
 *
 *   DateTimeTypeAdapter.java
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

import com.google.gson.*;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.lang.reflect.Type;

public class DateTimeTypeAdapter implements JsonSerializer<DateTime>, JsonDeserializer<DateTime> {

    private final String iso_pattern;

    public DateTimeTypeAdapter(String iso_pattern) {
        this.iso_pattern = iso_pattern;
    }

    @Override
    public JsonElement serialize(DateTime json,
                                 Type typeOfSrc,
                                 JsonSerializationContext context) {
        final DateTimeFormatter formatter = DateTimeFormat.forPattern(iso_pattern).withZoneUTC();
        return new JsonPrimitive(formatter.print(json));
    }

    @Override
    public DateTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

        if (json.getAsString() == null || json.getAsString().isEmpty()) {
            return null;
        }

        final DateTimeFormatter formatter = DateTimeFormat.forPattern(iso_pattern).withZoneUTC();
        return formatter.parseDateTime(json.getAsString());
    }
}
