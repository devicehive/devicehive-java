package com.devicehive.client.websocket.api.impl;


import com.devicehive.client.websocket.api.OAuthGrantAPI;
import com.devicehive.client.websocket.context.RestAgent;
import com.devicehive.client.websocket.model.AccessType;
import com.devicehive.client.websocket.model.OAuthGrant;
import com.devicehive.client.websocket.model.OAuthType;
import com.devicehive.client.websocket.model.exceptions.HiveClientException;
import com.devicehive.client.websocket.model.exceptions.HiveException;
import com.google.common.reflect.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.HttpMethod;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.devicehive.client.websocket.json.strategies.JsonPolicyDef.Policy.*;
import static javax.ws.rs.core.Response.Status.BAD_REQUEST;

/**
 * Implementation of {@link OAuthGrantAPI}.
 */
class OAuthGrantAPIImpl implements OAuthGrantAPI {
    private static final Logger LOGGER = LoggerFactory.getLogger(OAuthGrantAPIImpl.class);

    private static final String CURRENT_USER = "current";
    private static final String OAUTH_GRANTS_COLLECTION_PATH = "/user/%s/oauth/grant";
    private static final String OAUTH_GRANT_RESOURCE_PATH = "/user/%s/oauth/grant/%s";

    private final RestAgent restAgent;

