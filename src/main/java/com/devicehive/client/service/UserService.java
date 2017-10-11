package com.devicehive.client.service;

import com.devicehive.client.model.DHResponse;
import com.devicehive.client.model.Network;
import com.devicehive.client.model.UserFilter;
import com.devicehive.rest.api.UserApi;
import com.devicehive.rest.model.JsonStringWrapper;
import com.devicehive.rest.model.UserUpdate;
import com.devicehive.rest.model.UserVO;
import com.devicehive.rest.model.UserWithNetwork;
import com.devicehive.websocket.model.StatusEnum;

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
            authorize();
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
            authorize();
            userApi = createService(UserApi.class);
            result = execute(userApi.list(null, null,
                    null, null, null, null, null, null));
            return DHResponse.create(User.createList(result.getData()), result.getFailureData());
        } else {
            return response;
        }

    }

    public DHResponse<User> createUser(String login, String password, com.devicehive.rest.model.User.RoleEnum role, StatusEnum statusEnum, JsonStringWrapper data) {
        UserApi userApi = createService(UserApi.class);
        UserUpdate userUpdate = createUserBody(login, password, role, statusEnum, data);
        DHResponse<User> response;
        DHResponse<UserVO> result = execute(userApi.insertUser(userUpdate));
        response = DHResponse.create(User.create(result.getData()), result.getFailureData());
        if (response.isSuccessful()) {
            updateUser(response, login, role, data);
            return response;
        } else if (response.getFailureData().getCode() == 401) {
            authorize();
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

    private void updateUser(DHResponse<User> response, String login, com.devicehive.rest.model.User.RoleEnum role, JsonStringWrapper data) {
        if (response.isSuccessful()) {
            User user = response.getData();
            user.setLogin(login);
            user.setRole(role);
            user.setData(data);
        }
    }

    private UserUpdate createUserBody(String login, String password, com.devicehive.rest.model.User.RoleEnum role, StatusEnum statusEnum, JsonStringWrapper data) {
        UserUpdate userUpdate = new UserUpdate();
        userUpdate.setLogin(login);
        userUpdate.setRole(role);
        userUpdate.setStatus(statusEnum != null ? statusEnum.getValue() : 0);
        userUpdate.setPassword(password);
        userUpdate.setData(data);
        return userUpdate;
    }

    public DHResponse<Void> updateUser(long userId, UserUpdate body) {
        UserApi userApi = createService(UserApi.class);
        DHResponse<Void> response = execute(userApi.updateUser(body, userId));
        if (response.isSuccessful()) {
            return response;
        } else if (response.getFailureData().getCode() == 401) {
            authorize();
            userApi = createService(UserApi.class);
            return execute(userApi.updateUser(body, userId));
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
            authorize();
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
            return DHResponse.create(Network.createListVO(result.getData().getNetworks()), result.getFailureData());
        } else if (result.getFailureData().getCode() == 401) {
            authorize();
            userApi = createService(UserApi.class);
            result = execute(userApi.getCurrent());
            return DHResponse.create(Network.createListVO(result.getData().getNetworks()), result.getFailureData());
        } else {
            try {
                return DHResponse.create(Network.createListVO(result.getData().getNetworks()), result.getFailureData());
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
            authorize();
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
            authorize();
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
            authorize();
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
