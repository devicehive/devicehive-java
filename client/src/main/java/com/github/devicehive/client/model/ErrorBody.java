package com.github.devicehive.client.model;

public class ErrorBody {

    private String error;

    private String message;

    public String getError() {
        return error;
    }

    public ErrorBody setError(String error) {
        this.error = error;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public ErrorBody setMessage(String message) {
        this.message = message;
        return this;
    }
}
