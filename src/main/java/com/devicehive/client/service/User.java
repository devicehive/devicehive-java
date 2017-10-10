package com.devicehive.client.service;

import com.devicehive.rest.model.JsonStringWrapper;
import com.devicehive.rest.model.UserVO;
import com.devicehive.rest.model.UserWithNetwork;

import java.util.ArrayList;
import java.util.List;

public class User {

    private Long id = null;
    private String login;
    private com.devicehive.rest.model.User.RoleEnum role;
    private String password;
    private JsonStringWrapper data;

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

    public void save() {
    }


    public void getNetworks() {
    }


    public void unassignNetwork(long networkId) {
    }

    public void assignNetwork(long networkId) {
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

    public com.devicehive.rest.model.User.RoleEnum getRole() {
        return role;
    }

    public void setRole(com.devicehive.rest.model.User.RoleEnum role) {
        this.role = role;
    }

    public JsonStringWrapper getData() {
        return data;
    }

    public void setData(JsonStringWrapper data) {
        this.data = data;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
