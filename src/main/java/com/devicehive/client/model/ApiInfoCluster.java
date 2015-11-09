package com.devicehive.client.model;

import com.devicehive.client.StringUtil;
import com.google.gson.annotations.SerializedName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "")
public class ApiInfoCluster {

    @SerializedName("metadata.broker.list")
    private String metadataBrokerList = null;

    @SerializedName("zookeeper.connect")
    private String zookeeperConnect = null;

    @SerializedName("threads.count")
    private Integer threadsCount = null;

    @SerializedName("cassandra.contactpoints")
    private String cassandraContactpoints = null;

    @ApiModelProperty(value = "")
    public String getMetadataBrokerList() {
        return metadataBrokerList;
    }

    public void setMetadataBrokerList(String metadataBrokerList) {
        this.metadataBrokerList = metadataBrokerList;
    }

    @ApiModelProperty(value = "")
    public String getZookeeperConnect() {
        return zookeeperConnect;
    }

    public void setZookeeperConnect(String zookeeperConnect) {
        this.zookeeperConnect = zookeeperConnect;
    }

    @ApiModelProperty(value = "")
    public Integer getThreadsCount() {
        return threadsCount;
    }

    public void setThreadsCount(Integer threadsCount) {
        this.threadsCount = threadsCount;
    }

    @ApiModelProperty(value = "")
    public String getCassandraContactpoints() {
        return cassandraContactpoints;
    }

    public void setCassandraContactpoints(String cassandraContactpoints) {
        this.cassandraContactpoints = cassandraContactpoints;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class ApiInfoCluster {\n");

        sb.append("    metadata.broker.list: ").append(StringUtil.toIndentedString(metadataBrokerList)).append("\n");
        sb.append("    zookeeper.connect: ").append(StringUtil.toIndentedString(zookeeperConnect)).append("\n");
        sb.append("    threads.count: ").append(StringUtil.toIndentedString(threadsCount)).append("\n");
        sb.append("    cassandra.contactpoints: ").append(StringUtil.toIndentedString(cassandraContactpoints)).append("\n");
        sb.append("}");
        return sb.toString();
    }
}
