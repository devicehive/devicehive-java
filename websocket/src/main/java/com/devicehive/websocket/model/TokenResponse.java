package com.devicehive.websocket.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class TokenResponse {

    private final JwtTokenVO tokenVO;
    private final ErrorAction error;

    public boolean isError() {
        return error != null;
    }
}
