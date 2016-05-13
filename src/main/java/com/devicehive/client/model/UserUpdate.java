package com.devicehive.client.model;

import com.devicehive.client.StringUtil;
import com.google.gson.annotations.SerializedName;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


@ApiModel(description = "")
public class UserUpdate {

    @SerializedName("login")
    private String login = null;

    @SerializedName("role")
    private Integer role = null;

    @SerializedName("status")
    private Integer status = null;

    @SerializedName("password")
    private String password = null;

    @SerializedName("oldPassword")
    private String oldPassword = null;

    @SerializedName("googleLogin")
    private String googleLogin = null;

    @SerializedName("facebookLogin")
    private String facebookLogin = null;

    @SerializedName("githubLogin")
    private String githubLogin = null;

    @SerializedName("data")
    private JsonStringWrapper data = null;


    public enum RoleEnumEnum {
        @SerializedName("ADMIN")
        ADMIN("ADMIN"),

        @SerializedName("CLIENT")
        CLIENT("CLIENT");

        private String value;

        RoleEnumEnum(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return value;
        }
    }

    @SerializedName("roleEnum")
    private RoleEnumEnum roleEnum = null;


    public enum StatusEnumEnum {
        @SerializedName("ACTIVE")
        ACTIVE("ACTIVE"),

        @SerializedName("LOCKED_OUT")
        LOCKED_OUT("LOCKED_OUT"),

        @SerializedName("DISABLED")
        DISABLED("DISABLED"),

        @SerializedName("DELETED")
        DELETED("DELETED");

        private String value;

        StatusEnumEnum(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return value;
        }
    }

    @SerializedName("statusEnum")
    private StatusEnumEnum statusEnum = null;


    /**
     **/
    @ApiModelProperty(value = "")
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }


    /**
     **/
    @ApiModelProperty(value = "")
    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }


    /**
     **/
    @ApiModelProperty(value = "")
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }


    /**
     **/
    @ApiModelProperty(value = "")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    /**
     **/
    @ApiModelProperty(value = "")
    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }


    /**
     **/
    @ApiModelProperty(value = "")
    public String getGoogleLogin() {
        return googleLogin;
    }

    public void setGoogleLogin(String googleLogin) {
        this.googleLogin = googleLogin;
    }


    /**
     **/
    @ApiModelProperty(value = "")
    public String getFacebookLogin() {
        return facebookLogin;
    }

    public void setFacebookLogin(String facebookLogin) {
        this.facebookLogin = facebookLogin;
    }


    /**
     **/
    @ApiModelProperty(value = "")
    public String getGithubLogin() {
        return githubLogin;
    }

    public void setGithubLogin(String githubLogin) {
        this.githubLogin = githubLogin;
    }


    /**
     **/
    @ApiModelProperty(value = "")
    public JsonStringWrapper getData() {
        return data;
    }

    public void setData(JsonStringWrapper data) {
        this.data = data;
    }


    /**
     **/
    @ApiModelProperty(value = "")
    public RoleEnumEnum getRoleEnum() {
        return roleEnum;
    }

    public void setRoleEnum(RoleEnumEnum roleEnum) {
        this.roleEnum = roleEnum;
    }


    /**
     **/
    @ApiModelProperty(value = "")
    public StatusEnumEnum getStatusEnum() {
        return statusEnum;
    }

    public void setStatusEnum(StatusEnumEnum statusEnum) {
        this.statusEnum = statusEnum;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class UserUpdate {\n");

        sb.append("    login: ").append(StringUtil.toIndentedString(login)).append("\n");
        sb.append("    role: ").append(StringUtil.toIndentedString(role)).append("\n");
        sb.append("    status: ").append(StringUtil.toIndentedString(status)).append("\n");
        sb.append("    password: ").append(StringUtil.toIndentedString(password)).append("\n");
        sb.append("    oldPassword: ").append(StringUtil.toIndentedString(oldPassword)).append("\n");
        sb.append("    googleLogin: ").append(StringUtil.toIndentedString(googleLogin)).append("\n");
        sb.append("    facebookLogin: ").append(StringUtil.toIndentedString(facebookLogin)).append("\n");
        sb.append("    githubLogin: ").append(StringUtil.toIndentedString(githubLogin)).append("\n");
        sb.append("    data: ").append(StringUtil.toIndentedString(data)).append("\n");
        sb.append("    roleEnum: ").append(StringUtil.toIndentedString(roleEnum)).append("\n");
        sb.append("    statusEnum: ").append(StringUtil.toIndentedString(statusEnum)).append("\n");
        sb.append("}");
        return sb.toString();
    }
}
