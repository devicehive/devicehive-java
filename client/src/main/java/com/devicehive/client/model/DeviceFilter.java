package com.devicehive.client.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DeviceFilter {

    String name;
    String namePattern;
    Long networkId;
    String networkName;
    SortField sortField;
    SortOrder sortOrder = SortOrder.ASC;
    int take = 20;
    int skip;


}
