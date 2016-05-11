package com.devicehive.client.model;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

import io.swagger.annotations.ApiModelProperty;


public class NetworkUpdate   {
  
  @SerializedName("id")
  private Long id = null;

  @SerializedName("key")
  private NullableWrapper key = null;

  @SerializedName("name")
  private NullableWrapper name = null;

  @SerializedName("description")
  private NullableWrapper description = null;

  /**
   **/
  @ApiModelProperty(value = "")
  public Long getId() {
    return id;
  }
  public void setId(Long id) {
    this.id = id;
  }

  /**
   **/
  @ApiModelProperty(value = "")
  public NullableWrapper getKey() {
    return key;
  }
  public void setKey(NullableWrapper key) {
    this.key = key;
  }

  /**
   **/
  @ApiModelProperty(value = "")
  public NullableWrapper getName() {
    return name;
  }
  public void setName(NullableWrapper name) {
    this.name = name;
  }

  /**
   **/
  @ApiModelProperty(value = "")
  public NullableWrapper getDescription() {
    return description;
  }
  public void setDescription(NullableWrapper description) {
    this.description = description;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    NetworkUpdate networkUpdate = (NetworkUpdate) o;
    return Objects.equals(id, networkUpdate.id) &&
        Objects.equals(key, networkUpdate.key) &&
        Objects.equals(name, networkUpdate.name) &&
        Objects.equals(description, networkUpdate.description);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, key, name, description);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class NetworkUpdate {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    key: ").append(toIndentedString(key)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
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
