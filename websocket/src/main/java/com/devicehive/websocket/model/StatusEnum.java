package com.devicehive.websocket.model;

import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

/**
 * Gets or Sets status
 */
@JsonAdapter(StatusEnum.Adapter.class)
public enum StatusEnum {
    ACTIVE(0),

    LOCKED(1),

    DISABLED(2);

    private Integer value;

    StatusEnum(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    public static StatusEnum fromValue(String text) {
        for (StatusEnum b : StatusEnum.values()) {
            if (String.valueOf(b.value).equals(text)) {
                return b;
            }
        }
        return null;
    }

    public static class Adapter extends TypeAdapter<StatusEnum> {
        @Override
        public void write(final JsonWriter jsonWriter, final StatusEnum enumeration) throws IOException {
            jsonWriter.value(enumeration.getValue());
        }

        @Override
        public StatusEnum read(final JsonReader jsonReader) throws IOException {
            Integer value = jsonReader.nextInt();
            return StatusEnum.fromValue(String.valueOf(value));
        }
    }
}
