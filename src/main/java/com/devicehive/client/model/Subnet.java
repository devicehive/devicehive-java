package com.devicehive.client.model;

import com.devicehive.client.StringUtil;

import com.google.gson.annotations.SerializedName;



import io.swagger.annotations.*;



@ApiModel(description = "")
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
  public String toString()  {
    StringBuilder sb = new StringBuilder();
    sb.append("class Subnet {\n");
    
    sb.append("    inetAddress: ").append(StringUtil.toIndentedString(inetAddress)).append("\n");
    sb.append("    mask: ").append(StringUtil.toIndentedString(mask)).append("\n");
    sb.append("    subnet: ").append(StringUtil.toIndentedString(subnet)).append("\n");
    sb.append("}");
    return sb.toString();
  }
}
