package com.devicehive.service;

import com.devicehive.auth.HivePrincipal;
import com.devicehive.dao.DeviceDAO;
import com.devicehive.dao.DeviceNotificationDAO;
import com.devicehive.messages.bus.GlobalMessageBus;
import com.devicehive.model.Device;
import com.devicehive.model.DeviceNotification;
import com.devicehive.model.SpecialNotifications;
import com.devicehive.util.LogExecutionTime;
import com.devicehive.util.ServerResponsesFactory;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Stateless
@LogExecutionTime
public class DeviceNotificationService {
    private DeviceNotificationDAO deviceNotificationDAO;
    private DeviceEquipmentService deviceEquipmentService;
    private GlobalMessageBus globalMessageBus;
    private DeviceNotificationService self;
    private DeviceDAO deviceDAO;
    private DeviceService deviceService;

    @EJB
    public void setDeviceNotificationDAO(DeviceNotificationDAO deviceNotificationDAO) {
        this.deviceNotificationDAO = deviceNotificationDAO;
    }

    @EJB
    public void setDeviceEquipmentService(DeviceEquipmentService deviceEquipmentService) {
        this.deviceEquipmentService = deviceEquipmentService;
    }

    @EJB
    public void setGlobalMessageBus(GlobalMessageBus globalMessageBus) {
        this.globalMessageBus = globalMessageBus;
    }

    @EJB
    public void setSelf(DeviceNotificationService self) {
        this.self = self;
    }

    @EJB
    public void setDeviceDAO(DeviceDAO deviceDAO) {
        this.deviceDAO = deviceDAO;
    }

    @EJB
    public void setDeviceService(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<DeviceNotification> getDeviceNotificationList(Collection<String> devices, Collection<String> names,
                                                              Timestamp timestamp,
                                                              HivePrincipal principal) {
        if (devices != null) {
            return deviceNotificationDAO.findNotifications(
                    deviceService.findByGuidWithPermissionsCheck(devices, principal), names, timestamp, null);
        } else {
            return deviceNotificationDAO.findNotifications(null, names, timestamp, principal);
        }
    }

    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public DeviceNotification findById(@NotNull long id) {
        return deviceNotificationDAO.findById(id);
    }

    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<DeviceNotification> queryDeviceNotification(Device device,
                                                            Timestamp start,
                                                            Timestamp end,
                                                            String notification,
                                                            String sortField,
                                                            Boolean sortOrderAsc,
                                                            Integer take,
                                                            Integer skip,
                                                            Integer gridInterval) {
        return deviceNotificationDAO
                .queryDeviceNotification(device, start, end, notification, sortField, sortOrderAsc, take, skip,
                        gridInterval);
    }


    //device should be already set

    public List<DeviceNotification> saveDeviceNotification(List<DeviceNotification> notifications) {
        for (DeviceNotification notification : notifications) {
            deviceNotificationDAO.createNotification(notification);
        }
        return notifications;
    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public void submitDeviceNotification(DeviceNotification notification, Device device) {
        List<DeviceNotification> proceedNotifications = self.processDeviceNotification(notification, device);
        for (DeviceNotification currentNotification : proceedNotifications) {
            globalMessageBus.publishDeviceNotification(currentNotification);
        }
    }

    public List<DeviceNotification> processDeviceNotification(DeviceNotification notification, Device device) {
        List<DeviceNotification> notificationsToCreate = new ArrayList<>();
        switch (notification.getNotification()) {
            case SpecialNotifications.EQUIPMENT:
                deviceEquipmentService.refreshDeviceEquipment(notification, device);
                break;
            case SpecialNotifications.DEVICE_STATUS:
                notificationsToCreate.add(refreshDeviceStatusCase(notification, device));
                break;
            default:
                break;

        }
        notification.setDevice(device);
        notificationsToCreate.add(notification);
        return saveDeviceNotification(notificationsToCreate);

    }

    public DeviceNotification refreshDeviceStatusCase(DeviceNotification notification, Device device) {
        device = deviceDAO.findByUUIDWithNetworkAndDeviceClass(device.getGuid());
        String status = ServerResponsesFactory.parseNotificationStatus(notification);
        device.setStatus(status);
        return ServerResponsesFactory.createNotificationForDevice(device, SpecialNotifications.DEVICE_UPDATE);
    }


}
