package com.devicehive.client.websocket.api;


import com.devicehive.client.websocket.model.AccessToken;
import com.devicehive.client.websocket.model.exceptions.HiveException;

/**
 * The API for obtaining an OAuth access token.
 */
public interface OAuthTokenAPI {

    /**
     * Requests an OAuth {@link AccessToken} using the specified arguments.
     *
     * @param grantType   a grant type
     * @param code        an access code
     * @param redirectUri a redirect URI
     * @param clientId    a client identifier
     * @param scope       an authorization scope
     * @param login       a login
     * @param password    a password
     * @return an OAuth {@link AccessToken} resource
     * @throws HiveException if an error occurs during the request execution
     */
    AccessToken requestAccessToken(String grantType, String code, String redirectUri, String clientId, String scope,
                                   String login, String password) throws HiveException;
}
