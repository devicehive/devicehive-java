/*
 *
 *
 *   User.java
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

package com.github.devicehive.client.service;

import com.github.devicehive.client.model.DHResponse;
import com.github.devicehive.rest.model.RoleEnum;
import com.github.devicehive.rest.model.UserInsert;
import com.github.devicehive.rest.model.UserUpdate;
import com.github.devicehive.rest.model.UserVO;
import com.github.devicehive.rest.model.UserWithNetwork;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

public class User {

    private Long id = null;
    private String login;
    private RoleEnum role;
    private String password;
    private JsonObject data = new JsonObject();

    private User() {
    }


    public static User create(UserWithNetwork user) {
        if (user == null) {
            return null;
        }
        User result = new User();
        result.setId(user.getId());
        result.setLogin(user.getLogin());
        result.setRole(user.getRole());
        result.setData(user.getData());
        return result;
    }

    public static User create(UserInsert user) {
        if (user == null) {
            return null;
        }
        User result = new User();
        result.setId(user.getId());
        result.setData(user.getData());
        return result;
    }

    public static User create(UserVO user) {
        if (user == null) {
            return null;
        }
        User result = new User();
        result.setId(user.getId());
        result.setLogin(user.getLogin());
        result.setRole(user.getRole());
        result.setData(user.getData());
        return result;
    }

    public static List<User> createList(List<UserVO> list) {
        if (list == null) {
            return null;
        }
        List<User> result = new ArrayList<>(list.size());
        for (UserVO user : list) {
            result.add(create(user));
        }
        return result;
    }

    public boolean save() {
        UserUpdate body = new UserUpdate();
        if (data != null) {
            body.setData(data);
        }
        if (password != null && password.length() > 0) {
            body.setPassword(password);
        }
        body.setRole(role);
        if (login != null && login.length() > 0) {
            body.setLogin(login);
        }
        return DeviceHive.getInstance().getUserService().updateUser(body).isSuccessful();
    }


    public List<Network> getNetworks() {
        return DeviceHive.getInstance().getUserService().listNetworks().getData();
    }


    public boolean unassignNetwork(long networkId) {
        return DeviceHive.getInstance().getUserService().unassignNetwork(id, networkId).isSuccessful();
    }

    public boolean assignNetwork(long networkId) {
        DHResponse<Void> response = DeviceHive.getInstance().getUserService().assignNetwork(id, networkId);
        return response.isSuccessful();
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public RoleEnum getRole() {
        return role;
    }

    public void setRole(RoleEnum role) {
        this.role = role;
    }

    public JsonObject getData() {
        return data;
    }

    public void setData(JsonObject data) {
        if (data != null)
            this.data = data;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "{\n\"User\":{\n"
                + "\"id\":\"" + id + "\""
                + ",\n \"login\":\"" + login + "\""
                + ",\n \"role\":\"" + role + "\""
                + ",\n \"password\":\"" + password + "\""
                + ",\n \"data\":" + data
                + "}\n}";
    }
}
