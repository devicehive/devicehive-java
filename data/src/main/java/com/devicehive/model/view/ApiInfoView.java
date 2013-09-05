package com.devicehive.model.view;

import com.devicehive.json.strategies.JsonPolicyDef;
import org.apache.commons.lang3.ObjectUtils;

import java.sql.Timestamp;

import static com.devicehive.json.strategies.JsonPolicyDef.Policy.REST_SERVER_INFO;
import static com.devicehive.json.strategies.JsonPolicyDef.Policy.WEBSOCKET_SERVER_INFO;

/**
 * TODO JavaDoc
 */
public class ApiInfoView implements HiveEntity {

    @JsonPolicyDef({WEBSOCKET_SERVER_INFO, REST_SERVER_INFO})
    private String apiVersion;

    @JsonPolicyDef({WEBSOCKET_SERVER_INFO, REST_SERVER_INFO})
    private Timestamp serverTimestamp;

    @JsonPolicyDef(REST_SERVER_INFO)
    private String webSocketServerUrl;

    @JsonPolicyDef(WEBSOCKET_SERVER_INFO)
    private String restServerUrl;

    public ApiInfoView() {
    }

    public String getApiVersion() {
        return apiVersion;
    }

    public void setApiVersion(String apiVersion) {
        this.apiVersion = apiVersion;
    }

    public Timestamp getServerTimestamp() {
        return ObjectUtils.cloneIfPossible(serverTimestamp);
    }

    public void setServerTimestamp(Timestamp serverTimestamp) {
        this.serverTimestamp = ObjectUtils.cloneIfPossible(serverTimestamp);
    }

    public String getWebSocketServerUrl() {
        return webSocketServerUrl;
    }

    public void setWebSocketServerUrl(String webSocketServerUrl) {
        this.webSocketServerUrl = webSocketServerUrl;
    }


    public String getRestServerUrl() {
        return restServerUrl;
    }

    public void setRestServerUrl(String restServerUrl) {
        this.restServerUrl = restServerUrl;
    }
}
