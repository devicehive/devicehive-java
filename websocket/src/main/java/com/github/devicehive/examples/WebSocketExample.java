/*
 *
 *
 *   WebSocketExample.java
 *
 *   Copyright (C) 2018 DataArt
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
 */

package com.github.devicehive.examples;

import com.github.devicehive.rest.model.Device;
import com.github.devicehive.websocket.api.CommandWS;
import com.github.devicehive.websocket.api.DeviceWS;
import com.github.devicehive.websocket.api.WebSocketClient;
import com.github.devicehive.websocket.listener.CommandListener;
import com.github.devicehive.websocket.listener.DeviceListener;
import com.github.devicehive.websocket.model.repsonse.*;

import java.util.List;

public class WebSocketExample {
    private static final String URL = "";
    private static final String TOKEN = "";

    public static void main(String[] args) {


        WebSocketClient client = new WebSocketClient
                .Builder()
                .url(URL)
                .build();

        DeviceWS deviceWS = client.createDeviceWS();
        deviceWS.setListener(new DeviceListener() {
            @Override
            public void onList(List<Device> response) {
                System.out.println("LIST:" + response.size());
            }

            @Override
            public void onGet(Device response) {
                System.out.println("Single:" + response);
            }

            @Override
            public void onDelete(ResponseAction response) {

            }

            @Override
            public void onSave(ResponseAction response) {

            }

            @Override
            public void onError(ErrorResponse error) {
                System.out.println("DeviceListener:" + error);
            }
        });

        CommandWS commandWS = client.createCommandWS();
        commandWS.setListener(new CommandListener() {
            @Override
            public void onInsert(CommandInsertResponse response) {
                System.out.println(response);
            }

            @Override
            public void onUpdate(ResponseAction response) {

            }

            @Override
            public void onList(CommandListResponse response) {
                System.out.println(response);

            }

            @Override
            public void onGet(CommandGetResponse response) {

            }

            @Override
            public void onSubscribe(CommandSubscribeResponse response) {

            }

            @Override
            public void onUnsubscribe(ResponseAction response) {

            }

            @Override
            public void onError(ErrorResponse error) {
                System.out.println(error);
            }
        });

        deviceWS.list(null, null, null, null,
                null, null,
                null, 0, 0);
        deviceWS.get(null, "441z79GRgY0QnV9HKrLra8Jt2FXRQ6MzqmuP");
        commandWS.list(null, "3d77f31c-bddd-443b-b11c-640946b0581z4123tzxc3", null, null, "ALARM", null, null, null, null);

    }
}
