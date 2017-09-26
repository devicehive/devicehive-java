package com.devicehive.client.model;

import com.devicehive.client.DeviceHive;
import com.devicehive.rest.model.JsonStringWrapper;
import lombok.Data;
import org.joda.time.DateTime;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.util.List;

@Data
public class Device implements DeviceInterface {

    private Device() {
    }

    public Device(@Nonnull String id, @Nonnull String name) {
        this.id = id;
        this.name = name;
    }

    public static Device createDeviceFromRestResponse(com.devicehive.rest.model.Device device) {
        Device result = new Device();
        result.id = device.getId();
        result.name = device.getName();
        result.data = device.getData();
        result.networkId = device.getNetworkId();
        result.isBlocked = device.getIsBlocked();
        return result;
    }

    private String id = null;

    private String name = null;

    private JsonStringWrapper data = null;

    private Long networkId = null;

    private Boolean isBlocked = false;

    private DeviceHive deviceHive;

    public void setDeviceHive(DeviceHive deviceHive) {
        this.deviceHive = deviceHive;
    }

    public void save() {
        try {
            DeviceHive.getInstance().putDevice(id, name);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<DeviceCommand> getCommands(DateTime startTimestamp, DateTime endTimestamp, int maxNumber) {
        return null;
    }

    public List<DeviceNotification> getNotifications(DateTime startTimestamp, DateTime endTimestamp, int maxNumber) {
        return null;
    }

    public DeviceCommand sendCommand(DeviceCommand command, String parameters, DeviceCommandCallback resultCallback) {
        return null;
    }

    public DeviceNotification sendNotification(String notification, String parameters) {
        return null;
    }

    public void subscribeCommands(DeviceCommandCallback callback, CommandFilter commandFilter) {

    }

    public void subscribeNotifications(DeviceNotificationCallback callback, NameFilter nameFilter) {

    }

    public void unsubscribeCommands(CommandFilter commandFilter) {

    }

    public void unsubscribeNotifications(NameFilter nameFilter) {

    }
}
