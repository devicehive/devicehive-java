package com.devicehive.client.model;


import org.apache.commons.lang3.ObjectUtils;

import java.util.Date;
import java.util.Set;

/**
 * A container for subscription filtering parameters.
 */
public class SubscriptionFilter implements Cloneable {

    private final Set<String> uuids;
    private final Set<String> names;
    private volatile Date timestamp;

    public SubscriptionFilter(Set<String> uuids, Set<String> names, Date timestamp) {
        this.uuids = ObjectUtils.cloneIfPossible(uuids);
        this.names = ObjectUtils.cloneIfPossible(names);
        this.timestamp = ObjectUtils.cloneIfPossible(timestamp);
    }

    public Set<String> getUuids() {
        return ObjectUtils.cloneIfPossible(uuids);
    }

    public Set<String> getNames() {
        return ObjectUtils.cloneIfPossible(names);
    }

    public Date getTimestamp() {
        return ObjectUtils.cloneIfPossible(timestamp);
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = ObjectUtils.cloneIfPossible(timestamp);
    }
}
