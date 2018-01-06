/*
 *
 *
 *   UserService.java
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
import com.github.devicehive.client.model.UserFilter;
import com.github.devicehive.rest.api.UserApi;
import com.github.devicehive.rest.model.*;
import com.google.gson.JsonObject;

import java.util.List;

public class UserService extends BaseService {


    public DHResponse<User> getCurrentUser() {
        UserApi userApi = createService(UserApi.class);
        DHResponse<User> response;
        DHResponse<UserWithNetwork> result = execute(userApi.getCurrent());
        response = DHResponse.create(User.create(result.getData()), result.getFailureData());
        if (response.isSuccessful()) {
            return response;
        } else if (response.getFailureData().getCode() == 401) {
            refreshAndAuthorize();
            userApi = createService(UserApi.class);
            result = execute(userApi.getCurrent());
            return DHResponse.create(User.create(result.getData()), result.getFailureData());
        } else {
            return response;
        }

    }

    public DHResponse<List<User>> getUsers() {
        UserApi userApi = createService(UserApi.class);
        DHResponse<List<User>> response;
        DHResponse<List<UserVO>> result = execute(userApi.list(null, null,
                null, null, null, null, null, null));
        response = DHResponse.create(User.createList(result.getData()), result.getFailureData());
        if (response.isSuccessful()) {
            return response;
        } else if (response.getFailureData().getCode() == 401) {
            refreshAndAuthorize();
            userApi = createService(UserApi.class);
            result = execute(userApi.list(null, null,
                    null, null, null, null, null, null));
            return DHResponse.create(User.createList(result.getData()), result.getFailureData());
        } else {
            return response;
        }

    }

    public DHResponse<User> createUser(String login, String password, RoleEnum role, StatusEnum statusEnum, JsonObject data) {
        UserApi userApi = createService(UserApi.class);
        UserUpdate userUpdate = createUserBody(login, password, role, statusEnum, data);
        DHResponse<User> response;
        DHResponse<UserInsert> result = execute(userApi.insertUser(userUpdate));
        response = DHResponse.create(User.create(result.getData()), result.getFailureData());
        if (response.isSuccessful()) {
            updateUser(response, login, role, data);
            return response;
        } else if (response.getFailureData().getCode() == 401) {
            refreshAndAuthorize();
            userApi = createService(UserApi.class);
            result = execute(userApi.insertUser(userUpdate));
            response = DHResponse.create(User.create(result.getData()), result.getFailureData());
            updateUser(response, login, role, data);
            return response;
        } else {
            updateUser(response, login, role, data);
            return response;
        }
    }

    private void updateUser(DHResponse<User> response, String login, RoleEnum role, JsonObject data) {
        if (response.isSuccessful()) {
            User user = response.getData();
            user.setLogin(login);
            user.setRole(role);
            user.setData(data);
        }
    }

    private UserUpdate createUserBody(String login, String password, RoleEnum role, StatusEnum statusEnum, JsonObject data) {
        UserUpdate userUpdate = new UserUpdate();
        userUpdate.setLogin(login);
        userUpdate.setRole(role);
        userUpdate.setStatus(statusEnum);
        userUpdate.setPassword(password);
        userUpdate.setData(data);
        return userUpdate;
    }

    public DHResponse<Void> updateUser(UserUpdate body) {
        UserApi userApi = createService(UserApi.class);
        DHResponse<Void> response = execute(userApi.updateCurrentUser(body));
        if (response.isSuccessful()) {
            return response;
        } else if (response.getFailureData().getCode() == 401) {
            refreshAndAuthorize();
            userApi = createService(UserApi.class);
            return execute(userApi.updateCurrentUser(body));
        } else {
            return response;
        }
    }

    public DHResponse<Void> removeUser(long id) {
        UserApi userApi = createService(UserApi.class);
        DHResponse<Void> response = execute(userApi.deleteUser(id));
        if (response.isSuccessful()) {
            return response;
        } else if (response.getFailureData().getCode() == 401) {
            refreshAndAuthorize();
            userApi = createService(UserApi.class);
            return execute(userApi.deleteUser(id));
        } else {
            return response;
        }
    }

    public DHResponse<List<Network>> listNetworks() {
        UserApi userApi = createService(UserApi.class);
        DHResponse<UserWithNetwork> result = execute(userApi.getCurrent());
        if (result.isSuccessful()) {
            return DHResponse.create(Network.createList(result.getData().getNetworks()), result.getFailureData());
        } else if (result.getFailureData().getCode() == 401) {
            refreshAndAuthorize();
            userApi = createService(UserApi.class);
            result = execute(userApi.getCurrent());
            return DHResponse.create(Network.createList(result.getData().getNetworks()), result.getFailureData());
        } else {
            try {
                return DHResponse.create(Network.createList(result.getData().getNetworks()), result.getFailureData());
            } catch (NullPointerException e) {
                return DHResponse.create(null, result.getFailureData());
            }
        }
    }

    public DHResponse<Void> unassignNetwork(long userId, long networkId) {
        UserApi userApi = createService(UserApi.class);

        DHResponse<Void> response = execute(userApi.unassignNetwork(userId, networkId));
        if (response.isSuccessful()) {
            return response;
        } else if (response.getFailureData().getCode() == 401) {
            refreshAndAuthorize();
            userApi = createService(UserApi.class);
            return execute(userApi.unassignNetwork(userId, networkId));
        } else {
            return response;
        }
    }

    public DHResponse<Void> assignNetwork(long userId, long networkId) {
        UserApi userApi = createService(UserApi.class);

        DHResponse<Void> response = execute(userApi.assignNetwork(userId, networkId));
        if (response.isSuccessful()) {
            return response;
        } else if (response.getFailureData().getCode() == 401) {
            refreshAndAuthorize();
            userApi = createService(UserApi.class);
            return execute(userApi.assignNetwork(userId, networkId));
        } else {
            return response;
        }
    }

    public DHResponse<List<User>> listUsers(UserFilter filter) {
        UserApi userApi = createService(UserApi.class);
        DHResponse<List<User>> response;

        DHResponse<List<UserVO>> result = execute(userApi.list(filter.getLogin(), filter.getLoginPattern(),
                filter.getRole() != null ? filter.getRole().getValue() : null, filter.getStatus() != null ? filter.getStatus().getValue() : null,
                filter.getSortField().sortField(), filter.getSortOrder().sortOrder(), filter.getTake(), filter.getSkip()));

        response = DHResponse.create(User.createList(result.getData()), result.getFailureData());
        if (response.isSuccessful()) {
            return response;
        } else if (response.getFailureData().getCode() == 401) {
            refreshAndAuthorize();
            userApi = createService(UserApi.class);
            result = execute(userApi.list(filter.getLogin(), filter.getLoginPattern(),
                    filter.getRole() != null ? filter.getRole().getValue() : null,
                    filter.getStatus() != null ? filter.getStatus().getValue() : null,
                    filter.getSortField().sortField(), filter.getSortOrder().sortOrder(), filter.getTake(), filter.getSkip()));
            return DHResponse.create(User.createList(result.getData()), result.getFailureData());
        } else {
            return response;
        }
    }
}
