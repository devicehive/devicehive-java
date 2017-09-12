package com.devicehive.client.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@Data
@RequiredArgsConstructor
public class FailureData {
    public static final int NO_CODE = -1;

    private int code;
    private String message;

}
