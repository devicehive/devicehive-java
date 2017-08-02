package com.devicehive.websocket.model.request;

import com.devicehive.websocket.model.SortOrder;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import org.joda.time.DateTime;

import static com.devicehive.websocket.model.ActionConstant.NOTIFICATION_LIST;

@Data
public class NotificationListAction extends RequestAction {

    @SerializedName("deviceId")
    private String deviceId;
    @SerializedName("start")
    private DateTime start;
    @SerializedName("end")
    private DateTime end;
    @SerializedName("notification")
    private String notification;
    @SerializedName("sortField")
    private String sortField;
    @SerializedName("sortOrder")
    private SortOrder sortOrder;
    @SerializedName("take")
    private Integer take;
    @SerializedName("skip")
    private Integer skip;

    public NotificationListAction() {
        super(NOTIFICATION_LIST);
    }
}
