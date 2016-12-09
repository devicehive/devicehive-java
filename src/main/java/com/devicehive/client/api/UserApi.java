package com.devicehive.client.api;

import com.devicehive.client.model.NetworkVO;
import com.devicehive.client.model.UserUpdate;
import com.devicehive.client.model.UserVO;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface UserApi {
  /**
   * Assign network
   * Associates network with the user.
   * @param id User identifier. (required)
   * @param networkId Network identifier. (required)
   * @return Call&lt;Void&gt;
   */

  @PUT("user/{id}/network/{networkId}")
  Call<Void> assignNetwork(
          @Path("id") Long id, @Path("networkId") Long networkId
  );

  /**
   * Delete user
   * Deletes an existing user.
   * @param id User identifier. (required)
   * @return Call&lt;Void&gt;
   */

  @DELETE("user/{id}")
  Call<Void> deleteUser(
          @Path("id") Long id
  );

  /**
   * Get current user
   * Get information about the current user.
   * @return Call&lt;UserVO&gt;
   */

  @GET("user/current")
  Call<UserVO> getCurrent();


  /**
   * Get user&#39;s network
   * Gets information about user/network association.
   * @param id User identifier. (required)
   * @param networkId Network identifier. (required)
   * @return Call&lt;NetworkVO&gt;
   */

  @GET("user/{id}/network/{networkId}")
  Call<NetworkVO> getNetwork(
          @Path("id") Long id, @Path("networkId") Long networkId
  );

  /**
   * Get user
   * Gets information about user and its assigned networks. Only administrators are allowed to get information about any user. User-level accounts can only retrieve information about themselves.
   * @param id User identifier. (required)
   * @return Call&lt;UserVO&gt;
   */

  @GET("user/{id}")
  Call<UserVO> getUser(
          @Path("id") Long id
  );

  /**
   * Create user
   * Creates new user.
   * @param body User body (required)
   * @return Call&lt;UserVO&gt;
   */

  @POST("user")
  Call<UserVO> insertUser(
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
   * @return Call&lt;List<UserVO>&gt;
   */

  @GET("user")
  Call<List<UserVO>> list(
          @Query("login") String login, @Query("loginPattern") String loginPattern, @Query("role") Integer role, @Query("status") Integer status, @Query("sortField") String sortField, @Query("sortOrder") String sortOrder, @Query("take") Integer take, @Query("skip") Integer skip
  );

  /**
   * Unassign network
   * Removes association between network and user.
   * @param id User identifier. (required)
   * @param networkId Network identifier. (required)
   * @return Call&lt;Void&gt;
   */

  @DELETE("user/{id}/network/{networkId}")
  Call<Void> unassignNetwork(
          @Path("id") Long id, @Path("networkId") Long networkId
  );

  /**
   * Update current user
   * Updates an existing user.  Only administrators are allowed to update any property of any user. User-level accounts can only change their own password in case: 1. They already have a password. 2. They provide a valid current password in the &#39;oldPassword&#39; property.
   * @param body User body (required)
   * @return Call&lt;Void&gt;
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
   * @return Call&lt;Void&gt;
   */

  @PUT("user/{id}")
  Call<Void> updateUser(
          @Body UserUpdate body, @Path("id") Long id
  );

}
