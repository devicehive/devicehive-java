package com.devicehive.client2.api;

import com.devicehive.client2.model.Network;
import com.devicehive.client2.model.NetworkUpdate;


import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface NetworkApi {
  /**
   * Delete network
   * Deletes an existing device network.
   * @param id Network identifier. (required)
   * @return Call<Void>
   */
  
  @DELETE("network/{id}")
  Call<Void> delete(
    @Path("id") Long id
  );

  /**
   * Get network
   * Gets information about device network and its devices.
   * @param id Network identifier. (required)
   * @return Call<Network>
   */
  
  @GET("network/{id}")
  Call<Network> get(
    @Path("id") Long id
  );

  /**
   * Create network
   * Creates new device network.
   * @param body Network body (required)
   * @return Call<Network>
   */
  
  @POST("network")
  Call<Network> insert(
    @Body Network body
  );

  /**
   * List networks
   * Gets list of device networks the client has access to.
   * @param name Filter by network name. (optional)
   * @param namePattern Filter by network name pattern. (optional)
   * @param sortField Result list sort field. (optional)
   * @param sortOrder Result list sort order. (optional)
   * @param take Number of records to take from the result list. (optional, default to 20)
   * @param skip Number of records to skip from the result list. (optional, default to 0)
   * @return Call<List<Network>>
   */
  
  @GET("network")
  Call<List<Network>> list(
    @Query("name") String name, @Query("namePattern") String namePattern, @Query("sortField") String sortField, @Query("sortOrder") String sortOrder, @Query("take") Integer take, @Query("skip") Integer skip
  );

  /**
   * Update network
   * Updates an existing device network.
   * @param body Network body (required)
   * @param id Network identifier. (required)
   * @return Call<Void>
   */
  
  @PUT("network/{id}")
  Call<Void> update(
          @Body NetworkUpdate body, @Path("id") Long id
  );

}
