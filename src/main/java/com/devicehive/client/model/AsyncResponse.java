package com.devicehive.client.model;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

import io.swagger.annotations.ApiModelProperty;


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
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AsyncResponse asyncResponse = (AsyncResponse) o;
    return Objects.equals(cancelled, asyncResponse.cancelled) &&
        Objects.equals(done, asyncResponse.done) &&
        Objects.equals(suspended, asyncResponse.suspended);
  }

  @Override
  public int hashCode() {
    return Objects.hash(cancelled, done, suspended);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AsyncResponse {\n");
    
    sb.append("    cancelled: ").append(toIndentedString(cancelled)).append("\n");
    sb.append("    done: ").append(toIndentedString(done)).append("\n");
    sb.append("    suspended: ").append(toIndentedString(suspended)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}
