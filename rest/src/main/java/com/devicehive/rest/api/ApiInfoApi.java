package com.devicehive.rest.api;

import com.devicehive.rest.model.ApiInfo;
import com.devicehive.rest.model.ClusterConfig;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;


public interface ApiInfoApi {
  /**
   * Get API info
   * Returns version of API, server timestamp and WebSocket base uri
   * @return Call&lt;ApiInfo&gt;
   */
  @Headers({
    "Content-Type:application/json"
  })
  @GET("info")
  Call<ApiInfo> getApiInfo();
    

  /**
   * Get cluster configuration
   * Returns information about cluster (Kafka, Zookeeper etc.)
   * @return Call&lt;ClusterConfig&gt;
   */
  @Headers({
    "Content-Type:application/json"
  })
  @GET("info/config/cluster")
  Call<ClusterConfig> getClusterConfig();
    

}
