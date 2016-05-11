package com.devicehive.client.api;


import com.devicehive.client.model.ApiConfig;
import com.devicehive.client.model.ApiInfo;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInfoApi {
  /**
   * Get API info
   * Returns version of API, server timestamp and WebSocket base uri
   * @return Call<ApiInfo>
   */
  
  @GET("info")
  Call<ApiInfo> getApiInfo();
    

  /**
   * Get cluster configuration
   * Returns information about cluster (Kafka, Zookeeper etc.)
   * @return Call<Void>
   */
  
  @GET("info/config/cluster")
  Call<ApiConfig> getClusterConfig();
    

  /**
   * Get oAuth configuration
   * Returns configured identity providers
   * @return Call<Void>
   */
  
  @GET("info/config/auth")
  Call<Void> getOauth2Config();
    

}
