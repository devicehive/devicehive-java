package com.devicehive.client.impl;


import com.google.common.reflect.TypeToken;

import com.devicehive.client.AccessKeyAPI;
import com.devicehive.client.impl.context.RestAgent;
import com.devicehive.client.model.AccessKey;
import com.devicehive.client.model.exceptions.HiveClientException;
import com.devicehive.client.model.exceptions.HiveException;
import com.google.common.reflect.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.HttpMethod;
import java.lang.reflect.Type;
import java.util.List;

import static com.devicehive.client.impl.json.strategies.JsonPolicyDef.Policy.*;
import static javax.ws.rs.core.Response.Status.BAD_REQUEST;

/**
 * Implementation of {@link AccessKeyController}.
 */
class AccessKeyAPIImpl implements AccessKeyAPI {
    private static final Logger LOGGER = LoggerFactory.getLogger(AccessKeyAPIImpl.class);

    private static final String CURRENT_USER = "current";
    private static final String USER_ACCESS_KEYS_COLLECTION_PATH = "/user/%s/accesskey";
    private static final String USER_ACCESS_KEY_RESOURCE_PATH = "/user/%s/accesskey/%s";


    private final RestAgent restAgent;

    /**
     * Initializes a controller with a {@link RestAgent} to use for requests.
     *
     * @param restAgent an instance of {@link RestAgent}
     */
    AccessKeyAPIImpl(RestAgent restAgent) {
        this.restAgent = restAgent;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<AccessKey> listKeys(long userId) throws HiveException {
        LOGGER.debug("AccessKey: list requested for user id {}", userId);

        String path = String.format(USER_ACCESS_KEYS_COLLECTION_PATH, userId);
        Type type = new TypeToken<List<AccessKey>>() {}.getType();

        List<AccessKey> result = restAgent.execute(path, HttpMethod.GET, null, null, type, ACCESS_KEY_LISTED);

        LOGGER.debug("AccessKey: list request for user id {} proceed successfully.", userId);
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<AccessKey> listKeys() throws HiveException {
        LOGGER.debug("AccessKey: list requested for current user");

        String path = String.format(USER_ACCESS_KEYS_COLLECTION_PATH, CURRENT_USER);
        Type type = new TypeToken<List<AccessKey>>() {}.getType();

        List<AccessKey> result = restAgent.execute(path, HttpMethod.GET, null, null, type, ACCESS_KEY_LISTED);

        LOGGER.debug("AccessKey: list request for current user proceed successfully.");
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AccessKey getKey(long userId, long keyId) throws HiveException {
        LOGGER.debug("AccessKey: get requested for user with id {} and key id {}", userId, keyId);

        String path = String.format(USER_ACCESS_KEY_RESOURCE_PATH, userId, keyId);
        AccessKey result = restAgent.execute(path, HttpMethod.GET, null, AccessKey.class, ACCESS_KEY_LISTED);

        LOGGER.debug("AccessKey: get request proceed successfully for user with id {} and key id {}", userId, keyId);
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AccessKey getKey(long keyId) throws HiveException {
        LOGGER.debug("AccessKey: get requested for current user and key with id {}", keyId);

        String path = String.format(USER_ACCESS_KEY_RESOURCE_PATH, CURRENT_USER, keyId);
        AccessKey key = restAgent.execute(path, HttpMethod.GET, null, AccessKey.class, ACCESS_KEY_LISTED);

        LOGGER.debug("AccessKey: get request proceed successfully for current user and key with id {}", keyId);
        return key;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AccessKey insertKey(long userId, AccessKey key) throws HiveException {
        if (key == null) {
            throw new HiveClientException("Key cannot be null!", BAD_REQUEST.getStatusCode());
        }

        LOGGER.debug("AccessKey: insert requested for user with id {}. Key params: label {}, " +
            "expiration date {}", userId, key.getLabel(), key.getExpirationDate());

        String path = String.format(USER_ACCESS_KEYS_COLLECTION_PATH, userId);
        AccessKey result = restAgent.execute(path, HttpMethod.POST, null, null, key, AccessKey.class,
            ACCESS_KEY_PUBLISHED, ACCESS_KEY_SUBMITTED);

        LOGGER.debug("AccessKey: insert request proceed successfully for user with id {}. Key params: id {}, " +
            "label {}, expiration date {}", userId, key.getId(), key.getLabel(), key.getExpirationDate());
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AccessKey insertKey(AccessKey key) throws HiveException {
        if (key == null) {
            throw new HiveClientException("Key cannot be null!", BAD_REQUEST.getStatusCode());
        }

        LOGGER.debug("AccessKey: insert requested for current user. Key params: label {}, " +
            "expiration date {}", key.getLabel(), key.getExpirationDate());

        String path = String.format(USER_ACCESS_KEYS_COLLECTION_PATH, CURRENT_USER);
        AccessKey result = restAgent.execute(path, HttpMethod.POST, null, null, key, AccessKey.class,
            ACCESS_KEY_PUBLISHED, ACCESS_KEY_SUBMITTED);

        LOGGER.debug("AccessKey: insert request proceed successfully for current user. Key params: id {}, " +
            "label {}, expiration date {}", key.getId(), key.getLabel(), key.getExpirationDate());
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateKey(long userId, AccessKey key) throws HiveException {
        if (key == null || key.getId() == null) {
            throw new HiveClientException("Key cannot be null!", BAD_REQUEST.getStatusCode());
        }

        LOGGER.debug("AccessKey: update requested for user with id {}. Key params: id {}, label {}, " +
            "expiration date {}", userId, key.getId(), key.getLabel(), key.getExpirationDate());

        String path = String.format(USER_ACCESS_KEY_RESOURCE_PATH, userId, key.getId());
        restAgent.execute(path, HttpMethod.PUT, null, key, ACCESS_KEY_PUBLISHED);

        LOGGER.debug("AccessKey: update request proceed successfully for user with id {}. Key params: id {}, " +
            "label {}, expiration date {}", userId, key.getId(), key.getLabel(), key.getExpirationDate());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateKey(AccessKey key) throws HiveException {
        if (key == null || key.getId() == null) {
            throw new HiveClientException("Key cannot be null!", BAD_REQUEST.getStatusCode());
        }

        LOGGER.debug("AccessKey: update requested for current user. Key params: id{}, label {}, " +
            "expiration date {}", key.getId(), key.getLabel(), key.getExpirationDate());

        String path = String.format(USER_ACCESS_KEY_RESOURCE_PATH, CURRENT_USER, key.getId());
        restAgent.execute(path, HttpMethod.PUT, null, key, ACCESS_KEY_PUBLISHED);

        LOGGER.debug("AccessKey: update request proceed successfully for current user. Key params: id {}, " +
            "label {}, expiration date {}", key.getId(), key.getLabel(), key.getExpirationDate());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteKey(long userId, long keyId) throws HiveException {
        LOGGER.debug("AccessKey: delete requested for user with id {}. Key id {}", userId, keyId);

        String path = String.format(USER_ACCESS_KEY_RESOURCE_PATH, userId, keyId);
        restAgent.execute(path, HttpMethod.DELETE);

        LOGGER.debug("AccessKey: delete request proceed successfully for user with id {}. Key id {}", userId, keyId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteKey(long keyId) throws HiveException {
        LOGGER.debug("AccessKey: delete requested for current user. Key id {}", keyId);

        String path = String.format(USER_ACCESS_KEY_RESOURCE_PATH, CURRENT_USER, keyId);
        restAgent.execute(path, HttpMethod.DELETE);

        LOGGER.debug("AccessKey: delete request proceed successfully for current user. Key id {}", keyId);
    }
}
