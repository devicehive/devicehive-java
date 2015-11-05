package com.devicehive.client.impl;


import com.devicehive.client.UserAPI;
import com.devicehive.client.impl.context.RestAgent;
import com.devicehive.client.model.User;
import com.devicehive.client.model.UserNetwork;
import com.devicehive.client.model.exceptions.HiveClientException;
import com.devicehive.client.model.exceptions.HiveException;
import com.google.common.reflect.TypeToken;
import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.HttpMethod;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.devicehive.client.impl.json.strategies.JsonPolicyDef.Policy.*;
import static javax.ws.rs.core.Response.Status.BAD_REQUEST;

/**
 * Implementation of {@link UserAPI}.
 */
class UserAPIImpl implements UserAPI {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserAPIImpl.class);

    private static final String CURRENT_USER = "current";
    private static final String USERS_COLLECTION_PATH = "/user";
    private static final String USER_RESOURCE_PATH = "/user/%s";
    private static final String USER_NETWORK_RESOURCE_PATH = "/user/%s/network/%s";

    private final RestAgent restAgent;

    /**
     * Initializes the API with {@link RestAgent} to use for requests.
     *
     * @param restAgent a RestAgent to use for requests
     */
    UserAPIImpl(RestAgent restAgent) {
        this.restAgent = restAgent;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<User> listUsers(String login, String loginPattern, Integer role, Integer status, String sortField,
                                String sortOrder, Integer take, Integer skip) throws HiveException {
        LOGGER.debug("User: list requested with following parameters: login {}, login pattern {}, role {}, status {}, " +
            "sort field {}, sort order {}, take {}, skip {}", login, loginPattern, role, status, sortField, sortOrder,
            take, skip);

        String path = USERS_COLLECTION_PATH;
        Type type = new TypeToken<List<User>>() {}.getType();

        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("login", login);
        queryParams.put("loginPattern", loginPattern);
        queryParams.put("role", role);
        queryParams.put("status", status);
        queryParams.put("sortField", sortField);
        queryParams.put("sortOrder", sortOrder);
        queryParams.put("take", take);
        queryParams.put("skip", skip);

        List<User> result = restAgent.execute(path, HttpMethod.GET, null, queryParams, null, type, null, USERS_LISTED);

        LOGGER.debug("User: list proceed with following parameters: login {}, login pattern {}, role {}, status {}," +
            " sort field {}, sort order {}, take {}, skip {}", login, loginPattern, role, status, sortField, sortOrder,
            take, skip);

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User getUser(long id) throws HiveException {
        LOGGER.debug("User: get requested for user with id {}", id);

        String path = String.format(USER_RESOURCE_PATH, id);
        User result = restAgent.execute(path, HttpMethod.GET, null, null, User.class, USER_PUBLISHED);

        LOGGER.debug("User: get request proceed for user with id {}", id);

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User getCurrent() throws HiveException {
        LOGGER.debug("User: get requested for current user");

        String path = String.format(USER_RESOURCE_PATH, CURRENT_USER);
        User result = restAgent.execute(path, HttpMethod.GET, null, null, User.class, USER_PUBLISHED);

        LOGGER.debug("User: get request proceed for current user");

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User insertUser(User user) throws HiveException {
        if (user == null) {
            throw new HiveClientException("User cannot be null!", BAD_REQUEST.getStatusCode());
        }

        LOGGER.debug("User: insert requested for user with params: login {}, role {}, status {}", user.getLogin(),
            user.getRole(), user.getStatus());

        String path = USERS_COLLECTION_PATH;
        User result = restAgent.execute(path, HttpMethod.POST, null, null, user, User.class, USER_UPDATE, USER_SUBMITTED);

        LOGGER.debug("User: insert proceed for user with params: login {}, role {}, status {}. Id {}", user.getLogin(),
            user.getRole(), user.getStatus(), user.getId());

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateUser(User user) throws HiveException {
        if (user == null) {
            throw new HiveClientException("User cannot be null!", BAD_REQUEST.getStatusCode());
        }
        if (user.getId() == null) {
            throw new HiveClientException("User id cannot be null!", BAD_REQUEST.getStatusCode());
        }

        LOGGER.debug("User: update requested for user with params: id {}, login {}, role {}, status {}",
            user.getId(), user.getLogin(), user.getRole(), user.getStatus());

        String path = String.format(USER_RESOURCE_PATH, user.getId());
        restAgent.execute(path, HttpMethod.PUT, null, user, USER_UPDATE);

        LOGGER.debug("User: update proceed for user with params: id {}, login {}, role {}, status {}",
            user.getId(), user.getLogin(), user.getRole(), user.getStatus());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateCurrent(User user) throws HiveException {
        if (user == null) {
            throw new HiveClientException("User cannot be null!", BAD_REQUEST.getStatusCode());
        }

        LOGGER.debug("User: update requested for current user with params: login {}, role {}, status {}",
            user.getLogin(), user.getRole(), user.getStatus());

        String path = String.format(USER_RESOURCE_PATH, CURRENT_USER);
        restAgent.execute(path, HttpMethod.PUT, null, user, USER_UPDATE);

        LOGGER.debug("User: update proceed for current user with params: login {}, role {}, status {}",
            user.getLogin(), user.getRole(), user.getStatus());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteUser(long id) throws HiveException {
        LOGGER.debug("User: delete requested for user with id {}", id);

        String path = String.format(USER_RESOURCE_PATH, id);
        restAgent.execute(path, HttpMethod.DELETE);

        LOGGER.debug("User: delete proceed for user with id {}", id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserNetwork getNetwork(long userId, long networkId) throws HiveException {
        LOGGER.debug("User: getNetwork requested for user with id {} and network with id {}", userId, networkId);
        String path = "/user/" + userId + "/network/" + networkId;
        UserNetwork result = restAgent.execute(path, HttpMethod.GET, null,
                                               UserNetwork.class,
                                               NETWORKS_LISTED);
        LOGGER.debug("User: getNetwork proceed for user with id {} and network with id {}", userId, networkId);
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void assignNetwork(long userId, long networkId) throws HiveException {
        LOGGER.debug("User: assignNetwork requested for user with id {} and network with id {}", userId, networkId);

        String path = String .format(USER_NETWORK_RESOURCE_PATH, userId, networkId);
        JsonObject stub = new JsonObject();
        restAgent.execute(path, HttpMethod.PUT, null, stub, null);

        LOGGER.debug("User: assignNetwork proceed for user with id {} and network with id {}", userId, networkId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void unassignNetwork(long userId, long networkId) throws HiveException {
        LOGGER.debug("User: unassignNetwork requested for user with id {} and network with id {}", userId, networkId);

        String path = String.format(USER_NETWORK_RESOURCE_PATH, userId, networkId);
        restAgent.execute(path, HttpMethod.DELETE);

        LOGGER.debug("User: unassignNetwork proceed for user with id {} and network with id {}", userId, networkId);
    }
}
