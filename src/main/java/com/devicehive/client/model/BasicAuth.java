package com.devicehive.client.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class BasicAuth {
    private String username;
    private String password;

    public boolean isValid() {
        return (username != null && username.length() > 0)
                && (password != null && password.length() > 0);
    }
}
