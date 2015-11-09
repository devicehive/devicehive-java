package com.devicehive.client.api;

import retrofit.Callback;
import retrofit.http.*;

import com.devicehive.client.model.User;
import com.devicehive.client.model.UserUpdate;
import com.devicehive.client.model.Network;

import java.util.*;

public interface UserApi {
  
  /**
   * List users
   * Sync method
   * Gets list of users.
   * @param login Filter by user login.
   * @param loginPattern Filter by user login pattern.
   * @param role Filter by user role. 0 is Administrator, 1 is Client.
   * @param status Filter by user status. 0 is Active, 1 is Locked Out, 2 is Disabled.
   * @param sortField Result list sort field.
   * @param sortOrder Result list sort order. Available values are ASC and DESC.
   * @param take Number of records to take from the result list.
   * @param skip Number of records to skip from the result list.
   * @return List<User>
   */
  
  @GET("/user")
  List<User> list(
    @Query("login") String login, @Query("loginPattern") String loginPattern, @Query("role") Integer role, @Query("status") Integer status, @Query("sortField") String sortField, @Query("sortOrder") String sortOrder, @Query("take") Integer take, @Query("skip") Integer skip
  );

  /**
   * List users
   * Async method
   * @param login Filter by user login.
   * @param loginPattern Filter by user login pattern.
   * @param role Filter by user role. 0 is Administrator, 1 is Client.
   * @param status Filter by user status. 0 is Active, 1 is Locked Out, 2 is Disabled.
   * @param sortField Result list sort field.
   * @param sortOrder Result list sort order. Available values are ASC and DESC.
   * @param take Number of records to take from the result list.
   * @param skip Number of records to skip from the result list.
   * @param cb callback method
   * @return void
   */
  
  @GET("/user")
  void list(
    @Query("login") String login, @Query("loginPattern") String loginPattern, @Query("role") Integer role, @Query("status") Integer status, @Query("sortField") String sortField, @Query("sortOrder") String sortOrder, @Query("take") Integer take, @Query("skip") Integer skip, Callback<List<User>> cb
  );
  
  /**
   * Create user
   * Sync method
   * Creates new user.
   * @param body User body
   * @return User
   */
  
  @POST("/user")
  User insertUser(
    @Body UserUpdate body
  );

  /**
   * Create user
   * Async method
   * @param body User body
   * @param cb callback method
   * @return void
   */
  
  @POST("/user")
  void insertUser(
    @Body UserUpdate body, Callback<User> cb
  );
  
  /**
   * Get current user
   * Sync method
   * Get information about the current user.
   * @return User
   */
  
  @GET("/user/current")
  User getCurrent();
    

  /**
   * Get current user
   * Async method
   * @param cb callback method
   * @return void
   */
  
  @GET("/user/current")
  void getCurrent(
    Callback<User> cb
  );
  
  /**
   * Update current user
   * Sync method
   * Updates an existing user. \nOnly administrators are allowed to update any property of any user. User-level accounts can only change their own password in case:\n1. They already have a password.\n2. They provide a valid current password in the &#39;oldPassword&#39; property.
   * @param body User body
   * @return Void
   */
  
  @PUT("/user/current")
  Void updateCurrentUser(
    @Body UserUpdate body
  );

  /**
   * Update current user
   * Async method
   * @param body User body
   * @param cb callback method
   * @return void
   */
  
  @PUT("/user/current")
  void updateCurrentUser(
    @Body UserUpdate body, Callback<Void> cb
  );
  
  /**
   * Get user
   * Sync method
   * Gets information about user and its assigned networks.\nOnly administrators are allowed to get information about any user. User-level accounts can only retrieve information about themselves.
   * @param id User identifier.
   * @return User
   */
  
  @GET("/user/{id}")
  User getUser(
    @Path("id") Long id
  );

  /**
   * Get user
   * Async method
   * @param id User identifier.
   * @param cb callback method
   * @return void
   */
  
  @GET("/user/{id}")
  void getUser(
    @Path("id") Long id, Callback<User> cb
  );
  
  /**
   * Update user
   * Sync method
   * 
   * @param body User body
   * @param id User identifier.
   * @return Void
   */
  
  @PUT("/user/{id}")
  Void updateUser(
    @Body UserUpdate body, @Path("id") Long id
  );

  /**
   * Update user
   * Async method
   * @param body User body
   * @param id User identifier.
   * @param cb callback method
   * @return void
   */
  
  @PUT("/user/{id}")
  void updateUser(
    @Body UserUpdate body, @Path("id") Long id, Callback<Void> cb
  );
  
  /**
   * Delete user
   * Sync method
   * Deletes an existing user.
   * @param id User identifier.
   * @return Void
   */
  
  @DELETE("/user/{id}")
  Void deleteUser(
    @Path("id") Long id
  );

  /**
   * Delete user
   * Async method
   * @param id User identifier.
   * @param cb callback method
   * @return void
   */
  
  @DELETE("/user/{id}")
  void deleteUser(
    @Path("id") Long id, Callback<Void> cb
  );
  
  /**
   * Get user&#39;s network
   * Sync method
   * Gets information about user/network association.
   * @param id User identifier.
   * @param networkId Network identifier.
   * @return Network
   */
  
  @GET("/user/{id}/network/{networkId}")
  Network getNetwork(
    @Path("id") Long id, @Path("networkId") Long networkId
  );

  /**
   * Get user&#39;s network
   * Async method
   * @param id User identifier.
   * @param networkId Network identifier.
   * @param cb callback method
   * @return void
   */
  
  @GET("/user/{id}/network/{networkId}")
  void getNetwork(
    @Path("id") Long id, @Path("networkId") Long networkId, Callback<Network> cb
  );
  
  /**
   * Assign network
   * Sync method
   * Associates network with the user.
   * @param id User identifier.
   * @param networkId Network identifier.
   * @return Void
   */
  
  @PUT("/user/{id}/network/{networkId}")
  Void assignNetwork(
    @Path("id") Long id, @Path("networkId") Long networkId
  );

  /**
   * Assign network
   * Async method
   * @param id User identifier.
   * @param networkId Network identifier.
   * @param cb callback method
   * @return void
   */
  
  @PUT("/user/{id}/network/{networkId}")
  void assignNetwork(
    @Path("id") Long id, @Path("networkId") Long networkId, Callback<Void> cb
  );
  
  /**
   * Unassign network
   * Sync method
   * Removes association between network and user.
   * @param id User identifier.
   * @param networkId Network identifier.
   * @return Void
   */
  
  @DELETE("/user/{id}/network/{networkId}")
  Void unassignNetwork(
    @Path("id") Long id, @Path("networkId") Long networkId
  );

  /**
   * Unassign network
   * Async method
   * @param id User identifier.
   * @param networkId Network identifier.
   * @param cb callback method
   * @return void
   */
  
  @DELETE("/user/{id}/network/{networkId}")
  void unassignNetwork(
    @Path("id") Long id, @Path("networkId") Long networkId, Callback<Void> cb
  );
  
}
