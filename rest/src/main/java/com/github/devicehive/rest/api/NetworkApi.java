/*
 *
 *
 *   NetworkApi.java
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

import com.github.devicehive.rest.model.Network;
import com.github.devicehive.rest.model.NetworkId;
import com.github.devicehive.rest.model.NetworkUpdate;
import com.github.devicehive.rest.model.NetworkVO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface NetworkApi {
  /**
   * Delete network
   * Deletes an existing device network.
   * @param id Network identifier. (required)
   * @return Call&lt;Void&gt;
   */
  @Headers({
    "Content-Type:application/json"
  })
  @DELETE("network/{id}")
  Call<Void> delete(
          @Path("id") Long id
  );

  /**
   * Get network
   * Gets information about device network and its devices.
   * @param id Network identifier. (required)
   * @return Call&lt;NetworkVO&gt;
   */
  @Headers({
    "Content-Type:application/json"
  })
  @GET("network/{id}")
  Call<NetworkVO> get(
          @Path("id") Long id
  );

  /**
   * Create network
   * Creates new device network.
   * @param body Network body (required)
   * @return Call&lt;NetworkVO&gt;
   */
  @Headers({
    "Content-Type:application/json"
  })
  @POST("network")
  Call<NetworkId> insert(
          @Body NetworkUpdate body
  );

  /**
   * List networks
   * Gets list of device networks the client has access to.
   * @param name Filter by network name. (optional)
   * @param namePattern Filter by network name pattern. In pattern wildcards &#39;%&#39; and &#39;_&#39; can be used. (optional)
   * @param sortField Result list sort field. (optional)
   * @param sortOrder Result list sort order. The sortField should be specified. (optional)
   * @param take Number of records to take from the result list. (optional, default to 20)
   * @param skip Number of records to skip from the result list. (optional, default to 0)
   * @return Call&lt;List&lt;NetworkVO&gt;&gt;
   */
  @Headers({
    "Content-Type:application/json"
  })
  @GET("network")
  Call<List<Network>> list(
         @Query("name") String name, @Query("namePattern") String namePattern, @Query("sortField") String sortField, @Query("sortOrder") String sortOrder, @Query("take") Integer take, @Query("skip") Integer skip
  );

  /**
   * Update network
   * Updates an existing device network.
   * @param body Network body (required)
   * @param id Network identifier. (required)
   * @return Call&lt;Void&gt;
   */
  @Headers({
    "Content-Type:application/json"
  })
  @PUT("network/{id}")
  Call<Void> update(
          @Path("id") Long id, @Body NetworkUpdate body);

}
