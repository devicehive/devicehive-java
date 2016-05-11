package com.devicehive.client.model;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

import io.swagger.annotations.ApiModelProperty;


public class EquipmentUpdate   {
  
  @SerializedName("id")
  private Long id = null;

  @SerializedName("name")
  private NullableWrapper name = null;

  @SerializedName("code")
  private NullableWrapper code = null;

  @SerializedName("type")
  private NullableWrapper type = null;

  @SerializedName("data")
  private NullableWrapper data = null;

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
  public NullableWrapper getName() {
    return name;
  }
  public void setName(NullableWrapper name) {
    this.name = name;
  }

  /**
   **/
  @ApiModelProperty(value = "")
  public NullableWrapper getCode() {
    return code;
  }
  public void setCode(NullableWrapper code) {
    this.code = code;
  }

  /**
   **/
  @ApiModelProperty(value = "")
  public NullableWrapper getType() {
    return type;
  }
  public void setType(NullableWrapper type) {
    this.type = type;
  }

  /**
   **/
  @ApiModelProperty(value = "")
  public NullableWrapper getData() {
    return data;
  }
  public void setData(NullableWrapper data) {
    this.data = data;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    EquipmentUpdate equipmentUpdate = (EquipmentUpdate) o;
    return Objects.equals(id, equipmentUpdate.id) &&
        Objects.equals(name, equipmentUpdate.name) &&
        Objects.equals(code, equipmentUpdate.code) &&
        Objects.equals(type, equipmentUpdate.type) &&
        Objects.equals(data, equipmentUpdate.data);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, code, type, data);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class EquipmentUpdate {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    code: ").append(toIndentedString(code)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    data: ").append(toIndentedString(data)).append("\n");
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
