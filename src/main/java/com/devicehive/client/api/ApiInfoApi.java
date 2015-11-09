package com.devicehive.client.api;

import com.devicehive.client.model.ApiInfo;
import com.devicehive.client.model.ApiInfoAuth;
import com.devicehive.client.model.ApiInfoCluster;
import retrofit.Callback;
import retrofit.http.GET;

public interface ApiInfoApi {

    /**
     * Get API info
     * Sync method
     * Returns version of API, server timestamp and WebSocket base uri
     *
     * @return Void
     */

    @GET("/info")
    ApiInfo getApiInfo();


    /**
     * Get API info
     * Async method
     *
     * @param cb callback method
     * @return void
     */

    @GET("/info")
    void getApiInfo(
            Callback<ApiInfo> cb
    );

    /**
     * Get oAuth configuration
     * Sync method
     * Returns configured identity providers
     *
     * @return Void
     */

    @GET("/info/config/auth")
    ApiInfoAuth getOauth2Config();


    /**
     * Get oAuth configuration
     * Async method
     *
     * @param cb callback method
     * @return void
     */

    @GET("/info/config/auth")
    void getOauth2Config(
            Callback<ApiInfoAuth> cb
    );

    /**
     * Get cluster configuration
     * Sync method
     * Returns information about cluster (Kafka, Zookeeper etc.)
     *
     * @return Void
     */

    @GET("/info/config/cluster")
    ApiInfoCluster getClusterConfig();


    /**
     * Get cluster configuration
     * Async method
     *
     * @param cb callback method
     * @return void
     */

    @GET("/info/config/cluster")
    void getClusterConfig(
            Callback<ApiInfoCluster> cb
    );

}
