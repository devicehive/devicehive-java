package com.devicehive.client.service;

import com.devicehive.client.model.DHResponse;
import com.devicehive.rest.api.UserApi;
import com.devicehive.rest.model.JsonStringWrapper;
import com.devicehive.rest.model.UserUpdate;
import com.devicehive.rest.model.UserVO;
import com.devicehive.rest.model.UserWithNetwork;

import java.util.List;

public class UserService extends BaseService {

    private UserApi userApi;

    public DHResponse<User> getCurrentUser() {
        userApi = createService(UserApi.class);
        DHResponse<User> response;
        DHResponse<UserWithNetwork> result = execute(userApi.getCurrent());
        response = DHResponse.create(User.create(result.getData()), result.getFailureData());
        if (response.isSuccessful()) {
            return response;
        } else if (response.getFailureData().getCode() == 401) {
            authorize();
            result = execute(userApi.getCurrent());
            return DHResponse.create(User.create(result.getData()), result.getFailureData());
        } else {
            return response;
        }

    }

    public DHResponse<List<User>> getUsers() {
        userApi = createService(UserApi.class);
        DHResponse<List<User>> response;
        DHResponse<List<UserVO>> result = execute(userApi.list(null, null,
                null, null, null, null, null, null));
        response = DHResponse.create(User.createList(result.getData()), result.getFailureData());
        if (response.isSuccessful()) {
            return response;
        } else if (response.getFailureData().getCode() == 401) {
            authorize();
            result = execute(userApi.list(null, null,
                    null, null, null, null, null, null));
            return DHResponse.create(User.createList(result.getData()), result.getFailureData());
        } else {
            return response;
        }

    }

    public DHResponse<User> createUser(String login, int role, String password, JsonStringWrapper data) {
        userApi = createService(UserApi.class);
        UserUpdate userUpdate = new UserUpdate();
        userUpdate.setLogin(login);
        userUpdate.setRole(role);
        userUpdate.setPassword(password);
        userUpdate.setData(data);
        DHResponse<User> response;
        DHResponse<UserVO> result = execute(userApi.insertUser(userUpdate));
        response = DHResponse.create(User.create(result.getData()), result.getFailureData());
        if (response.isSuccessful()) {
            return response;
        } else if (response.getFailureData().getCode() == 401) {
            authorize();
            result = execute(userApi.insertUser(userUpdate));
            return DHResponse.create(User.create(result.getData()), result.getFailureData());
        } else {
            return response;
        }
    }

    public DHResponse<Void> removeUser(long id) {
        userApi = createService(UserApi.class);
        DHResponse<Void> response = execute(userApi.deleteUser(id));
        if (response.isSuccessful()) {
            return response;
        } else if (response.getFailureData().getCode() == 401) {
            authorize();
            return execute(userApi.deleteUser(id));
        } else {
            return response;
        }
    }
}
