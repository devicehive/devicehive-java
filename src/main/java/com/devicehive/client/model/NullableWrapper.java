package com.devicehive.client.model;


import com.google.common.base.Optional;

public class NullableWrapper {

    public static <T> T value(Optional<T> optional) {

        return optional != null ? optional.get() : null;
    }
}
