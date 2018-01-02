/*
 *
 *
 *   ApiInfoApi.java
 *
 *   Copyright (C) 2018 DataArt
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
 */

package com.github.devicehive.rest.api;

import com.github.devicehive.rest.model.ApiInfo;
import com.github.devicehive.rest.model.ClusterConfig;
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
