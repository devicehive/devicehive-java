package com.devicehive.client.model;

import com.devicehive.client.DeviceHive;
import com.devicehive.rest.model.JsonStringWrapper;
import org.joda.time.DateTime;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.util.List;

public class Device implements DeviceInterface {

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

    private String id = null;

    private String name = null;

    private JsonStringWrapper data = null;

    private Long networkId = null;

    private Boolean isBlocked = false;

    private DeviceHive deviceHive;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public JsonStringWrapper getData() {
        return data;
    }

    public void setData(JsonStringWrapper data) {
        this.data = data;
    }

    public Long getNetworkId() {
        return networkId;
    }

    public void setNetworkId(Long networkId) {
        this.networkId = networkId;
    }

    public Boolean getBlocked() {
        return isBlocked;
    }

    public void setBlocked(Boolean blocked) {
        isBlocked = blocked;
    }

    public void save() throws IOException {
        DeviceHive.getInstance().putDevice(id, name);
    }

    public List<DeviceCommand> getCommands(DateTime startTimestamp, DateTime endTimestamp, int maxNumber) throws IOException {
        return DeviceHive.getInstance().getDeviceCommandService()
                .getDeviceCommands(this.id, startTimestamp, endTimestamp, maxNumber).getData();
    }

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
