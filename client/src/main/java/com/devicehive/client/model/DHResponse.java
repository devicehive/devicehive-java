package com.devicehive.client.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DHResponse<T> {

    private T data;
    private FailureData failureData;

    public boolean isSuccessful() {
        return failureData == null;
    }

    public static <T> DHResponse<T> create(T data, FailureData failureData) {
        return new DHResponse<>(data, failureData);
    }
}
