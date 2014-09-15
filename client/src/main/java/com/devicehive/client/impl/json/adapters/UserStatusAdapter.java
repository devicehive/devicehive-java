package com.devicehive.client.impl.json.adapters;


import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import com.devicehive.client.impl.util.Messages;
import com.devicehive.client.model.UserStatus;

import java.io.IOException;

public class UserStatusAdapter extends TypeAdapter<UserStatus> {

    @Override
    public void write(JsonWriter out, UserStatus value) throws IOException {
        if (value == null) {
            out.nullValue();
        } else {
            out.value(value.getValue());
        }
    }

    @Override
    public UserStatus read(JsonReader in) throws IOException {
        JsonToken jsonToken = in.peek();
        if (jsonToken == JsonToken.NULL) {
            in.nextNull();
            return null;
        } else {
            try {
                return UserStatus.values()[in.nextInt()];
            } catch (RuntimeException e) {
                throw new IOException(Messages.INVALID_USER_STATUS, e);
            }
        }
    }
}
