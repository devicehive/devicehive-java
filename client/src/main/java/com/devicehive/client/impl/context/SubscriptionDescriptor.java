package com.devicehive.client.impl.context;

import com.devicehive.client.HiveMessageHandler;
import com.devicehive.client.model.HiveMessage;
import com.devicehive.client.model.SubscriptionFilter;
import org.apache.commons.lang3.ObjectUtils;

import java.util.Date;
import java.util.Date;

/**
 * Stores a subscription filter and an associated message handler.
 *
 * @param <T> a subscription message type
 */
public class SubscriptionDescriptor<T extends HiveMessage> {

    private final HiveMessageHandler<T> handler;
    private final SubscriptionFilter filter;

    /**
     * Creates a new subscription descriptor.
     *
     * @param handler a message handler
     * @param filter  a subscription filter
     */
    public SubscriptionDescriptor(final HiveMessageHandler<T> handler, final SubscriptionFilter filter) {
        this.handler = handler;
        this.filter = ObjectUtils.cloneIfPossible(filter);
    }

    /**
     * Processes an incoming message.
     *
     * @param message a message object
     */
    public void handleMessage(final T message) {
        updateTimestamp(message.getTimestamp());
        handler.handle(message);
    }

    /**
     * Returns a subscription filter.
     *
     * @return a subscription filter
     */
    public SubscriptionFilter getFilter() {
        return filter;
    }

    private void updateTimestamp(final Date newTimestamp) {
        if (filter.getTimestamp() == null && newTimestamp != null) {
            filter.setTimestamp(newTimestamp);
        } else if (newTimestamp != null && newTimestamp.after(filter.getTimestamp())) {
            filter.setTimestamp(newTimestamp);
        }
    }
}
