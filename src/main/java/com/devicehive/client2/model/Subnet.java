package com.devicehive.client2.model;

import java.util.Objects;

import io.swagger.annotations.ApiModelProperty;

import com.google.gson.annotations.SerializedName;





public class Subnet   {
  
  @SerializedName("inetAddress")
  private InetAddress inetAddress = null;

  @SerializedName("mask")
  private Integer mask = null;

  @SerializedName("subnet")
  private String subnet = null;

  /**
   **/
  @ApiModelProperty(value = "")
  public InetAddress getInetAddress() {
    return inetAddress;
  }
  public void setInetAddress(InetAddress inetAddress) {
    this.inetAddress = inetAddress;
  }

  /**
   **/
  @ApiModelProperty(value = "")
  public Integer getMask() {
    return mask;
  }
  public void setMask(Integer mask) {
    this.mask = mask;
  }

  /**
   **/
  @ApiModelProperty(value = "")
  public String getSubnet() {
    return subnet;
  }
  public void setSubnet(String subnet) {
    this.subnet = subnet;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Subnet subnet = (Subnet) o;
    return Objects.equals(inetAddress, subnet.inetAddress) &&
        Objects.equals(mask, subnet.mask) &&
        Objects.equals(subnet, subnet.subnet);
  }

  @Override
  public int hashCode() {
    return Objects.hash(inetAddress, mask, subnet);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Subnet {\n");
    
    sb.append("    inetAddress: ").append(toIndentedString(inetAddress)).append("\n");
    sb.append("    mask: ").append(toIndentedString(mask)).append("\n");
    sb.append("    subnet: ").append(toIndentedString(subnet)).append("\n");
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
