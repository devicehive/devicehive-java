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


    private String id = null;

    private String name = null;

    private JsonStringWrapper data = null;

    private Long networkId = null;

    private Boolean isBlocked = false;

    private Device() {
    }

    public Device(@Nonnull String id, @Nonnull String name) {
        this.id = id;
        this.name = name;
    }

    public static Device createDeviceFromRestResponse(com.devicehive.rest.model.Device device) {
        if (device == null) {
            return null;
        }
        Device result = new Device();
        result.id = device.getId();
        result.name = device.getName();
        result.data = device.getData();
        result.networkId = device.getNetworkId();
        result.isBlocked = device.getIsBlocked();
        return result;
    }

    public void save() throws IOException {
        DeviceHive.getInstance().putDevice(id, name);
    }

    public List<DeviceCommand> getCommands(DateTime startTimestamp, DateTime endTimestamp, int maxNumber) throws IOException {
        return DeviceHive.getInstance().getDeviceCommandService()
                .getDeviceCommands(this.id, startTimestamp, endTimestamp, maxNumber).getData();
    }
//TODO make me please :*(
    public List<DeviceNotification> getNotifications(DateTime startTimestamp, DateTime endTimestamp, int maxNumber) {
        return null;
    }

    public DeviceCommandCallback sendCommand(String command, List<Parameter> parameters) throws IOException {
        DeviceCommandCallback resultCallback = new DeviceCommandCallback() {
            public void onSuccess() {

            }

            public void onFail() {

            }
        };
        DeviceHive.getInstance().getDeviceCommandService()
                .sendCommand(id, command, parameters, resultCallback);
        return resultCallback;
    }

    public DHResponse<DeviceNotification> sendNotification(String notification, List<Parameter> parameters) throws IOException {
        return DeviceHive.getInstance().getDeviceNotificationService().sendNotification(id, notification,
                parameters);
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
