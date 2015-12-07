package com.devicehive.client.websocket.json.adapters;


import com.google.common.base.Optional;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Adapter factory for conversion from JSON into NullableWrapper and NullableWrapper into JSON
 */
public class OptionalAdapterFactory implements TypeAdapterFactory {

    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
        if (!Optional.class.isAssignableFrom(type.getRawType())) {
            return null;
        }
        ParameterizedType parameterizedType = (ParameterizedType) type.getType();
        Type internalType = parameterizedType.getActualTypeArguments()[0];
        /**
         * Cast is checked since we check is the class assignable from type T
         */
        @SuppressWarnings("unchecked")
        TypeAdapter<T> result = (TypeAdapter<T>) new OptionalAdapter(gson, internalType);
        return result;
    }

    private static class OptionalAdapter extends TypeAdapter<Optional<?>> {

        private Type internalType;
        private Gson gson;

        private OptionalAdapter(Gson gson, Type internalType) {
            this.gson = gson;
            this.internalType = internalType;
        }

        @Override
        public void write(JsonWriter out, Optional<?> value) throws IOException {
            if (value == null) {
                out.nullValue();
            } else {
                gson.toJson(value.get(), internalType, out);
            }
        }

        @Override
        public Optional<?> read(JsonReader in) throws IOException {
            return Optional.fromNullable(gson.fromJson(in, internalType));
        }
    }
}
