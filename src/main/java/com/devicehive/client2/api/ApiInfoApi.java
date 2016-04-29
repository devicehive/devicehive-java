package com.devicehive.client2.api;


import retrofit2.Call;
import retrofit2.http.*;

public interface ApiInfoApi {
  /**
   * Get API info
   * Returns version of API, server timestamp and WebSocket base uri
   * @return Call<Void>
   */
  
  @GET("info")
  Call<Void> getApiInfo();
    

  /**
   * Get cluster configuration
   * Returns information about cluster (Kafka, Zookeeper etc.)
   * @return Call<Void>
   */
  
  @GET("info/config/cluster")
  Call<Void> getClusterConfig();
    

  /**
   * Get oAuth configuration
   * Returns configured identity providers
   * @return Call<Void>
   */
  
  @GET("info/config/auth")
  Call<Void> getOauth2Config();
    

}
