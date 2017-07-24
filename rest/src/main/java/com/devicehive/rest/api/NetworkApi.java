package com.devicehive.rest.api;

import com.devicehive.rest.model.NetworkUpdate;
import com.devicehive.rest.model.NetworkVO;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;


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
   * @param authorization Authorization token (required)
   * @return Call&lt;NetworkVO&gt;
   */
  @Headers({
    "Content-Type:application/json"
  })
  @POST("network")
  Call<NetworkVO> insert(
          @Body NetworkVO body, @Header("Authorization") String authorization
  );

  /**
   * List networks
   * Gets list of device networks the client has access to.
   * @param authorization Authorization token (required)
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
  Call<List<NetworkVO>> list(
         @Query("name") String name, @Query("namePattern") String namePattern, @Query("sortField") String sortField, @Query("sortOrder") String sortOrder, @Query("take") Integer take, @Query("skip") Integer skip
  );

  /**
   * Update network
   * Updates an existing device network.
   * @param body Network body (required)
   * @param id Network identifier. (required)
   * @param authorization Authorization token (required)
   * @return Call&lt;Void&gt;
   */
  @Headers({
    "Content-Type:application/json"
  })
  @PUT("network/{id}")
  Call<Void> update(
          @Body NetworkUpdate body, @Path("id") Long id, @Header("Authorization") String authorization
  );

}
