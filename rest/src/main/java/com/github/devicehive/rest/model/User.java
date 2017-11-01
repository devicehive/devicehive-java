/*
 *
 *
 *   User.java
 *
 *   Copyright (C) 2017 DataArt
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

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
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

    @SerializedName("data")
    private JsonStringWrapper data = null;

    @SerializedName("admin")
    private Boolean admin = false;
}
