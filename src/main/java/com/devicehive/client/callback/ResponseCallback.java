package com.devicehive.client.callback;

import com.devicehive.client.model.DHResponse;

public interface ResponseCallback<T> {

    void onResponse(DHResponse<T> response);
}
