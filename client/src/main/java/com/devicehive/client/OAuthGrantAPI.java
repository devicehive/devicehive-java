package com.devicehive.client;


import com.devicehive.client.model.AccessType;
import com.devicehive.client.model.OAuthGrant;
import com.devicehive.client.model.OAuthType;
import com.devicehive.client.model.exceptions.HiveException;

import java.util.Date;
import java.util.List;

/**
 * Client side controller for OAuth grants. Transport declared in the hive context will be used.
 *
 * @see <a href="http://devicehive.com/restful/#Reference/OAuthGrant">DeviceHive RESTful API: OAuth Grant</a>
 */
public interface OAuthGrantAPI {

    /**
     * Queries OAuth grants.
     *
     * @param userId        User identifier.
     * @param start         grant start timestamp (UTC).
     * @param end           grant end timestamp (UTC).
     * @param clientOauthId OAuth client OAuth identifier.
     * @param type          OAuth grant type.
     * @param scope         OAuth scope.
     * @param redirectUri   OAuth redirect URI.
     * @param accessType    access type.
     * @param sortField     Result list sort field. Available values are Date (default).
     * @param sortOrder     Result list sort order. Available values are ASC and DESC
     * @param take          Number of records to take
     * @param skip          Number of records to skip
     * @return a list of {@link OAuthGrant} resources
     * @throws HiveException if an error occurs during the request execution
     * @see <a href="http://devicehive.com/restful/#Reference/OAuthGrant/list">DeviceHive RESTful API: OAuth Grant: list</a>
     */
    List<OAuthGrant> list(long userId, Date start, Date end, String clientOauthId, OAuthType type,
                          String scope, String redirectUri, AccessType accessType, String sortField, String sortOrder,
                          Integer take, Integer skip) throws HiveException;

    /**
     * Queries OAuth grants of the current user.
     *
     * @param start         grant start timestamp (UTC).
     * @param end           grant end timestamp (UTC).
     * @param clientOauthId OAuth client OAuth identifier.
     * @param type          OAuth grant type.
     * @param scope         OAuth scope.
     * @param redirectUri   OAuth redirect URI.
     * @param accessType    access type.
     * @param sortField     Result list sort field. Available values are Date (default).
     * @param sortOrder     Result list sort order. Available values are ASC and DESC
     * @param take          Number of records to take
     * @param skip          Number of records to skip
     * @return a list of {@link OAuthGrant} resources
     * @throws HiveException if an error occurs during the request execution
     * @see <a href="http://devicehive.com/restful/#Reference/OAuthGrant/list">DeviceHive RESTful API: OAuth Grant: list</a>
     */
    List<OAuthGrant> list(Date start, Date end, String clientOauthId, OAuthType type,
                          String scope, String redirectUri, AccessType accessType, String sortField, String sortOrder,
                          Integer take, Integer skip) throws HiveException;

    /**
     * Retrieves information about an OAuth grant.
     *
     * @param userId  a user identifier
     * @param grantId a grant identifier
     * @return an {@link OAuthGrant} resource associated with requested id.
     * @throws HiveException if an error occurs during the request execution
     * @see <a href="http://devicehive.com/restful/#Reference/OAuthGrant/get">DeviceHive RESTful API: OAuth Grant: get</a>
     */
    OAuthGrant get(long userId, long grantId) throws HiveException;

    /**
     * Retrieves information about an OAuth grant of the current user.
     *
     * @param grantId a grant identifier
     * @return an {@link OAuthGrant} resource associated with the requested id.
     * @throws HiveException if an error occurs during the request execution
     * @see <a href="http://devicehive.com/restful/#Reference/OAuthGrant/get">DeviceHive RESTful API: OAuth Grant: get</a>
     */
    OAuthGrant get(long grantId) throws HiveException;

    /**
     * Creates a new OAuth grant.
     *
     * @param userId a user identifier
     * @param grant  a grant to be created
     * @return a created {@link OAuthGrant} resource
     * @throws HiveException if an error occurs during the request execution
     * @see <a href="http://devicehive.com/restful/#Reference/OAuthGrant/insert">DeviceHive RESTful API: OAuth Grant:
     * insert</a>
     */
    OAuthGrant insert(long userId, OAuthGrant grant) throws HiveException;

    /**
     * Creates a new OAuth grant for the current user.
     *
     * @param grant a grant to be created
     * @return a created {@link OAuthGrant} resource
     * @throws HiveException if an error occurs during the request execution
     * @see <a href="http://devicehive.com/restful/#Reference/OAuthGrant/insert">DeviceHive RESTful API: OAuth Grant:
     * insert</a>
     */
    OAuthGrant insert(OAuthGrant grant) throws HiveException;

    /**
     * Updates an existing OAuth grant.
     *
     * @param userId User identifier
     * @param grant  grant resource providing update info
     * @return the updated {@link OAuthGrant} resource
     * @throws HiveException if an error occurs during the request execution
     * @see <a href="http://devicehive.com/restful/#Reference/OAuthGrant/update">DeviceHive RESTful API: OAuth Grant:
     * update</a>
     */
    OAuthGrant update(long userId, OAuthGrant grant) throws HiveException;

    /**
     * Updates an existing OAuth grant of the current user.
     *
     * @param grant a grant resource with updated info
     * @return the updated {@link OAuthGrant}
     * @throws HiveException if an error occurs during the request execution
     * @see <a href="http://devicehive.com/restful/#Reference/OAuthGrant/update">DeviceHive RESTful API: OAuth Grant:
     * update</a>
     */
    OAuthGrant update(OAuthGrant grant) throws HiveException;

    /**
     * Removes an existing OAuth grant.
     *
     * @param userId  a user identifier
     * @param grantId a grant identifier
     * @throws HiveException if an error occurs during the request execution
     * @see <a href="http://devicehive.com/restful/#Reference/OAuthGrant/delete">DeviceHive RESTful API: OAuth Grant:
     * delete</a>
     */
    void delete(long userId, long grantId) throws HiveException;

    /**
     * Removes an existing OAuth grant of the current user.
     *
     * @param grantId a grant identifier
     * @throws HiveException if an error occurs during the request execution
     * @see <a href="http://devicehive.com/restful/#Reference/OAuthGrant/delete">DeviceHive RESTful API: OAuth Grant:
     * delete</a>
     */
    void delete(long grantId) throws HiveException;
}
