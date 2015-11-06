package io.swagger.client.model;

import com.google.gson.annotations.SerializedName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.client.StringUtil;

@ApiModel(description = "")
public class DeviceCommandResponse {

    @SerializedName("id")
    private Long id = null;

    @SerializedName("timestamp")
    private String timestamp = null;

    @SerializedName("userId")
    private Long userId = null;

    @ApiModelProperty(value = "")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ApiModelProperty(value = "")
    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    @ApiModelProperty(value = "")
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class DeviceCommandResponse {\n");

        sb.append("    id: ").append(StringUtil.toIndentedString(id)).append("\n");
        sb.append("    timestamp: ").append(StringUtil.toIndentedString(timestamp)).append("\n");
        sb.append("    userId: ").append(StringUtil.toIndentedString(userId)).append("\n");
        sb.append("}");
        return sb.toString();
    }
}
