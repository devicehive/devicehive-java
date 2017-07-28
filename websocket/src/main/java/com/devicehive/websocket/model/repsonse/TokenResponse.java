package com.devicehive.websocket.model.repsonse;

import lombok.Data;

@Data
public class TokenResponse {

    private final JwtTokenResponse tokenVO;
    private final ErrorResponse error;

    public boolean isError() {
        return error != null;
    }
}
