package com.devicehive.client.websocket.api;


import com.devicehive.client.websocket.model.AccessKey;
import com.devicehive.client.websocket.model.exceptions.HiveException;

import java.util.List;

/**
 * The API for access keys: {@code /user/{userId}/accesskey}. Transport declared in the hive context will be used.
 *
 * @see <a href="http://www.devicehive.com/restful/#Reference/AccessKey">DeviceHive RESTful API: AccessKey</a>
 */
public interface AccessKeyAPI {

    /**
     * Retrieves a list of access keys and their permissions.
     *
     * @param userId a user identifier.
     * @return If successful, this method returns an array of {@link AccessKey} resources in the response body according
     * to the specification.
     * @throws HiveException if an error occurs during the request execution
     * @see <a href="http://www.devicehive.com/restful#Reference/AccessKey/list">DeviceHive RESTful API: AccessKey: list</a>
     */
    List<AccessKey> listKeys(long userId) throws HiveException;

    /**
     * Retrieves a list of access keys and their permissions. Uses the 'current' keyword to list access keys of the current
     * user.
     *
     * @return If successful, this method returns array of {@link AccessKey} resources in the response body according to
     * the specification.
     * @throws HiveException if an error occurs during the request execution
     * @see <a href="http://www.devicehive.com/restful#Reference/AccessKey/list">DeviceHive RESTful API: AccessKey: list</a>
     */
    List<AccessKey> listKeys() throws HiveException;

    /**
     * Retrieves information about access key and its permissions.
     *
     * @param userId a user identifier.
     * @param keyId  an access key identifier.
     * @return If successful, this method returns an {@link AccessKey} resource in the response body according to the
     * specification.
     * @throws HiveException if an error occurs during the request execution
     * @see <a href="http://www.devicehive.com/restful#Reference/AccessKey/get">DeviceHive RESTful API: AccessKey: get</a>
     */
    AccessKey getKey(long userId, long keyId) throws HiveException;

    /**
     * Retrieves information about access key and its permissions. Uses the 'current' keyword to get access key of the
     * current user.
     *
     * @param keyId an access key identifier.
     * @return If successful, this method returns an {@link AccessKey} resource in the response body according to the
     * specification.
     * @throws HiveException if an error occurs during the request execution
     * @see <a href="http://www.devicehive.com/restful#Reference/AccessKey/get">DeviceHive RESTful API: AccessKey: get</a>
     */
    AccessKey getKey(long keyId) throws HiveException;

    /**
     * Creates new access key.
     *
     * @param userId a user identifier.
     * @return If successful, this method returns an {@link AccessKey} resource in the response body according to the
     * specification.
     * @throws HiveException if an error occurs during the request execution
     * @see <a href="http://www.devicehive.com/restful#Reference/AccessKey/insert">DeviceHive RESTful API: AccessKey:
     * insert</a>
     */
    AccessKey insertKey(long userId, AccessKey key) throws HiveException;

    /**
     * Creates new access key. Uses the 'current' keyword to create access key of the current user.
     *
     * @return If successful, this method returns an {@link AccessKey} resource in the response body according to the
     * specification.
     * @throws HiveException if an error occurs during the request execution
     * @see <a href="http://www.devicehive.com/restful#Reference/AccessKey/insert">DeviceHive RESTful API: AccessKey:
     * insert</a>
     */
    AccessKey insertKey(AccessKey key) throws HiveException;

    /**
     * Updates an existing access key.
     *
     * @param userId a user identifier.
     * @param key    a key to be updated
     * @throws HiveException if an error occurs during the request execution
     * @see <a href="http://www.devicehive.com/restful#Reference/AccessKey/update">DeviceHive RESTful API: AccessKey:
     * update</a>
     */
    void updateKey(long userId, AccessKey key) throws HiveException;

    /**
     * Updates an existing access key. Use the 'current' keyword to update access key of the current user.
     *
     * @param key a key to be updated
     * @throws HiveException if an error occurs during the request execution
     * @see <a href="http://www.devicehive.com/restful#Reference/AccessKey/update">DeviceHive RESTful API: AccessKey:
     * update</a>
     */
    void updateKey(AccessKey key) throws HiveException;

    /**
     * Removes an existing access key.
     *
     * @param userId a user identifier.
     * @param keyId  an access key identifier.
     * @throws HiveException if an error occurs during the request execution
     * @see <a href="http://www.devicehive.com/restful#Reference/AccessKey/delete">DeviceHive RESTful API: AccessKey:
     * delete</a>
     */
    void deleteKey(long userId, long keyId) throws HiveException;

    /**
     * Removes the current user's access key.
     *
     * @param keyId an access key identifier.
     * @throws HiveException if an error occurs during the request execution
     * @see <a href="http://www.devicehive.com/restful#Reference/AccessKey/delete">DeviceHive RESTful API: AccessKey:
     * delete</a>
     */
    void deleteKey(long keyId) throws HiveException;
}
