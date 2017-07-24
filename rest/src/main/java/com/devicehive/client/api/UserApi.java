package com.devicehive.client.api;

import com.devicehive.client.model.*;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.*;

import java.util.List;


public interface UserApi {
  /**
   * Assign network
   * Associates network with the user.
   * @param id User identifier. (required)
   * @param networkId Network identifier. (required)
   * @param authorization Authorization token (required)
   * @return Call&lt;Void&gt;
   */
  @Headers({
    "Content-Type:application/json"
  })
  @PUT("user/{id}/network/{networkId}")
  Call<Void> assignNetwork(
          @Path("id") Long id, @Path("networkId") Long networkId, @Header("Authorization") String authorization
  );

  /**
   * Delete user
   * Deletes an existing user.
   * @param id User identifier. (required)
   * @param authorization Authorization token (required)
   * @return Call&lt;Void&gt;
   */
  @Headers({
    "Content-Type:application/json"
  })
  @DELETE("user/{id}")
  Call<Void> deleteUser(
          @Path("id") Long id, @Header("Authorization") String authorization
  );

  /**
   * Get current user
   * Get information about the current user.
   * @param authorization Authorization token (required)
   * @return Call&lt;UserWithNetworkVO&gt;
   */
  @Headers({
    "Content-Type:application/json"
  })
  @GET("user/current")
  Call<UserWithNetworkVO> getCurrent(
          @Header("Authorization") String authorization
  );

  /**
   * Get user&#39;s network
   * Gets information about user/network association.
   * @param id User identifier. (required)
   * @param networkId Network identifier. (required)
   * @param authorization Authorization token (required)
   * @return Call&lt;UserNetworkResponse&gt;
   */
  @Headers({
    "Content-Type:application/json"
  })
  @GET("user/{id}/network/{networkId}")
  Call<UserNetworkResponse> getNetwork(
          @Path("id") Long id, @Path("networkId") Long networkId, @Header("Authorization") String authorization
  );

  /**
   * Get user
   * Gets information about user and its assigned networks. Only administrators are allowed to get information about any user. User-level accounts can only retrieve information about themselves.
   * @param id User identifier. (required)
   * @param authorization Authorization token (required)
   * @return Call&lt;UserResponse&gt;
   */
  @Headers({
    "Content-Type:application/json"
  })
  @GET("user/{id}")
  Call<UserResponse> getUser(
          @Path("id") Long id, @Header("Authorization") String authorization
  );

  /**
   * Create user
   * Creates new user.
   * @param body User body (required)
   * @param authorization Authorization token (required)
   * @return Call&lt;UserVO&gt;
   */
  @Headers({
    "Content-Type:application/json"
  })
  @POST("user")
  Call<UserVO> insertUser(
          @Body UserUpdate body, @Header("Authorization") String authorization
  );

  /**
   * List users
   * Gets list of users.
   * @param authorization Authorization token (required)
   * @param login Filter by user login. (optional)
   * @param loginPattern Filter by user login pattern. (optional)
   * @param role Filter by user role. 0 is Administrator, 1 is Client. (optional)
   * @param status Filter by user status. 0 is Active, 1 is Locked Out, 2 is Disabled. (optional)
   * @param sortField Result list sort field. (optional)
   * @param sortOrder Result list sort order. The sortField should be specified. (optional)
   * @param take Number of records to take from the result list. (optional, default to 20)
   * @param skip Number of records to skip from the result list. (optional, default to 0)
   * @return Call&lt;List&lt;UserVO&gt;&gt;
   */
  @Headers({
    "Content-Type:application/json"
  })
  @GET("user")
  Call<List<UserVO>> list(
          @Header("Authorization") String authorization, @Query("login") String login, @Query("loginPattern") String loginPattern, @Query("role") Integer role, @Query("status") Integer status, @Query("sortField") String sortField, @Query("sortOrder") String sortOrder, @Query("take") Integer take, @Query("skip") Integer skip
  );

  /**
   * Unassign network
   * Removes association between network and user.
   * @param id User identifier. (required)
   * @param networkId Network identifier. (required)
   * @param authorization Authorization token (required)
   * @return Call&lt;Void&gt;
   */
  @Headers({
    "Content-Type:application/json"
  })
  @DELETE("user/{id}/network/{networkId}")
  Call<Void> unassignNetwork(
          @Path("id") Long id, @Path("networkId") Long networkId, @Header("Authorization") String authorization
  );

  /**
   * Update current user
   * Updates an existing user.  Only administrators are allowed to update any property of any user. User-level accounts can only change their own password in case: 1. They already have a password. 2. They provide a valid current password in the &#39;oldPassword&#39; property.
   * @param body User body (required)
   * @param authorization Authorization token (required)
   * @return Call&lt;Void&gt;
   */
  @Headers({
    "Content-Type:application/json"
  })
  @PUT("user/current")
  Call<Void> updateCurrentUser(
          @Body UserUpdate body, @Header("Authorization") String authorization
  );

  /**
   * Update user
   *
   * @param body User body (required)
   * @param id User identifier. (required)
   * @param authorization Authorization token (required)
   * @return Call&lt;Void&gt;
   */
  @Headers({
    "Content-Type:application/json"
  })
  @PUT("user/{id}")
  Call<Void> updateUser(
          @Body UserUpdate body, @Path("id") Long id, @Header("Authorization") String authorization
  );

}
