package com.devicehive.client.model;

import lombok.Data;

@Data
public class TokenAuth {

    private String refreshToken;
    private String accessToken;

}
