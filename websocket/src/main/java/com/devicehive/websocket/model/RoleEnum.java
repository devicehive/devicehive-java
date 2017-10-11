package com.devicehive.websocket.model;

import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

/**
 * Gets or Sets role
 */
@JsonAdapter(RoleEnum.Adapter.class)
public enum RoleEnum {
    ADMIN(0),

    CLIENT(1);

    private Integer value;

    RoleEnum(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    public static RoleEnum fromValue(String text) {
        for (RoleEnum b : RoleEnum.values()) {
            if (String.valueOf(b.value).equals(text)) {
                return b;
            }
        }
        return null;
    }

    public static class Adapter extends TypeAdapter<RoleEnum> {
        @Override
        public void write(final JsonWriter jsonWriter, final RoleEnum enumeration) throws IOException {
            jsonWriter.value(enumeration.getValue());
        }

        @Override
        public RoleEnum read(final JsonReader jsonReader) throws IOException {
            Integer value = jsonReader.nextInt();
            return RoleEnum.fromValue(String.valueOf(value));
        }
    }
}
