package com.devicehive.client;


import com.devicehive.client.model.User;
import com.devicehive.client.model.UserNetwork;
import com.devicehive.client.model.exceptions.HiveException;

import java.util.List;

/**
 * The API for user: {@code /user}. Transport declared in the hive context will be used.
 *
 * @see <a href="http://www.devicehive.com/restful/#Reference/User">DeviceHive RESTful API: User</a>
 */
public interface UserAPI {

    /**
     * Queries a list of users using following criteria.
     *
     * @param login        user login ignored, when loginPattern is specified
     * @param loginPattern login pattern (LIKE %VALUE%) user login will be ignored, if not null
     * @param role         User's role ADMIN - 0, CLIENT - 1
     * @param status       ACTIVE - 0 (normal state, user can log on) , LOCKED_OUT - 1 (locked for multiple log in
     *                     failures), DISABLED - 2 , DELETED - 3;
     * @param sortField    either of "login", "loginAttempts", "role", "status", "lastLogin"
     * @param sortOrder    either ASC or DESC
     * @param take         Number of records to take
     * @param skip         Number of records to skip
     * @return a list of {@link User} resources
     * @throws HiveException if an error occurs during the request execution
     * @see <a href="http://www.devicehive.com/restful#Reference/User/list">DeviceHive RESTful API: User: list</a>
     */
    List<User> listUsers(String login, String loginPattern, Integer role, Integer status, String sortField,
                         String sortOrder, Integer take, Integer skip) throws HiveException;

    /**
     * Retrieves information about a user.
     *
     * @param id a user identifier
     * @return a {@link User} resource associated with the request identifier
     * @throws HiveException if an error occurs during the request execution
     * @see <a href="http://www.devicehive.com/restful#Reference/User/get">DeviceHive RESTful API: User: get</a>
     */
    User getUser(long id) throws HiveException;

    /**
     * Retrieves information about the current user.
     *
     * @return the current {@link User}
     * @throws HiveException if an error occurs during the request execution
     * @see <a href="http://www.devicehive.com/restful#Reference/User/get">DeviceHive RESTful API: User: get</a>
     */
    User getCurrent() throws HiveException;

    /**
     * Creates a new user.
     *
     * @param user a user to be inserted
     * @return a {@link User} resource with id and the last login timestamp
     * @throws HiveException if an error occurs during the request execution
     * @see <a href="http://www.devicehive.com/restful#Reference/User/insert">DeviceHive RESTful API: User: insert</a>
     */
    User insertUser(User user) throws HiveException;

    /**
     * Updates an existing user.
     *
     * @param user the {@link User} resource with the updated info
     * @throws HiveException if an error occurs during the request execution
     * @see <a href="http://www.devicehive.com/restful#Reference/User/update">DeviceHive RESTful API: User: update</a>
     */
    void updateUser(User user) throws HiveException;

    /**
     * Updates the current user.
     *
     * @param user the {@link User} resource with the update info
     * @throws HiveException if an error occurs during the request execution
     * @see <a href="http://www.devicehive.com/restful#Reference/User/update">DeviceHive RESTful API: User: update</a>
     */
    void updateCurrent(User user) throws HiveException;

    /**
     * Removes an existing user.
     *
     * @param id a user identifier
     * @throws HiveException if an error occurs during the request execution
     * @see <a href="http://www.devicehive.com/restful#Reference/User/delete">DeviceHive RESTful API: User: delete</a>
     */
    void deleteUser(long id) throws HiveException;

    /**
     * Retrieves information about a user/network association.
     *
     * @param userId    a user identifier
     * @param networkId a network identifier
     * @return If successful, this method returns a {@link UserNetwork} association.
     * @throws HiveException if an error occurs during the request execution
     * @see <a href="http://www.devicehive.com/restful#Reference/User/getNetwork">DeviceHive RESTful API: User: getNetwork</a>
     */
    UserNetwork getNetwork(long userId, long networkId) throws HiveException;

    /**
     * Associates a network with a user.
     *
     * @param userId    a user identifier
     * @param networkId a network identifier
     * @throws HiveException if an error occurs during the request execution
     * @see <a href="http://www.devicehive.com/restful#Reference/User/assignNetwork">DeviceHive RESTful API: User:
     * assignNetwork</a>
     */
    void assignNetwork(long userId, long networkId) throws HiveException;

    /**
     * Breaks association between a network and a user.
     *
     * @param userId    a user identifier
     * @param networkId a network identifier
     * @throws HiveException if an error occurs during the request execution
     * @see <a href="http://www.devicehive.com/restful#Reference/User/unassignNetwork">DeviceHive RESTful API: User:
     * unassignNetwork</a>
     */
    void unassignNetwork(long userId, long networkId) throws HiveException;
}
