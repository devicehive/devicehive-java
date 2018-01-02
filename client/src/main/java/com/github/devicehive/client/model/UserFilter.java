/*
 *
 *
 *   UserFilter.java
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

package com.github.devicehive.client.model;

import com.github.devicehive.rest.model.RoleEnum;
import com.github.devicehive.rest.model.SortField;
import com.github.devicehive.rest.model.SortOrder;
import com.github.devicehive.rest.model.StatusEnum;

public class UserFilter {

    String login;
    String loginPattern;
    RoleEnum role;
    StatusEnum status;
    SortField sortField = SortField.ID;
    SortOrder sortOrder = SortOrder.DESC;
    Integer take = 30;
    Integer skip = 0;

    public String getLogin() {
        return login;
    }

    public UserFilter setLogin(String login) {
        this.login = login;
        return this;
    }

    public String getLoginPattern() {
        return loginPattern;
    }

    public UserFilter setLoginPattern(String loginPattern) {
        this.loginPattern = loginPattern;
        return this;
    }

    public RoleEnum getRole() {
        return role;
    }

    public UserFilter setRole(RoleEnum role) {
        this.role = role;
        return this;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public UserFilter setStatus(StatusEnum status) {
        this.status = status;
        return this;
    }

    public SortField getSortField() {
        return sortField;
    }

    public UserFilter setSortField(SortField sortField) {
        this.sortField = sortField;
        return this;
    }

    public SortOrder getSortOrder() {
        return sortOrder;
    }

    public UserFilter setSortOrder(SortOrder sortOrder) {
        this.sortOrder = sortOrder;
        return this;
    }

    public Integer getTake() {
        return take;
    }

    public UserFilter setTake(Integer take) {
        this.take = take;
        return this;
    }

    public Integer getSkip() {
        return skip;
    }

    public UserFilter setSkip(Integer skip) {
        this.skip = skip;
        return this;
    }
}

