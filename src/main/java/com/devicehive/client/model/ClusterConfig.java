package com.devicehive.client.model;


import com.devicehive.client.StringUtil;
import com.google.gson.annotations.SerializedName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;



@ApiModel(description = "")
public class ClusterConfig   {
  
  @SerializedName("metadataBrokerList")
  private String metadataBrokerList = null;
  
  @SerializedName("zookeeperConnect")
  private String zookeeperConnect = null;
  
  @SerializedName("threadsCount")
  private Integer threadsCount = null;
  

  
  /**
   **/
  @ApiModelProperty(value = "")
  public String getMetadataBrokerList() {
    return metadataBrokerList;
  }
  public void setMetadataBrokerList(String metadataBrokerList) {
    this.metadataBrokerList = metadataBrokerList;
  }

  
  /**
   **/
  @ApiModelProperty(value = "")
  public String getZookeeperConnect() {
    return zookeeperConnect;
  }
  public void setZookeeperConnect(String zookeeperConnect) {
    this.zookeeperConnect = zookeeperConnect;
  }

  
  /**
   **/
  @ApiModelProperty(value = "")
  public Integer getThreadsCount() {
    return threadsCount;
  }
  public void setThreadsCount(Integer threadsCount) {
    this.threadsCount = threadsCount;
  }

  

  @Override
  public String toString()  {
    StringBuilder sb = new StringBuilder();
    sb.append("class ClusterConfig {\n");
    
    sb.append("    metadataBrokerList: ").append(StringUtil.toIndentedString(metadataBrokerList)).append("\n");
    sb.append("    zookeeperConnect: ").append(StringUtil.toIndentedString(zookeeperConnect)).append("\n");
    sb.append("    threadsCount: ").append(StringUtil.toIndentedString(threadsCount)).append("\n");
    sb.append("}");
    return sb.toString();
  }
}
