package com.devicehive.client.model;

import com.devicehive.client.StringUtil;
import com.google.gson.annotations.SerializedName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;



@ApiModel(description = "")
public class InetAddress   {
  
  @SerializedName("multicastAddress")
  private Boolean multicastAddress = false;
  
  @SerializedName("anyLocalAddress")
  private Boolean anyLocalAddress = false;
  
  @SerializedName("loopbackAddress")
  private Boolean loopbackAddress = false;
  
  @SerializedName("linkLocalAddress")
  private Boolean linkLocalAddress = false;
  
  @SerializedName("siteLocalAddress")
  private Boolean siteLocalAddress = false;
  
  @SerializedName("mcglobal")
  private Boolean mcglobal = false;
  
  @SerializedName("mcnodeLocal")
  private Boolean mcnodeLocal = false;
  
  @SerializedName("mclinkLocal")
  private Boolean mclinkLocal = false;
  
  @SerializedName("mcsiteLocal")
  private Boolean mcsiteLocal = false;
  
  @SerializedName("mcorgLocal")
  private Boolean mcorgLocal = false;
  
  @SerializedName("canonicalHostName")
  private String canonicalHostName = null;
  
  @SerializedName("address")
  private List<String> address = new ArrayList<String>();
  
  @SerializedName("hostAddress")
  private String hostAddress = null;
  
  @SerializedName("hostName")
  private String hostName = null;
  

  
  /**
   **/
  @ApiModelProperty(value = "")
  public Boolean getMulticastAddress() {
    return multicastAddress;
  }
  public void setMulticastAddress(Boolean multicastAddress) {
    this.multicastAddress = multicastAddress;
  }

  
  /**
   **/
  @ApiModelProperty(value = "")
  public Boolean getAnyLocalAddress() {
    return anyLocalAddress;
  }
  public void setAnyLocalAddress(Boolean anyLocalAddress) {
    this.anyLocalAddress = anyLocalAddress;
  }

  
  /**
   **/
  @ApiModelProperty(value = "")
  public Boolean getLoopbackAddress() {
    return loopbackAddress;
  }
  public void setLoopbackAddress(Boolean loopbackAddress) {
    this.loopbackAddress = loopbackAddress;
  }

  
  /**
   **/
  @ApiModelProperty(value = "")
  public Boolean getLinkLocalAddress() {
    return linkLocalAddress;
  }
  public void setLinkLocalAddress(Boolean linkLocalAddress) {
    this.linkLocalAddress = linkLocalAddress;
  }

  
  /**
   **/
  @ApiModelProperty(value = "")
  public Boolean getSiteLocalAddress() {
    return siteLocalAddress;
  }
  public void setSiteLocalAddress(Boolean siteLocalAddress) {
    this.siteLocalAddress = siteLocalAddress;
  }

  
  /**
   **/
  @ApiModelProperty(value = "")
  public Boolean getMcglobal() {
    return mcglobal;
  }
  public void setMcglobal(Boolean mcglobal) {
    this.mcglobal = mcglobal;
  }

  
  /**
   **/
  @ApiModelProperty(value = "")
  public Boolean getMcnodeLocal() {
    return mcnodeLocal;
  }
  public void setMcnodeLocal(Boolean mcnodeLocal) {
    this.mcnodeLocal = mcnodeLocal;
  }

  
  /**
   **/
  @ApiModelProperty(value = "")
  public Boolean getMclinkLocal() {
    return mclinkLocal;
  }
  public void setMclinkLocal(Boolean mclinkLocal) {
    this.mclinkLocal = mclinkLocal;
  }

  
  /**
   **/
  @ApiModelProperty(value = "")
  public Boolean getMcsiteLocal() {
    return mcsiteLocal;
  }
  public void setMcsiteLocal(Boolean mcsiteLocal) {
    this.mcsiteLocal = mcsiteLocal;
  }

  
  /**
   **/
  @ApiModelProperty(value = "")
  public Boolean getMcorgLocal() {
    return mcorgLocal;
  }
  public void setMcorgLocal(Boolean mcorgLocal) {
    this.mcorgLocal = mcorgLocal;
  }

  
  /**
   **/
  @ApiModelProperty(value = "")
  public String getCanonicalHostName() {
    return canonicalHostName;
  }
  public void setCanonicalHostName(String canonicalHostName) {
    this.canonicalHostName = canonicalHostName;
  }

  
  /**
   **/
  @ApiModelProperty(value = "")
  public List<String> getAddress() {
    return address;
  }
  public void setAddress(List<String> address) {
    this.address = address;
  }

  
  /**
   **/
  @ApiModelProperty(value = "")
  public String getHostAddress() {
    return hostAddress;
  }
  public void setHostAddress(String hostAddress) {
    this.hostAddress = hostAddress;
  }

  
  /**
   **/
  @ApiModelProperty(value = "")
  public String getHostName() {
    return hostName;
  }
  public void setHostName(String hostName) {
    this.hostName = hostName;
  }

  

  @Override
  public String toString()  {
    StringBuilder sb = new StringBuilder();
    sb.append("class InetAddress {\n");
    
    sb.append("    multicastAddress: ").append(StringUtil.toIndentedString(multicastAddress)).append("\n");
    sb.append("    anyLocalAddress: ").append(StringUtil.toIndentedString(anyLocalAddress)).append("\n");
    sb.append("    loopbackAddress: ").append(StringUtil.toIndentedString(loopbackAddress)).append("\n");
    sb.append("    linkLocalAddress: ").append(StringUtil.toIndentedString(linkLocalAddress)).append("\n");
    sb.append("    siteLocalAddress: ").append(StringUtil.toIndentedString(siteLocalAddress)).append("\n");
    sb.append("    mcglobal: ").append(StringUtil.toIndentedString(mcglobal)).append("\n");
    sb.append("    mcnodeLocal: ").append(StringUtil.toIndentedString(mcnodeLocal)).append("\n");
    sb.append("    mclinkLocal: ").append(StringUtil.toIndentedString(mclinkLocal)).append("\n");
    sb.append("    mcsiteLocal: ").append(StringUtil.toIndentedString(mcsiteLocal)).append("\n");
    sb.append("    mcorgLocal: ").append(StringUtil.toIndentedString(mcorgLocal)).append("\n");
    sb.append("    canonicalHostName: ").append(StringUtil.toIndentedString(canonicalHostName)).append("\n");
    sb.append("    address: ").append(StringUtil.toIndentedString(address)).append("\n");
    sb.append("    hostAddress: ").append(StringUtil.toIndentedString(hostAddress)).append("\n");
    sb.append("    hostName: ").append(StringUtil.toIndentedString(hostName)).append("\n");
    sb.append("}");
    return sb.toString();
  }
}
