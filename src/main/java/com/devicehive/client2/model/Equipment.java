package com.devicehive.client2.model;

import java.util.Objects;

import io.swagger.annotations.ApiModelProperty;

import com.google.gson.annotations.SerializedName;





public class Equipment   {
  
  @SerializedName("id")
  private Long id = null;

  @SerializedName("name")
  private String name = null;

  @SerializedName("code")
  private String code = null;

  @SerializedName("type")
  private String type = null;

  @SerializedName("data")
  private JsonStringWrapper data = null;

  @SerializedName("entityVersion")
  private Long entityVersion = null;

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
  @ApiModelProperty(required = true, value = "")
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }

  /**
   **/
  @ApiModelProperty(required = true, value = "")
  public String getCode() {
    return code;
  }
  public void setCode(String code) {
    this.code = code;
  }

  /**
   **/
  @ApiModelProperty(required = true, value = "")
  public String getType() {
    return type;
  }
  public void setType(String type) {
    this.type = type;
  }

  /**
   **/
  @ApiModelProperty(value = "")
  public JsonStringWrapper getData() {
    return data;
  }
  public void setData(JsonStringWrapper data) {
    this.data = data;
  }

  /**
   **/
  @ApiModelProperty(value = "")
  public Long getEntityVersion() {
    return entityVersion;
  }
  public void setEntityVersion(Long entityVersion) {
    this.entityVersion = entityVersion;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Equipment equipment = (Equipment) o;
    return Objects.equals(id, equipment.id) &&
        Objects.equals(name, equipment.name) &&
        Objects.equals(code, equipment.code) &&
        Objects.equals(type, equipment.type) &&
        Objects.equals(data, equipment.data) &&
        Objects.equals(entityVersion, equipment.entityVersion);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, code, type, data, entityVersion);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Equipment {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    code: ").append(toIndentedString(code)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    data: ").append(toIndentedString(data)).append("\n");
    sb.append("    entityVersion: ").append(toIndentedString(entityVersion)).append("\n");
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
