package com.devicehive.client.model;


import com.devicehive.client.StringUtil;
import com.google.gson.annotations.SerializedName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel(description = "")
public class ApiInfoAuth {


    @SerializedName("providers")
    private List<ApiInfoClient> providers = null;
    @SerializedName("sessionTimeout")
    private Long sessionTimeout = null;

    @ApiModelProperty(value = "")
    public Long getSessionTimeout() {
        return sessionTimeout;
    }

    public void setSessionTimeout(Long sessionTimeout) {
        this.sessionTimeout = sessionTimeout;
    }

    @ApiModelProperty(value = "")
    public List<ApiInfoClient> getProviders() {
        return providers;
    }

    public void setProviders(List<ApiInfoClient> providers) {
        this.providers = providers;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class ApiInfoAuth {\n");

        sb.append("    providers: ").append(StringUtil.toIndentedString(providers)).append("\n");
        sb.append("    sessionTimeout: ").append(StringUtil.toIndentedString(sessionTimeout)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    public class ApiInfoClient {
        @SerializedName("name")
        private String name = null;
        @SerializedName("clientId")
        private String clientId = null;

        @ApiModelProperty(value = "")
        public String getClientId() {
            return clientId;
        }

        public void setClientId(String clientId) {
            this.clientId = clientId;
        }

        @ApiModelProperty(value = "")
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("class ApiInfoClient {\n");

            sb.append("    name: ").append(StringUtil.toIndentedString(name)).append("\n");
            sb.append("    clientId: ").append(StringUtil.toIndentedString(clientId)).append("\n");
            sb.append("}");
            return sb.toString();
        }
    }


}
