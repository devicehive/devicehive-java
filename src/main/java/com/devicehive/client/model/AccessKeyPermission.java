package com.devicehive.client.model;

import com.devicehive.client.StringUtil;
import com.google.gson.annotations.SerializedName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;



@ApiModel(description = "")
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
  public String toString()  {
    StringBuilder sb = new StringBuilder();
    sb.append("class AccessKeyPermission {\n");
    
    sb.append("    id: ").append(StringUtil.toIndentedString(id)).append("\n");
    sb.append("    domains: ").append(StringUtil.toIndentedString(domains)).append("\n");
    sb.append("    subnets: ").append(StringUtil.toIndentedString(subnets)).append("\n");
    sb.append("    actions: ").append(StringUtil.toIndentedString(actions)).append("\n");
    sb.append("    networkIds: ").append(StringUtil.toIndentedString(networkIds)).append("\n");
    sb.append("    deviceGuids: ").append(StringUtil.toIndentedString(deviceGuids)).append("\n");
    sb.append("    entityVersion: ").append(StringUtil.toIndentedString(entityVersion)).append("\n");
    sb.append("    actionsAsSet: ").append(StringUtil.toIndentedString(actionsAsSet)).append("\n");
    sb.append("    deviceGuidsAsSet: ").append(StringUtil.toIndentedString(deviceGuidsAsSet)).append("\n");
    sb.append("    networkIdsAsSet: ").append(StringUtil.toIndentedString(networkIdsAsSet)).append("\n");
    sb.append("    domainsAsSet: ").append(StringUtil.toIndentedString(domainsAsSet)).append("\n");
    sb.append("    subnetsAsSet: ").append(StringUtil.toIndentedString(subnetsAsSet)).append("\n");
    sb.append("}");
    return sb.toString();
  }
}
