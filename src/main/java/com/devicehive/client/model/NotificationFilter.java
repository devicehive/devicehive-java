package com.devicehive.client.model;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;

public class NotificationFilter {

    private String notificationNames;
    private DateTime startTimestamp;
    private DateTime endTimestamp;
    private SortOrder sortOrder;
    private SortField sortField;

    public void setNotificationNames(String... notificationNames) {
        this.notificationNames = StringUtils.join(notificationNames, ",");
    }

    public String getNotificationNames() {
        return notificationNames;
    }

    public SortOrder getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(SortOrder sortOrder) {
        this.sortOrder = sortOrder;
    }

    public SortField getSortField() {
        return sortField;
    }

    public void setSortField(SortField sortField) {
        this.sortField = sortField;
    }


    public DateTime getStartTimestamp() {
        return startTimestamp;
    }

    public void setStartTimestamp(DateTime startTimestamp) {
        this.startTimestamp = startTimestamp;
    }

    public DateTime getEndTimestamp() {
        return endTimestamp;
    }

    public void setEndTimestamp(DateTime endTimestamp) {
        this.endTimestamp = endTimestamp;
    }

}
