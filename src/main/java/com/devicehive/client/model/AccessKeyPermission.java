package com.devicehive.client.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.swagger.annotations.ApiModelProperty;


public class AccessKeyPermission   {
  
  @SerializedName("id")
  private Long id = null;

  @SerializedName("domains")
  private JsonStringWrapper domains = null;

  @SerializedName("subnets")
  private JsonStringWrapper subnets = null;

  @SerializedName("actions")
  private JsonStringWrapper actions = null;

  @SerializedName("networkIds")
  private JsonStringWrapper networkIds = null;

  @SerializedName("deviceGuids")
  private JsonStringWrapper deviceGuids = null;

  @SerializedName("entityVersion")
  private Long entityVersion = null;

  @SerializedName("actionsAsSet")
  private List<String> actionsAsSet = new ArrayList<String>();

  @SerializedName("deviceGuidsAsSet")
  private List<String> deviceGuidsAsSet = new ArrayList<String>();

  @SerializedName("networkIdsAsSet")
  private List<Long> networkIdsAsSet = new ArrayList<Long>();

  @SerializedName("domainsAsSet")
  private List<String> domainsAsSet = new ArrayList<String>();

  @SerializedName("subnetsAsSet")
  private List<Subnet> subnetsAsSet = new ArrayList<Subnet>();

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
  public JsonStringWrapper getDomains() {
    return domains;
  }
  public void setDomains(JsonStringWrapper domains) {
    this.domains = domains;
  }

  /**
   **/
  @ApiModelProperty(value = "")
  public JsonStringWrapper getSubnets() {
    return subnets;
  }
  public void setSubnets(JsonStringWrapper subnets) {
    this.subnets = subnets;
  }

  /**
   **/
  @ApiModelProperty(value = "")
  public JsonStringWrapper getActions() {
    return actions;
  }
  public void setActions(JsonStringWrapper actions) {
    this.actions = actions;
  }

  /**
   **/
  @ApiModelProperty(value = "")
  public JsonStringWrapper getNetworkIds() {
    return networkIds;
  }
  public void setNetworkIds(JsonStringWrapper networkIds) {
    this.networkIds = networkIds;
  }

  /**
   **/
  @ApiModelProperty(value = "")
  public JsonStringWrapper getDeviceGuids() {
    return deviceGuids;
  }
  public void setDeviceGuids(JsonStringWrapper deviceGuids) {
    this.deviceGuids = deviceGuids;
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

  /**
   **/
  @ApiModelProperty(value = "")
  public List<String> getActionsAsSet() {
    return actionsAsSet;
  }
  public void setActionsAsSet(List<String> actionsAsSet) {
    this.actionsAsSet = actionsAsSet;
  }

  /**
   **/
  @ApiModelProperty(value = "")
  public List<String> getDeviceGuidsAsSet() {
    return deviceGuidsAsSet;
  }
  public void setDeviceGuidsAsSet(List<String> deviceGuidsAsSet) {
    this.deviceGuidsAsSet = deviceGuidsAsSet;
  }

  /**
   **/
  @ApiModelProperty(value = "")
  public List<Long> getNetworkIdsAsSet() {
    return networkIdsAsSet;
  }
  public void setNetworkIdsAsSet(List<Long> networkIdsAsSet) {
    this.networkIdsAsSet = networkIdsAsSet;
  }

  /**
   **/
  @ApiModelProperty(value = "")
  public List<String> getDomainsAsSet() {
    return domainsAsSet;
  }
  public void setDomainsAsSet(List<String> domainsAsSet) {
    this.domainsAsSet = domainsAsSet;
  }

  /**
   **/
  @ApiModelProperty(value = "")
  public List<Subnet> getSubnetsAsSet() {
    return subnetsAsSet;
  }
  public void setSubnetsAsSet(List<Subnet> subnetsAsSet) {
    this.subnetsAsSet = subnetsAsSet;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AccessKeyPermission accessKeyPermission = (AccessKeyPermission) o;
    return Objects.equals(id, accessKeyPermission.id) &&
        Objects.equals(domains, accessKeyPermission.domains) &&
        Objects.equals(subnets, accessKeyPermission.subnets) &&
        Objects.equals(actions, accessKeyPermission.actions) &&
        Objects.equals(networkIds, accessKeyPermission.networkIds) &&
        Objects.equals(deviceGuids, accessKeyPermission.deviceGuids) &&
        Objects.equals(entityVersion, accessKeyPermission.entityVersion) &&
        Objects.equals(actionsAsSet, accessKeyPermission.actionsAsSet) &&
        Objects.equals(deviceGuidsAsSet, accessKeyPermission.deviceGuidsAsSet) &&
        Objects.equals(networkIdsAsSet, accessKeyPermission.networkIdsAsSet) &&
        Objects.equals(domainsAsSet, accessKeyPermission.domainsAsSet) &&
        Objects.equals(subnetsAsSet, accessKeyPermission.subnetsAsSet);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, domains, subnets, actions, networkIds, deviceGuids, entityVersion, actionsAsSet, deviceGuidsAsSet, networkIdsAsSet, domainsAsSet, subnetsAsSet);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AccessKeyPermission {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    domains: ").append(toIndentedString(domains)).append("\n");
    sb.append("    subnets: ").append(toIndentedString(subnets)).append("\n");
    sb.append("    actions: ").append(toIndentedString(actions)).append("\n");
    sb.append("    networkIds: ").append(toIndentedString(networkIds)).append("\n");
    sb.append("    deviceGuids: ").append(toIndentedString(deviceGuids)).append("\n");
    sb.append("    entityVersion: ").append(toIndentedString(entityVersion)).append("\n");
    sb.append("    actionsAsSet: ").append(toIndentedString(actionsAsSet)).append("\n");
    sb.append("    deviceGuidsAsSet: ").append(toIndentedString(deviceGuidsAsSet)).append("\n");
    sb.append("    networkIdsAsSet: ").append(toIndentedString(networkIdsAsSet)).append("\n");
    sb.append("    domainsAsSet: ").append(toIndentedString(domainsAsSet)).append("\n");
    sb.append("    subnetsAsSet: ").append(toIndentedString(subnetsAsSet)).append("\n");
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
