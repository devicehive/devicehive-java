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

package com.github.devicehive.rest.model;

import com.github.devicehive.rest.adapters.NullJsonAdapter;
import com.google.gson.JsonObject;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class User {

    @SerializedName("id")
    private Long id = null;

    @SerializedName("login")
    private String login = null;

    @SerializedName("passwordHash")
    private String passwordHash = null;

    @SerializedName("passwordSalt")
    private String passwordSalt = null;

    @SerializedName("loginAttempts")
    private Integer loginAttempts = null;

    @SerializedName("role")
    private RoleEnum role = null;

    @SerializedName("status")
    private StatusEnum status = null;

    @SerializedName("networks")
    private List<NetworkVO> networks = new ArrayList<NetworkVO>();

    @SerializedName("lastLogin")
    private Date lastLogin = null;

    @SerializedName("googleLogin")
    private String googleLogin = null;

    @SerializedName("facebookLogin")
    private String facebookLogin = null;

    @SerializedName("githubLogin")
    private String githubLogin = null;

    @SerializedName("entityVersion")
    private Long entityVersion = null;
    @JsonAdapter(value = NullJsonAdapter.class)
    @SerializedName("data")
    private JsonObject data = new JsonObject();

    @SerializedName("admin")
    private Boolean admin = false;

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

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getPasswordSalt() {
        return passwordSalt;
    }

    public void setPasswordSalt(String passwordSalt) {
        this.passwordSalt = passwordSalt;
    }

    public Integer getLoginAttempts() {
        return loginAttempts;
    }

    public void setLoginAttempts(Integer loginAttempts) {
        this.loginAttempts = loginAttempts;
    }

    public RoleEnum getRole() {
        return role;
    }

    public void setRole(RoleEnum role) {
        this.role = role;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }

    public List<NetworkVO> getNetworks() {
        return networks;
    }

    public void setNetworks(List<NetworkVO> networks) {
        this.networks = networks;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    public String getGoogleLogin() {
        return googleLogin;
    }

    public void setGoogleLogin(String googleLogin) {
        this.googleLogin = googleLogin;
    }

    public String getFacebookLogin() {
        return facebookLogin;
    }

    public void setFacebookLogin(String facebookLogin) {
        this.facebookLogin = facebookLogin;
    }

    public String getGithubLogin() {
        return githubLogin;
    }

    public void setGithubLogin(String githubLogin) {
        this.githubLogin = githubLogin;
    }

    public Long getEntityVersion() {
        return entityVersion;
    }

    public void setEntityVersion(Long entityVersion) {
        this.entityVersion = entityVersion;
    }

    public JsonObject getData() {
        return data;
    }

    public void setData(JsonObject data) {
        if (data != null) this.data = data;
    }

    public Boolean getAdmin() {
        return admin;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }
}
