/*
 *
 *
 *   UserListAction.java
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

package com.github.devicehive.websocket.model.request;

import com.github.devicehive.rest.model.RoleEnum;
import com.github.devicehive.rest.model.SortOrder;
import com.github.devicehive.rest.model.StatusEnum;
import com.google.gson.annotations.SerializedName;

import static com.github.devicehive.websocket.model.ActionConstant.USER_LIST;

public class UserListAction extends RequestAction {

    @SerializedName("login")
    private String login;
    @SerializedName("loginPattern")
    private String loginPattern;
    @SerializedName("status")
    private StatusEnum status;
    @SerializedName("role")
    private RoleEnum role;
    @SerializedName("sortField")
    private String sortField;
    @SerializedName("sortOrder")
    private SortOrder sortOrder;
    @SerializedName("take")
    private Integer take;
    @SerializedName("skip")
    private Integer skip;

    public UserListAction() {
        super(USER_LIST);
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getLoginPattern() {
        return loginPattern;
    }

    public void setLoginPattern(String loginPattern) {
        this.loginPattern = loginPattern;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }

    public RoleEnum getRole() {
        return role;
    }

    public void setRole(RoleEnum role) {
        this.role = role;
    }

    public String getSortField() {
        return sortField;
    }

    public void setSortField(String sortField) {
        this.sortField = sortField;
    }

    public SortOrder getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(SortOrder sortOrder) {
        this.sortOrder = sortOrder;
    }

    public Integer getTake() {
        return take;
    }

    public void setTake(Integer take) {
        this.take = take;
    }

    public Integer getSkip() {
        return skip;
    }

    public void setSkip(Integer skip) {
        this.skip = skip;
    }
}
