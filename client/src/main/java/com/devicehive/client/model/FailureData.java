package com.devicehive.client.model;

import com.devicehive.websocket.model.repsonse.ErrorResponse;
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

    public static FailureData create(ErrorResponse response) {
        return new FailureData(response.getCode(), response.getError());
    }

}
