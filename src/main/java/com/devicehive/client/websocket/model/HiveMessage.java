package com.devicehive.client.websocket.model;

import java.util.Date;

/**
 * Created by stas on 05.07.14.
 */
public interface HiveMessage extends HiveEntity {

    public Date getTimestamp();
}