    /**
     * Initializes the API with {@link RestAgent} to use for requests.
     *
     * @param restAgent a RestAgent to use for requests
     */
    OAuthGrantAPIImpl(RestAgent restAgent) {
        this.restAgent = restAgent;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<OAuthGrant> list(long userId, Date start, Date end, String clientOauthId, OAuthType type,
                                 String scope, String redirectUri, AccessType accessType, String sortField,
                                 String sortOrder, Integer take, Integer skip) throws HiveException {
        LOGGER.debug("OAuthGrant: list requested with parameters: userId {}, start timestamp {}, end timestamp {}, " +
            "client OAuth identifier {}, OAuth grant type {}, OAuth scope {} OAuth redirect URI {}, access type {}, " +
            "sort field {}, sort order {}, take {}, skip {}", userId, start, end, clientOauthId, type, scope, redirectUri,
            accessType, sortField, sortOrder, take, skip);

        String path = String.format(OAUTH_GRANTS_COLLECTION_PATH, userId);
        Type paramType = new TypeToken<List<OAuthGrant>>() {}.getType();

        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("start", start);
        queryParams.put("end", end);
        queryParams.put("clientOAuthId", clientOauthId);
        queryParams.put("type", type);
        queryParams.put("scope", scope);
        queryParams.put("redirectUri", redirectUri);
        queryParams.put("accessType", accessType);
        queryParams.put("sortField", sortField);
        queryParams.put("sortOrder", sortOrder);
        queryParams.put("take", take);
        queryParams.put("skip", skip);

        List<OAuthGrant> result = restAgent.execute(path, HttpMethod.GET, null, queryParams, paramType,
            OAUTH_GRANT_LISTED);

        LOGGER.debug("OAuthGrant: list request proceed for parameters: userId {}, start timestamp {}, end timestamp {}, " +
            "client OAuth identifier {}, OAuth grant type {}, OAuth scope {} OAuth redirect URI {}, " +
            "access type {}, sort field {}, sort order {}, take {}, skip {}", userId, start, end, clientOauthId, type,
            scope, redirectUri, accessType, sortField, sortOrder, take, skip);

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<OAuthGrant> list(Date start, Date end, String clientOauthId, OAuthType type, String scope,
                                 String redirectUri, AccessType accessType, String sortField, String sortOrder,
                                 Integer take, Integer skip) throws HiveException {
        LOGGER.debug("OAuthGrant: list requested for current user with parameters: start timestamp {}, end timestamp {}, " +
            "client OAuth identifier {}, OAuth grant type {}, OAuth scope {} OAuth redirect URI {}, access type {}, " +
            "sort field {}, sort order {}, take {}, skip {}", start, end, clientOauthId, type, scope, redirectUri,
            accessType, sortField, sortOrder, take, skip);

        String path = String.format(OAUTH_GRANTS_COLLECTION_PATH, CURRENT_USER);
        Type paramType = new TypeToken<List<OAuthGrant>>() {}.getType();

        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("start", start);
        queryParams.put("end", end);
        queryParams.put("clientOAuthId", clientOauthId);
        queryParams.put("type", type);
        queryParams.put("scope", scope);
        queryParams.put("redirectUri", redirectUri);
        queryParams.put("accessType", accessType);
        queryParams.put("sortField", sortField);
        queryParams.put("sortOrder", sortOrder);
        queryParams.put("take", take);
        queryParams.put("skip", skip);

        List<OAuthGrant> result = restAgent.execute(path, HttpMethod.GET, null, queryParams, paramType,
            OAUTH_GRANT_LISTED);

        LOGGER.debug("OAuthGrant: list proceed for current user with parameters: start timestamp {}, end timestamp {}, " +
            "client OAuth identifier {}, OAuth grant type {}, OAuth scope {} OAuth redirect URI {}, access type {}, " +
            "sort field {}, sort order {}, take {}, skip {}", start, end, clientOauthId, type, scope, redirectUri,
            accessType, sortField, sortOrder, take, skip);

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OAuthGrant get(long userId, long grantId) throws HiveException {
        LOGGER.debug("OAuthGrant: get requested for user id {} and grant id {}", userId, grantId);

        String path = String.format(OAUTH_GRANT_RESOURCE_PATH, userId, grantId);
        OAuthGrant result = restAgent.execute(path, HttpMethod.GET, null, OAuthGrant.class, OAUTH_GRANT_LISTED);

        LOGGER.debug("OAuthGrant: get proceed for user id {} and grant id {}", userId, grantId);

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OAuthGrant get(long grantId) throws HiveException {
        LOGGER.debug("OAuthGrant: get requested for current user and grant id {}", grantId);

        String path = String.format(OAUTH_GRANT_RESOURCE_PATH, CURRENT_USER, grantId);
        OAuthGrant result = restAgent.execute(path, HttpMethod.GET, null, OAuthGrant.class, OAUTH_GRANT_LISTED);

        LOGGER.debug("OAuthGrant: get proceed for current user and grant id {}", grantId);

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OAuthGrant insert(long userId, OAuthGrant grant) throws HiveException {
        if (grant == null) {
            throw new HiveClientException("OAuthGrant cannot be null!", BAD_REQUEST.getStatusCode());
        }

        LOGGER.debug("OAuthGrant: insert requested for user with id {} and grant with scope {} and type {}",
            userId, grant.getScope(), grant.getType());

        String path = String.format(OAUTH_GRANTS_COLLECTION_PATH, userId);

        OAuthGrant result;
        if (OAuthType.TOKEN.equals(grant.getType())) {
            result = restAgent.execute(path, HttpMethod.POST, null, null, grant, OAuthGrant.class, OAUTH_GRANT_PUBLISHED,
                OAUTH_GRANT_SUBMITTED_TOKEN);
        } else {
            result = restAgent.execute(path, HttpMethod.POST, null, null, grant, OAuthGrant.class, OAUTH_GRANT_PUBLISHED,
                OAUTH_GRANT_SUBMITTED_CODE);
        }

        LOGGER.debug("OAuthGrant: insert proceed for user with id {} and grant with scope {} and type {}. Result id {}",
            userId, grant.getScope(), grant.getType(), result.getId());

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OAuthGrant insert(OAuthGrant grant) throws HiveException {
        if (grant == null) {
            throw new HiveClientException("OAuthGrant cannot be null!", BAD_REQUEST.getStatusCode());
        }

        LOGGER.debug("OAuthGrant: insert requested for current user and grant with scope {} and type {}",
            grant.getScope(), grant.getType());

        String path = String.format(OAUTH_GRANTS_COLLECTION_PATH, CURRENT_USER);

        OAuthGrant result;
        if (OAuthType.TOKEN.equals(grant.getType())) {
            result = restAgent.execute(path, HttpMethod.POST, null, null, grant, OAuthGrant.class, OAUTH_GRANT_PUBLISHED,
                OAUTH_GRANT_SUBMITTED_TOKEN);
        } else {
            result = restAgent.execute(path, HttpMethod.POST, null, null, grant, OAuthGrant.class, OAUTH_GRANT_PUBLISHED,
                OAUTH_GRANT_SUBMITTED_CODE);
        }

        LOGGER.debug("OAuthGrant: insert proceed for current user and grant with scope {} and type {}. Result id {}",
                     grant.getScope(), grant.getType(), result.getId());

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OAuthGrant update(long userId, OAuthGrant grant) throws HiveException {
        if (grant == null) {
            throw new HiveClientException("OAuthGrant cannot be null!", BAD_REQUEST.getStatusCode());
        }
        if (grant.getId() == null) {
            throw new HiveClientException("OAuthGrant id cannot be null!", BAD_REQUEST.getStatusCode());
        }

        LOGGER.debug("OAuthGrant: update requested for user with id {}, grant id {} and grant with scope {} and type {}",
            userId, grant.getId(), grant.getScope(), grant.getType());

        String path = String.format(OAUTH_GRANT_RESOURCE_PATH, userId, grant.getId());
        OAuthGrant result = restAgent.execute(path, HttpMethod.PUT, null, null, grant, OAuthGrant.class,
            OAUTH_GRANT_PUBLISHED, null);

        LOGGER.debug("OAuthGrant: update proceed for user with id {}, grant id {} and grant with scope {} and type {}",
            userId, grant.getId(), grant.getScope(), grant.getType());

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OAuthGrant update(OAuthGrant grant) throws HiveException {
        if (grant == null) {
            throw new HiveClientException("OAuthGrant cannot be null!", BAD_REQUEST.getStatusCode());
        }
        if (grant.getId() == null) {
            throw new HiveClientException("OAuthGrant id cannot be null!", BAD_REQUEST.getStatusCode());
        }

        LOGGER.debug("OAuthGrant: update requested for current user, grant id {} and grant with scope {} and type {}",
            grant.getId(), grant.getScope(), grant.getType());

        String path = String.format(OAUTH_GRANT_RESOURCE_PATH, CURRENT_USER, grant.getId());
        OAuthGrant result = restAgent.execute(path, HttpMethod.PUT, null, null, grant, OAuthGrant.class,
            OAUTH_GRANT_PUBLISHED, null);

        LOGGER.debug("OAuthGrant: update proceed for current user, grant id {} and grant with scope {} and type {}",
                     grant.getId(), grant.getScope(), grant.getType());

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(long userId, long grantId) throws HiveException {
        LOGGER.debug("OAuthGrant: delete requested for user id {} and grant id {}", userId, grantId);

        String path = String.format(OAUTH_GRANT_RESOURCE_PATH, userId, grantId);
        restAgent.execute(path, HttpMethod.DELETE);

        LOGGER.debug("OAuthGrant: delete proceed for user id {} and grant id {}", userId, grantId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(long grantId) throws HiveException {
        LOGGER.debug("OAuthGrant: delete requested for current user and grant id {}", grantId);

        String path = String.format(OAUTH_GRANT_RESOURCE_PATH, CURRENT_USER, grantId);
        restAgent.execute(path, HttpMethod.DELETE);

        LOGGER.debug("OAuthGrant: delete proceed for current user and grant id {}", grantId);
    }
}
