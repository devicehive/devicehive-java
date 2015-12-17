package com.devicehive.client.websocket.model;

import com.devicehive.client.json.strategies.JsonPolicyDef;
import com.google.common.base.Optional;

import static com.devicehive.client.json.strategies.JsonPolicyDef.Policy.*;

/**
 * Represents a client with the access to the DeviceHive OAuth API. For more details see <a
 * href="http://www.devicehive.com/restful#Reference/OAuthClient">OAuthClient</a>
 */
public class OAuthClient implements HiveEntity {

    private static final long serialVersionUID = -1095382534684298244L;
    @JsonPolicyDef({OAUTH_CLIENT_LISTED, OAUTH_CLIENT_PUBLISHED, OAUTH_GRANT_LISTED})
    private Long id;

    @JsonPolicyDef({OAUTH_CLIENT_LISTED, OAUTH_CLIENT_SUBMITTED, OAUTH_GRANT_LISTED, OAUTH_CLIENT_SUBMITTED,
                    OAUTH_GRANT_PUBLISHED})
    private Optional<String> name;

    @JsonPolicyDef({OAUTH_CLIENT_LISTED, OAUTH_CLIENT_SUBMITTED, OAUTH_GRANT_LISTED, OAUTH_GRANT_PUBLISHED})
    private Optional<String> domain;

    @JsonPolicyDef({OAUTH_CLIENT_LISTED, OAUTH_CLIENT_SUBMITTED, OAUTH_GRANT_LISTED, OAUTH_GRANT_PUBLISHED})
    private Optional<String> subnet;

    @JsonPolicyDef({OAUTH_CLIENT_LISTED, OAUTH_CLIENT_SUBMITTED, OAUTH_GRANT_LISTED, OAUTH_GRANT_PUBLISHED})
    private Optional<String> redirectUri;

    @JsonPolicyDef({OAUTH_CLIENT_LISTED, OAUTH_CLIENT_SUBMITTED, OAUTH_GRANT_LISTED, OAUTH_GRANT_PUBLISHED})
    private Optional<String> oauthId;

    @JsonPolicyDef({OAUTH_CLIENT_LISTED, OAUTH_CLIENT_PUBLISHED, OAUTH_GRANT_LISTED})
    private Optional<String> oauthSecret;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return NullableWrapper.value(name);
    }

    public void setName(String name) {
        this.name = Optional.fromNullable(name);
    }

    public String getDomain() {
        return NullableWrapper.value(domain);
    }

    public void setDomain(String domain) {
        this.domain = Optional.fromNullable(domain);
    }

    public String getSubnet() {
        return NullableWrapper.value(subnet);
    }

    public void setSubnet(String subnet) {
        this.subnet = Optional.fromNullable(subnet);
    }

    public String getRedirectUri() {
        return NullableWrapper.value(redirectUri);
    }

    public void setRedirectUri(String redirectUri) {
        this.redirectUri = Optional.fromNullable(redirectUri);
    }

    public String getOauthId() {
        return NullableWrapper.value(oauthId);
    }

    public void setOauthId(String oauthId) {
        this.oauthId = Optional.fromNullable(oauthId);
    }

    public String getOauthSecret() {
        return NullableWrapper.value(oauthSecret);
    }

    public void setOauthSecret(String oauthSecret) {
        this.oauthSecret = Optional.fromNullable(oauthSecret);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("OAuthClient{");
        sb.append("oauthId=").append(oauthId);
        sb.append(", id=").append(id);
        sb.append(", name=").append(name);
        sb.append(", domain=").append(domain);
        sb.append(", subnet=").append(subnet);
        sb.append(", redirectUri=").append(redirectUri);
        sb.append('}');
        return sb.toString();
    }
}
