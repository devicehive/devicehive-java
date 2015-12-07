package com.devicehive.client.websocket.api.impl;

import com.devicehive.client.websocket.api.OAuthTokenAPI;
import com.devicehive.client.websocket.context.RestAgent;
import com.devicehive.client.websocket.model.AccessToken;
import com.devicehive.client.websocket.model.exceptions.HiveException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Implementation of {@link OAuthTokenAPI}.
 */
class OAuthTokenAPIImpl implements OAuthTokenAPI {
    private static final Logger LOGGER = LoggerFactory.getLogger(OAuthTokenAPIImpl.class);

    private static final String OAUTH_TOKEN_ENDPOINT = "/oauth2/token";

    private final RestAgent restAgent;

    /**
     * Initializes the API with {@link RestAgent} to use for requests.
     *
     * @param restAgent a RestAgent to use for requests
     */
    OAuthTokenAPIImpl(RestAgent restAgent) {
        this.restAgent = restAgent;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AccessToken requestAccessToken(String grantType, String code, String redirectUri, String clientId,
                                          String scope, String login, String password) throws HiveException {
        LOGGER.debug("Access token requested with params: grant type {}, code {}, redirect uri {}, client id {}, " +
            "scope {}, login {}", grantType, code, redirectUri, clientId, scope, login);

        String path = OAUTH_TOKEN_ENDPOINT;

        Map<String, String> formParams = new HashMap<>();
        formParams.put("grant_type", grantType);
        formParams.put("code", code);
        formParams.put("redirect_uri", redirectUri);
        formParams.put("client_id", clientId);
        formParams.put("scope", scope);
        formParams.put("username", login);
        formParams.put("password", password);

        AccessToken result = restAgent.executeForm(path, formParams, AccessToken.class, null);

        LOGGER.debug("Access token request proceed for params: grant type {}, code {}, redirect uri {}, client id {}, " +
            "scope {}, login {}", grantType, code, redirectUri, clientId, scope, login);

        return result;
    }
}
