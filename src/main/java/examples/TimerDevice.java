package examples;

import com.devicehive.client.ApiClient;
import com.devicehive.client.api.ApiInfoApi;
import com.devicehive.client.api.DeviceApi;
import com.devicehive.client.api.DeviceCommandApi;
import com.devicehive.client.api.DeviceNotificationApi;
import com.devicehive.client.api.NetworkApi;
import com.devicehive.client.model.ApiInfo;
import com.devicehive.client.model.DeviceClassUpdate;
import com.devicehive.client.model.DeviceCommand;
import com.devicehive.client.model.DeviceNotificationWrapper;
import com.devicehive.client.model.DeviceUpdate;
import com.devicehive.client.model.JsonStringWrapper;
import com.devicehive.client.model.Network;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import org.joda.time.DateTime;

class TimerDevice {

  private DateTime alarmTime = null;
  private DateTime currentTime = null;

  private ApiClient restClient;

  private ScheduledExecutorService ses;
  private DeviceNotificationApi notificationApiImpl;
  private DeviceApi deviceApiImpl;
  private ApiInfoApi infoApiImpl;
  private DeviceCommandApi commandApiImpl;
  private NetworkApi networkApiImpl;

  private DateTime timestamp;

  public TimerDevice() {
    restClient = new ApiClient(Const.URL, ApiClient.AUTH_API_KEY, Const.API_KEY);
    ses = Executors.newScheduledThreadPool(3);
    inflateApi();
  }

  private void inflateApi() {
    notificationApiImpl = restClient.createService(DeviceNotificationApi.class);
    commandApiImpl = restClient.createService(DeviceCommandApi.class);
    infoApiImpl = restClient.createService(ApiInfoApi.class);
    deviceApiImpl = restClient.createService(DeviceApi.class);
    networkApiImpl = restClient.createService(NetworkApi.class);
  }

  void run() throws IOException {

    registerDevice();

    ApiInfo apiInfo = infoApiImpl.getApiInfo().execute().body();

    timestamp = apiInfo.getServerTimestamp();
    //Send current timestamp notification
    ses.scheduleAtFixedRate(new Runnable() {
      @Override public void run() {
        DeviceNotificationWrapper myDevice = getTimestampNotificationWrapper();
        try {
          notificationApiImpl.insert(Const.DEVICE_ID, myDevice).execute();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }, 0, 2, TimeUnit.SECONDS);

    //Commands Polling
    ses.scheduleAtFixedRate(new Runnable() {
      @Override public void run() {
        List<DeviceCommand> commands = null;
        try {
          commands = commandApiImpl.poll(Const.DEVICE_ID, "ON", timestamp.toString(), 30L)
              .execute()
              .body();
        } catch (IOException e) {
          e.printStackTrace();
        }

        if (commands != null && commands.size() != 0) {
          Collections.sort(commands);
          DeviceCommand command = commands.get(commands.size() - 1);
          updateTimestamp(command.getTimestamp());
          String deviceParams = command.getParameters();
          alarmTime = DateTime.parse(deviceParams).withMillisOfSecond(0);
        }
      }
    }, 0, 1, TimeUnit.SECONDS);

    //Send Alarm Notification
    ses.scheduleAtFixedRate(new Runnable() {
      @Override public void run() {
        currentTime = DateTime.now().withMillisOfSecond(0);
        DeviceNotificationWrapper alarmNotification = createAlarmNotificationWrapper();
        if (isAlarmTime()) {
          try {
            notificationApiImpl.insert(Const.DEVICE_ID, alarmNotification).execute().body();
          } catch (IOException e) {
            e.printStackTrace();
          }
        }
      }
    }, 0, 1, TimeUnit.SECONDS);
  }

  //Adds new device or updates current device

  private void registerDevice() {
    DeviceUpdate device = createDevice();
    try {
      List<Network> networks = networkApiImpl.list(null, null, null, null, null, null).
          execute().body();
      if (networks != null && !networks.isEmpty()) {
        device.setNetwork(networks.get(0));
        deviceApiImpl.register(device, Const.DEVICE_ID).execute().raw().toString();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private DeviceUpdate createDevice() {
    DeviceUpdate device = new DeviceUpdate();
    device.setName(Const.NAME);
    device.setStatus(Const.STATUS);
    device.setGuid(Const.DEVICE_ID);

    DeviceClassUpdate deviceClass = new DeviceClassUpdate();
    deviceClass.setName(Const.DC_NAME);
    deviceClass.setVersion(Const.DC_VERSION);
    device.setDeviceClass(deviceClass);

    return device;
  }

  private boolean isAlarmTime() {
    return alarmTime != null && alarmTime.isEqual(currentTime);
  }

  private DeviceNotificationWrapper getTimestampNotificationWrapper() {
    DeviceNotificationWrapper wrapper = new DeviceNotificationWrapper();
    JsonStringWrapper jsonStringWrapper = new JsonStringWrapper();
    DateTime currentTimestamp = DateTime.now();
    jsonStringWrapper.setJsonString(currentTimestamp.toString());
    wrapper.setParameters(jsonStringWrapper);
    wrapper.setNotification("Timestamp");
    return wrapper;
  }

  private DeviceNotificationWrapper createAlarmNotificationWrapper() {
    DeviceNotificationWrapper wrapper = new DeviceNotificationWrapper();
    JsonStringWrapper jsonStringWrapper = new JsonStringWrapper();
    jsonStringWrapper.setJsonString("BIP BIP BIP at " + alarmTime);
    wrapper.setParameters(jsonStringWrapper);
    wrapper.setNotification("ALARM");
    return wrapper;
  }

  private void updateTimestamp(DateTime timestamp) {
    if (this.timestamp == null) {
      this.timestamp = timestamp;
    } else if (this.timestamp.isBefore(timestamp)) {
      this.timestamp = timestamp;
    }
  }
}
