package com.devicehive.client.model;

import com.devicehive.client.StringUtil;
import com.google.gson.annotations.SerializedName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;



@ApiModel(description = "")
public class AsyncResponse   {
  
  @SerializedName("cancelled")
  private Boolean cancelled = false;
  
  @SerializedName("done")
  private Boolean done = false;
  
  @SerializedName("suspended")
  private Boolean suspended = false;
  

  
  /**
   **/
  @ApiModelProperty(value = "")
  public Boolean getCancelled() {
    return cancelled;
  }
  public void setCancelled(Boolean cancelled) {
    this.cancelled = cancelled;
  }

  
  /**
   **/
  @ApiModelProperty(value = "")
  public Boolean getDone() {
    return done;
  }
  public void setDone(Boolean done) {
    this.done = done;
  }

  
  /**
   **/
  @ApiModelProperty(value = "")
  public Boolean getSuspended() {
    return suspended;
  }
  public void setSuspended(Boolean suspended) {
    this.suspended = suspended;
  }

  

  @Override
  public String toString()  {
    StringBuilder sb = new StringBuilder();
    sb.append("class AsyncResponse {\n");
    
    sb.append("    cancelled: ").append(StringUtil.toIndentedString(cancelled)).append("\n");
    sb.append("    done: ").append(StringUtil.toIndentedString(done)).append("\n");
    sb.append("    suspended: ").append(StringUtil.toIndentedString(suspended)).append("\n");
    sb.append("}");
    return sb.toString();
  }
}
