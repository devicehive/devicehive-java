package com.devicehive.client.callback;

import com.devicehive.client.model.DHResponse;
import com.devicehive.rest.model.ApiInfo;

public interface ApiInfoCallback {

    void apiInfo(DHResponse<ApiInfo> apiInfo);
}
