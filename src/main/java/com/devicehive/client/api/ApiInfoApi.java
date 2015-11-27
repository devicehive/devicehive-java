package com.devicehive.client.api;

import com.devicehive.client.model.ApiConfig;
import com.devicehive.client.model.ApiInfo;
import com.devicehive.client.model.ClusterConfig;
import retrofit.Callback;
import retrofit.http.GET;

public interface ApiInfoApi {
  
  /**
   * Get API info
   * Sync method
   * Returns version of API, server timestamp and WebSocket base uri
   * @return ApiInfo
   */
  
  @GET("/info")
  ApiInfo getApiInfo();
    

  /**
   * Get API info
   * Async method
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
   * Gets information about supported authentication providers.
   * @return ApiConfig
   */
  
  @GET("/info/config/auth")
  ApiConfig getOauth2Config();
    

  /**
   * Get oAuth configuration
   * Async method
   * @param cb callback method
   * @return void
   */
  
  @GET("/info/config/auth")
  void getOauth2Config(
          Callback<ApiConfig> cb
  );
  
  /**
   * Get cluster configuration
   * Sync method
   * Returns information about cluster (Kafka, Zookeeper etc.)
   * @return ClusterConfig
   */
  
  @GET("/info/config/cluster")
  ClusterConfig getClusterConfig();
    

  /**
   * Get cluster configuration
   * Async method
   * @param cb callback method
   * @return void
   */
  
  @GET("/info/config/cluster")
  void getClusterConfig(
          Callback<ClusterConfig> cb
  );
  
}
