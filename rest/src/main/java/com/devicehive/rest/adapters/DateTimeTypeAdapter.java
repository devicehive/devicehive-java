package com.devicehive.rest.adapters;

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
        final DateTimeFormatter formatter = DateTimeFormat.forPattern(iso_pattern).withZoneUTC();
        return formatter.parseDateTime(json.getAsString());
    }
}
