package io.swagger.client.api;

import io.swagger.client.model.ApiInfoResponse;
import retrofit.Callback;
import retrofit.http.GET;

public interface ApiInfoApi {
  
  /**
   * Get API info
   * Sync method
   * Returns version of API, server timestamp and WebSocket base uri
   * @return Void
   */
  
  @GET("/info")
  ApiInfoResponse getApiInfo();
    

  /**
   * Get API info
   * Async method
   * @param cb callback method
   * @return void
   */
  
  @GET("/info")
  void getApiInfo(
    Callback<ApiInfoResponse> cb
  );
  
  /**
   * Get oAuth configuration
   * Sync method
   * Returns configured identity providers
   * @return Void
   */
  
  @GET("/info/config/auth")
  Void getOauth2Config();
    

  /**
   * Get oAuth configuration
   * Async method
   * @param cb callback method
   * @return void
   */
  
  @GET("/info/config/auth")
  void getOauth2Config(
    Callback<Void> cb
  );
  
  /**
   * Get cluster configuration
   * Sync method
   * Returns information about cluster (Kafka, Zookeeper etc.)
   * @return Void
   */
  
  @GET("/info/config/cluster")
  Void getClusterConfig();
    

  /**
   * Get cluster configuration
   * Async method
   * @param cb callback method
   * @return void
   */
  
  @GET("/info/config/cluster")
  void getClusterConfig(
    Callback<Void> cb
  );
  
}
