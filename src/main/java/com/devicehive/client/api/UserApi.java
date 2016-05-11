package com.devicehive.client.api;


import com.devicehive.client2.model.Network;
import com.devicehive.client2.model.User;
import com.devicehive.client2.model.UserUpdate;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface UserApi {
  /**
   * Assign network
   * Associates network with the user.
   * @param id User identifier. (required)
   * @param networkId Network identifier. (required)
   * @return Call<Void>
   */
  
  @PUT("user/{id}/network/{networkId}")
  Call<Void> assignNetwork(
          @Path("id") Long id, @Path("networkId") Long networkId
  );

  /**
   * Delete user
   * Deletes an existing user.
   * @param id User identifier. (required)
   * @return Call<Void>
   */
  
  @DELETE("user/{id}")
  Call<Void> deleteUser(
          @Path("id") Long id
  );

  /**
   * Get current user
   * Get information about the current user.
   * @return Call<User>
   */
  
  @GET("user/current")
  Call<User> getCurrent();
    

  /**
   * Get user&#39;s network
   * Gets information about user/network association.
   * @param id User identifier. (required)
   * @param networkId Network identifier. (required)
   * @return Call<Network>
   */
  
  @GET("user/{id}/network/{networkId}")
  Call<Network> getNetwork(
          @Path("id") Long id, @Path("networkId") Long networkId
  );

  /**
   * Get user
   * Gets information about user and its assigned networks.\nOnly administrators are allowed to get information about any user. User-level accounts can only retrieve information about themselves.
   * @param id User identifier. (required)
   * @return Call<User>
   */
  
  @GET("user/{id}")
  Call<User> getUser(
          @Path("id") Long id
  );

  /**
   * Create user
   * Creates new user.
   * @param body User body (required)
   * @return Call<User>
   */
  
  @POST("user")
  Call<User> insertUser(
          @Body UserUpdate body
  );

  /**
   * List users
   * Gets list of users.
   * @param login Filter by user login. (optional)
   * @param loginPattern Filter by user login pattern. (optional)
   * @param role Filter by user role. 0 is Administrator, 1 is Client. (optional)
   * @param status Filter by user status. 0 is Active, 1 is Locked Out, 2 is Disabled. (optional)
   * @param sortField Result list sort field. (optional)
   * @param sortOrder Result list sort order. Available values are ASC and DESC. (optional)
   * @param take Number of records to take from the result list. (optional, default to 20)
   * @param skip Number of records to skip from the result list. (optional, default to 0)
   * @return Call<List<User>>
   */
  
  @GET("user")
  Call<List<User>> list(
          @Query("login") String login, @Query("loginPattern") String loginPattern, @Query("role") Integer role, @Query("status") Integer status, @Query("sortField") String sortField, @Query("sortOrder") String sortOrder, @Query("take") Integer take, @Query("skip") Integer skip
  );

  /**
   * Unassign network
   * Removes association between network and user.
   * @param id User identifier. (required)
   * @param networkId Network identifier. (required)
   * @return Call<Void>
   */
  
  @DELETE("user/{id}/network/{networkId}")
  Call<Void> unassignNetwork(
          @Path("id") Long id, @Path("networkId") Long networkId
  );

  /**
   * Update current user
   * Updates an existing user. \nOnly administrators are allowed to update any property of any user. User-level accounts can only change their own password in case:\n1. They already have a password.\n2. They provide a valid current password in the &#39;oldPassword&#39; property.
   * @param body User body (required)
   * @return Call<Void>
   */
  
  @PUT("user/current")
  Call<Void> updateCurrentUser(
          @Body UserUpdate body
  );

  /**
   * Update user
   * 
   * @param body User body (required)
   * @param id User identifier. (required)
   * @return Call<Void>
   */
  
  @PUT("user/{id}")
  Call<Void> updateUser(
          @Body UserUpdate body, @Path("id") Long id
  );

}
