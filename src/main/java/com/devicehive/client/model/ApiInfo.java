package com.devicehive.client.model;

import com.devicehive.client.StringUtil;
import com.google.gson.annotations.SerializedName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "")
public class ApiInfo {

    @SerializedName("apiVersion")
    private String apiVersion=null;

    @SerializedName("serverTimestamp")
    private String serverTimestamp=null;

    @SerializedName("webSocketServerUrl")
    private String webSocketServerUrl =null;

    @ApiModelProperty(value = "")
    public String getApiVersion() {
        return apiVersion;
    }

    public void setApiVersion(String apiVersion) {
        this.apiVersion = apiVersion;
    }

    @ApiModelProperty(required = true,value = "")
    public String getServerTimestamp() {
        return serverTimestamp;
    }

    public void setServerTimestamp(String serverTimestamp) {
        this.serverTimestamp = serverTimestamp;
    }

    @ApiModelProperty(required = true,value = "")
    public String getWebSocketServerUrl() {
        return webSocketServerUrl;
    }

    public void setWebSocketServerUrl(String webSocketServerUrl) {
        this.webSocketServerUrl = webSocketServerUrl;
    }



    @Override
    public String toString()  {
        StringBuilder sb = new StringBuilder();
        sb.append("class ApiInfo {\n");

        sb.append("    apiVersion: ").append(StringUtil.toIndentedString(apiVersion)).append("\n");
        sb.append("    serverTimestamp: ").append(StringUtil.toIndentedString(serverTimestamp)).append("\n");
        sb.append("    webSocketServerUrl: ").append(StringUtil.toIndentedString(webSocketServerUrl)).append("\n");
        sb.append("}");
        return sb.toString();
    }
}
