package com.devicehive.rest.api;

import com.devicehive.rest.model.ApiInfoVO;
import com.devicehive.rest.model.ClusterConfigVO;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;


public interface ApiInfoVOApi {
  /**
   * Get API info
   * Returns version of API, server timestamp and WebSocket base uri
   * @return Call&lt;ApiInfoVO&gt;
   */
  @Headers({
    "Content-Type:application/json"
  })
  @GET("info")
  Call<ApiInfoVO> getApiInfo();
    

  /**
   * Get cluster configuration
   * Returns information about cluster (Kafka, Zookeeper etc.)
   * @return Call&lt;ClusterConfigVO&gt;
   */
  @Headers({
    "Content-Type:application/json"
  })
  @GET("info/config/cluster")
  Call<ClusterConfigVO> getClusterConfig();
    

}
