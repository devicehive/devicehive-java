/*
 *
 *
 *   NotificationFilter.java
 *
 *   Copyright (C) 2018 DataArt
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
 */

package com.github.devicehive.client.model;

import com.github.devicehive.rest.model.SortField;
import com.github.devicehive.rest.model.SortOrder;
import org.joda.time.DateTime;

import java.util.Arrays;
import java.util.List;

public class NotificationFilter {

    private List<String> notificationNames;
    private List<String> deviceTypesIds;
    private List<String> networkIds;
    private DateTime startTimestamp;
    private DateTime endTimestamp;
    private SortOrder sortOrder;
    private SortField sortField;

    public void setNotificationNames(String... notificationNames) {
        this.notificationNames = Arrays.asList(notificationNames);

    }

    public List<String> getNotificationNames() {
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
        return startTimestamp == null ? null : startTimestamp.withMillisOfSecond(0);
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

    public void setDeviceTypeIds(String... notificationNames) {
        this.notificationNames = Arrays.asList(notificationNames);

    }
    public void setNetworkIds(String... notificationNames) {
        this.notificationNames = Arrays.asList(notificationNames);

    }

    public List<String> getDeviceTypesIds() {
        return deviceTypesIds;
    }

    public List<String> getNetworkIds() {
        return networkIds;
    }
}
