package io.swagger.client.api;

import io.swagger.client.CollectionFormats.*;

import retrofit.Callback;
import retrofit.http.*;
import retrofit.mime.*;

import io.swagger.client.model.Network;
import io.swagger.client.model.NetworkUpdate;

import java.util.*;

public interface NetworkApi {
  
  /**
   * List networks
   * Sync method
   * Gets list of device networks the client has access to.
   * @param name Filter by network name.
   * @param namePattern Filter by network name pattern.
   * @param sortField Result list sort field.
   * @param sortOrder Result list sort order.
   * @param take Number of records to take from the result list.
   * @param skip Number of records to skip from the result list.
   * @return List<Network>
   */
  
  @GET("/network")
  List<Network> list(
    @Query("name") String name, @Query("namePattern") String namePattern, @Query("sortField") String sortField, @Query("sortOrder") String sortOrder, @Query("take") Integer take, @Query("skip") Integer skip
  );

  /**
   * List networks
   * Async method
   * @param name Filter by network name.
   * @param namePattern Filter by network name pattern.
   * @param sortField Result list sort field.
   * @param sortOrder Result list sort order.
   * @param take Number of records to take from the result list.
   * @param skip Number of records to skip from the result list.
   * @param cb callback method
   * @return void
   */
  
  @GET("/network")
  void list(
    @Query("name") String name, @Query("namePattern") String namePattern, @Query("sortField") String sortField, @Query("sortOrder") String sortOrder, @Query("take") Integer take, @Query("skip") Integer skip, Callback<List<Network>> cb
  );
  
  /**
   * Create network
   * Sync method
   * Creates new device network.
   * @param body Network body
   * @return Network
   */
  
  @POST("/network")
  Network insert(
    @Body Network body
  );

  /**
   * Create network
   * Async method
   * @param body Network body
   * @param cb callback method
   * @return void
   */
  
  @POST("/network")
  void insert(
    @Body Network body, Callback<Network> cb
  );
  
  /**
   * Get network
   * Sync method
   * Gets information about device network and its devices.
   * @param id Network identifier.
   * @return Network
   */
  
  @GET("/network/{id}")
  Network get(
    @Path("id") Long id
  );

  /**
   * Get network
   * Async method
   * @param id Network identifier.
   * @param cb callback method
   * @return void
   */
  
  @GET("/network/{id}")
  void get(
    @Path("id") Long id, Callback<Network> cb
  );
  
  /**
   * Update network
   * Sync method
   * Updates an existing device network.
   * @param body Network body
   * @param id Network identifier.
   * @return Void
   */
  
  @PUT("/network/{id}")
  Void update(
    @Body NetworkUpdate body, @Path("id") Long id
  );

  /**
   * Update network
   * Async method
   * @param body Network body
   * @param id Network identifier.
   * @param cb callback method
   * @return void
   */
  
  @PUT("/network/{id}")
  void update(
    @Body NetworkUpdate body, @Path("id") Long id, Callback<Void> cb
  );
  
  /**
   * Delete network
   * Sync method
   * Deletes an existing device network.
   * @param id Network identifier.
   * @return Void
   */
  
  @DELETE("/network/{id}")
  Void delete(
    @Path("id") Long id
  );

  /**
   * Delete network
   * Async method
   * @param id Network identifier.
   * @param cb callback method
   * @return void
   */
  
  @DELETE("/network/{id}")
  void delete(
    @Path("id") Long id, Callback<Void> cb
  );
  
}
