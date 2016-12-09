package com.devicehive.client.api;

import com.devicehive.client.model.ApiConfigVO;
import com.devicehive.client.model.ApiInfoVO;
import com.devicehive.client.model.ClusterConfigVO;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInfoVOApi {
  /**
   * Get API info
   * Returns version of API, server timestamp and WebSocket base uri
   * @return Call&lt;ApiInfoVO&gt;
   */
  
  @GET("info")
  Call<ApiInfoVO> getApiInfo();
    

  /**
   * Get cluster configuration
   * Returns information about cluster (Kafka, Zookeeper etc.)
   * @return Call&lt;ClusterConfigVO&gt;
   */
  
  @GET("info/config/cluster")
  Call<ClusterConfigVO> getClusterConfig();
    

  /**
   * Get oAuth configuration
   * Gets information about supported authentication providers.
   * @return Call&lt;ApiConfigVO&gt;
   */
  
  @GET("info/config/auth")
  Call<ApiConfigVO> getOauth2Config();
    

}
