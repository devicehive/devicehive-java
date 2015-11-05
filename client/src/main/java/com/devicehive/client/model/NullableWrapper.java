package com.devicehive.client.model;


import com.google.common.base.Optional;

class NullableWrapper {

    static <T> T value(Optional<T> optional) {
        return optional != null ? optional.get() : null;
    }
}
