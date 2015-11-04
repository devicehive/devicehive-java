package com.devicehive.client;


import com.devicehive.client.model.OAuthClient;
import com.devicehive.client.model.exceptions.HiveException;

import java.util.List;

/**
 * Client side controller for OAuth clients. Transport declared in the hive context will be used.
 *
 * @see <a href="http://devicehive.com/restful/#Reference/OAuthClient">DeviceHive RESTful API: OAuth Client</a>
 */
public interface OAuthClientController {

    /**
     * Queries OAuth clients.
     *
     * @param name        client name.
     * @param namePattern client name pattern.
     * @param domain      domain.
     * @param oauthId     OAuth client ID.
     * @param sortField   Result list sort field. Available values are ID, Name, Domain and OAuthID.
     * @param sortOrder   Result list sort order. Available values are ASC and DESC.
     * @param take        Number of records to take.
     * @param skip        Number of records to skip.
     * @return a list of {@link OAuthClient} resources
     * @throws HiveException if an error occurs during the request execution
     * @see <a href="http://devicehive.com/restful/#Reference/OAuthClient/list">DeviceHive RESTful API: OAuth Client: list</a>
     */
    List<OAuthClient> list(String name, String namePattern, String domain, String oauthId, String sortField,
                           String sortOrder, Integer take, Integer skip) throws HiveException;

    /**
     * Retrieves information about an OAuth client.
     *
     * @param id an OAuth client identifier.
     * @return an {@link OAuthClient} resource associated with requested id.
     * @throws HiveException if an error occurs during the request execution
     * @see <a href="http://devicehive.com/restful/#Reference/OAuthClient/get">DeviceHive RESTful API: OAuth Client: get</a>
     */
    OAuthClient get(long id) throws HiveException;

    /**
     * Creates a new OAuth client.
     *
     * @param client a client to be inserted
     * @return an {@link OAuthClient} resource with a client identifier and a client OAuth secret.
     * @throws HiveException if an error occurs during the request execution
     * @see <a href="http://devicehive.com/restful/#Reference/OAuthClient/insert">DeviceHive RESTful API: OAuth Client:
     * insert</a>
     */
    OAuthClient insert(OAuthClient client) throws HiveException;

    /**
     * Updates an existing OAuth client.
     *
     * @param client OAuth client resource update info.
     * @throws HiveException if an error occurs during the request execution
     * @see <a href="http://devicehive.com/restful/#Reference/OAuthClient/update">DeviceHive RESTful API: OAuth Client:
     * update</a>
     */
    void update(OAuthClient client) throws HiveException;

    /**
     * Removes an existing OAuth client.
     *
     * @param id OAuth client identifier.
     * @throws HiveException if an error occurs during the request execution
     * @see <a href="http://devicehive.com/restful/#Reference/OAuthClient/delete">DeviceHive RESTful API: OAuth Client:
     * delete</a>
     */
    void delete(long id) throws HiveException;
}
