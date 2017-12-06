package com.github.devicehive.websocket;

import com.github.devicehive.rest.model.Device;
import com.github.devicehive.rest.model.DeviceUpdate;
import com.github.devicehive.rest.model.NetworkUpdate;
import com.github.devicehive.websocket.api.AuthListener;
import com.github.devicehive.websocket.api.ConfigurationWS;
import com.github.devicehive.websocket.api.DeviceWS;
import com.github.devicehive.websocket.api.NetworkWS;
import com.github.devicehive.websocket.api.TokenWS;
import com.github.devicehive.websocket.api.WebSocketClient;
import com.github.devicehive.websocket.listener.ConfigurationListener;
import com.github.devicehive.websocket.listener.DeviceListener;
import com.github.devicehive.websocket.listener.TokenListener;
import com.github.devicehive.websocket.model.repsonse.ConfigurationGetResponse;
import com.github.devicehive.websocket.model.repsonse.ConfigurationInsertResponse;
import com.github.devicehive.websocket.model.repsonse.ErrorResponse;
import com.github.devicehive.websocket.model.repsonse.ResponseAction;
import com.github.devicehive.websocket.model.repsonse.TokenGetResponse;
import com.github.devicehive.websocket.model.repsonse.TokenRefreshResponse;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

class Helper {
  static final String HTTP_URL = "http://playground.dev.devicehive.com/api/rest";
  private static final String URL = "ws://playground.dev.devicehive.com/api/websocket";
  String accessToken =
      "eyJhbGciOiJIUzI1NiJ9.eyJwYXlsb2FkIjp7InUiOjEsImEiOlswXSwibiI6WyIqIl0sImQiOlsiKiJdLCJlIjoxNTA5NzIwMDQwNzE4LCJ0IjoxfX0.G_u8MiEKDKnWJdplfBo6_MI5BNyupaOLsg46PsdNRa8";
  static final String REFRESH_TOKEN =
      "eyJhbGciOiJIUzI1NiJ9.eyJwYXlsb2FkIjp7InUiOjEsImEiOlswXSwibiI6WyIqIl0sImQiOlsiKiJdLCJlIjoxNTI0MTUxMjAxMTA4LCJ0IjowfX0.2wfpmIjrHRtGBoSF3-T77aSAiUYPFSGtgBuGoVZtSxc";

  int awaitTimeout = 10;
  TimeUnit awaitTimeUnit = TimeUnit.SECONDS;

  WebSocketClient client = new WebSocketClient.Builder().url(URL).build();

  private TokenWS tokenWS = client.createTokenWS();
  private DeviceWS deviceWS = client.createDeviceWS();

  void authenticate() throws InterruptedException {
    final CountDownLatch latch = new CountDownLatch(1);
    client.setListener(new AuthListener() {
      @Override public void onSuccess(ResponseAction responseAction) {
        latch.countDown();
      }

      @Override public void onError(ErrorResponse error) {
        refresh(latch);
      }
    });

    if (accessToken != null && !accessToken.isEmpty()) {
      client.authenticate(accessToken);
    } else {
      refresh(latch);
    }
    latch.await(awaitTimeout, awaitTimeUnit);
  }

  private void refresh(final CountDownLatch latch) {
    tokenWS.setListener(new TokenListener() {
      @Override public void onGet(TokenGetResponse response) {

      }

      @Override public void onCreate(TokenGetResponse response) {

      }

      @Override public void onRefresh(TokenRefreshResponse response) {
        latch.countDown();
        accessToken = response.getAccessToken();
        client.authenticate(accessToken);
      }

      @Override public void onError(ErrorResponse error) {
        latch.countDown();
      }
    });
    tokenWS.refresh(null, REFRESH_TOKEN);
  }

  void deleteConfiguration(ConfigurationWS configurationWS, String name)
      throws InterruptedException {
    final CountDownLatch latch = new CountDownLatch(1);
    configurationWS.setListener(new ConfigurationListener() {
      @Override public void onGet(ConfigurationGetResponse response) {

      }

      @Override public void onPut(ConfigurationInsertResponse response) {

      }

      @Override public void onDelete(ResponseAction response) {
        latch.countDown();
      }

      @Override public void onError(ErrorResponse error) {

      }
    });

    configurationWS.delete(null, name);
    latch.await(awaitTimeout, awaitTimeUnit);
  }

  void registerDevice(DeviceWS deviceWS, String deviceId, String deviceName, Long networkId)
      throws InterruptedException {
    DeviceUpdate deviceUpdate = new DeviceUpdate();
    deviceUpdate.setName(deviceName);
    deviceUpdate.setNetworkId(networkId);

    deviceWS.save(null, deviceId, deviceUpdate);
  }

  void registerDevice(String deviceId) throws InterruptedException {
    authenticate();
    final CountDownLatch latch = new CountDownLatch(1);
    final AtomicBoolean atomicBoolean = new AtomicBoolean(false);
    DeviceUpdate deviceUpdate = new DeviceUpdate();
    deviceUpdate.setName(deviceId);
    deviceWS.save(null, deviceId, deviceUpdate);
    deviceWS.setListener(new DeviceListener() {
      @Override public void onList(List<Device> response) {

      }

      @Override public void onGet(Device response) {

      }

      @Override public void onDelete(ResponseAction response) {

      }

      @Override public void onSave(ResponseAction response) {
        atomicBoolean.set(true);
        latch.countDown();
      }

      @Override public void onError(ErrorResponse error) {

      }
    });
    latch.await(awaitTimeout, TimeUnit.SECONDS);
    deviceWS.setListener(null);
  }

  void deleteDevice(String id) {
    deviceWS.delete(null, id);
  }

  public NetworkUpdate getNetworkUpdate(String networkName) {
    NetworkUpdate networkUpdate = new NetworkUpdate();
    networkUpdate.setName(networkName);
    return networkUpdate;
  }

  void deleteNetwork(NetworkWS networkWS, long id) {
    networkWS.delete(null, id);
  }
}