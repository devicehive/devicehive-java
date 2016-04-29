package com.devicehive.client2.model;

import java.util.Objects;

import io.swagger.annotations.ApiModelProperty;

import com.google.gson.annotations.SerializedName;





public class JsonStringWrapper   {
  
  @SerializedName("jsonString")
  private String jsonString = null;

  /**
   **/
  @ApiModelProperty(value = "")
  public String getJsonString() {
    return jsonString;
  }
  public void setJsonString(String jsonString) {
    this.jsonString = jsonString;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    JsonStringWrapper jsonStringWrapper = (JsonStringWrapper) o;
    return Objects.equals(jsonString, jsonStringWrapper.jsonString);
  }

  @Override
  public int hashCode() {
    return Objects.hash(jsonString);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class JsonStringWrapper {\n");
    
    sb.append("    jsonString: ").append(toIndentedString(jsonString)).append("\n");
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
