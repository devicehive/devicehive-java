package com.devicehive.client.websocket.context;


import org.apache.commons.lang3.ObjectUtils;
import org.joda.time.DateTime;

import java.util.Set;

/**
 * A container for subscription filtering parameters.
 */
public class SubscriptionFilter {

    private final Set<String> uuids;
    private final Set<String> names;
    private volatile DateTime timestamp;

    public SubscriptionFilter(Set<String> uuids, Set<String> names, DateTime timestamp) {
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

    public DateTime getTimestamp() {
        return ObjectUtils.cloneIfPossible(timestamp);
    }

    public void setTimestamp(DateTime timestamp) {
        this.timestamp = ObjectUtils.cloneIfPossible(timestamp);
    }


    @Override
    protected Object clone() throws CloneNotSupportedException {
        super.clone();
        return new SubscriptionFilter(uuids,names,timestamp);
    }
}
