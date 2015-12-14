package com.devicehive.client.model;

import com.devicehive.client.websocket.model.HiveEntity;
import org.joda.time.DateTime;

/**
 * Created by stas on 05.07.14.
 */
public interface HiveMessage extends HiveEntity {

    DateTime getTimestamp();
}
