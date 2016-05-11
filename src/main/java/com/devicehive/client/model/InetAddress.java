package com.devicehive.client.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.swagger.annotations.ApiModelProperty;


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
  private List<byte[]> address = new ArrayList<byte[]>();

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
  public List<byte[]> getAddress() {
    return address;
  }
  public void setAddress(List<byte[]> address) {
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
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    InetAddress inetAddress = (InetAddress) o;
    return Objects.equals(multicastAddress, inetAddress.multicastAddress) &&
        Objects.equals(anyLocalAddress, inetAddress.anyLocalAddress) &&
        Objects.equals(loopbackAddress, inetAddress.loopbackAddress) &&
        Objects.equals(linkLocalAddress, inetAddress.linkLocalAddress) &&
        Objects.equals(siteLocalAddress, inetAddress.siteLocalAddress) &&
        Objects.equals(mcglobal, inetAddress.mcglobal) &&
        Objects.equals(mcnodeLocal, inetAddress.mcnodeLocal) &&
        Objects.equals(mclinkLocal, inetAddress.mclinkLocal) &&
        Objects.equals(mcsiteLocal, inetAddress.mcsiteLocal) &&
        Objects.equals(mcorgLocal, inetAddress.mcorgLocal) &&
        Objects.equals(canonicalHostName, inetAddress.canonicalHostName) &&
        Objects.equals(address, inetAddress.address) &&
        Objects.equals(hostAddress, inetAddress.hostAddress) &&
        Objects.equals(hostName, inetAddress.hostName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(multicastAddress, anyLocalAddress, loopbackAddress, linkLocalAddress, siteLocalAddress, mcglobal, mcnodeLocal, mclinkLocal, mcsiteLocal, mcorgLocal, canonicalHostName, address, hostAddress, hostName);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class InetAddress {\n");
    
    sb.append("    multicastAddress: ").append(toIndentedString(multicastAddress)).append("\n");
    sb.append("    anyLocalAddress: ").append(toIndentedString(anyLocalAddress)).append("\n");
    sb.append("    loopbackAddress: ").append(toIndentedString(loopbackAddress)).append("\n");
    sb.append("    linkLocalAddress: ").append(toIndentedString(linkLocalAddress)).append("\n");
    sb.append("    siteLocalAddress: ").append(toIndentedString(siteLocalAddress)).append("\n");
    sb.append("    mcglobal: ").append(toIndentedString(mcglobal)).append("\n");
    sb.append("    mcnodeLocal: ").append(toIndentedString(mcnodeLocal)).append("\n");
    sb.append("    mclinkLocal: ").append(toIndentedString(mclinkLocal)).append("\n");
    sb.append("    mcsiteLocal: ").append(toIndentedString(mcsiteLocal)).append("\n");
    sb.append("    mcorgLocal: ").append(toIndentedString(mcorgLocal)).append("\n");
    sb.append("    canonicalHostName: ").append(toIndentedString(canonicalHostName)).append("\n");
    sb.append("    address: ").append(toIndentedString(address)).append("\n");
    sb.append("    hostAddress: ").append(toIndentedString(hostAddress)).append("\n");
    sb.append("    hostName: ").append(toIndentedString(hostName)).append("\n");
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
