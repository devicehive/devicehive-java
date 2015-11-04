package com.devicehive.client;


import com.devicehive.client.model.AccessToken;
import com.devicehive.client.model.exceptions.HiveException;

/**
 * Client side controller for obtaining an OAuth access token. Transport declared in the hive context will be used.
 */
public interface OAuthTokenController {

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
