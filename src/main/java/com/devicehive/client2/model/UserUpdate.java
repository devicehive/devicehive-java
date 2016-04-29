package com.devicehive.client2.model;

import java.util.Objects;

import io.swagger.annotations.ApiModelProperty;

import com.google.gson.annotations.SerializedName;





public class UserUpdate   {
  
  @SerializedName("login")
  private NullableWrapper login = null;

  @SerializedName("role")
  private NullableWrapper role = null;

  @SerializedName("status")
  private NullableWrapper status = null;

  @SerializedName("password")
  private NullableWrapper password = null;

  @SerializedName("oldPassword")
  private NullableWrapper oldPassword = null;

  @SerializedName("googleLogin")
  private NullableWrapper googleLogin = null;

  @SerializedName("facebookLogin")
  private NullableWrapper facebookLogin = null;

  @SerializedName("githubLogin")
  private NullableWrapper githubLogin = null;

  @SerializedName("data")
  private NullableWrapper data = null;


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
  public NullableWrapper getLogin() {
    return login;
  }
  public void setLogin(NullableWrapper login) {
    this.login = login;
  }

  /**
   **/
  @ApiModelProperty(value = "")
  public NullableWrapper getRole() {
    return role;
  }
  public void setRole(NullableWrapper role) {
    this.role = role;
  }

  /**
   **/
  @ApiModelProperty(value = "")
  public NullableWrapper getStatus() {
    return status;
  }
  public void setStatus(NullableWrapper status) {
    this.status = status;
  }

  /**
   **/
  @ApiModelProperty(value = "")
  public NullableWrapper getPassword() {
    return password;
  }
  public void setPassword(NullableWrapper password) {
    this.password = password;
  }

  /**
   **/
  @ApiModelProperty(value = "")
  public NullableWrapper getOldPassword() {
    return oldPassword;
  }
  public void setOldPassword(NullableWrapper oldPassword) {
    this.oldPassword = oldPassword;
  }

  /**
   **/
  @ApiModelProperty(value = "")
  public NullableWrapper getGoogleLogin() {
    return googleLogin;
  }
  public void setGoogleLogin(NullableWrapper googleLogin) {
    this.googleLogin = googleLogin;
  }

  /**
   **/
  @ApiModelProperty(value = "")
  public NullableWrapper getFacebookLogin() {
    return facebookLogin;
  }
  public void setFacebookLogin(NullableWrapper facebookLogin) {
    this.facebookLogin = facebookLogin;
  }

  /**
   **/
  @ApiModelProperty(value = "")
  public NullableWrapper getGithubLogin() {
    return githubLogin;
  }
  public void setGithubLogin(NullableWrapper githubLogin) {
    this.githubLogin = githubLogin;
  }

  /**
   **/
  @ApiModelProperty(value = "")
  public NullableWrapper getData() {
    return data;
  }
  public void setData(NullableWrapper data) {
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
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UserUpdate userUpdate = (UserUpdate) o;
    return Objects.equals(login, userUpdate.login) &&
        Objects.equals(role, userUpdate.role) &&
        Objects.equals(status, userUpdate.status) &&
        Objects.equals(password, userUpdate.password) &&
        Objects.equals(oldPassword, userUpdate.oldPassword) &&
        Objects.equals(googleLogin, userUpdate.googleLogin) &&
        Objects.equals(facebookLogin, userUpdate.facebookLogin) &&
        Objects.equals(githubLogin, userUpdate.githubLogin) &&
        Objects.equals(data, userUpdate.data) &&
        Objects.equals(roleEnum, userUpdate.roleEnum) &&
        Objects.equals(statusEnum, userUpdate.statusEnum);
  }

  @Override
  public int hashCode() {
    return Objects.hash(login, role, status, password, oldPassword, googleLogin, facebookLogin, githubLogin, data, roleEnum, statusEnum);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UserUpdate {\n");
    
    sb.append("    login: ").append(toIndentedString(login)).append("\n");
    sb.append("    role: ").append(toIndentedString(role)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
    sb.append("    password: ").append(toIndentedString(password)).append("\n");
    sb.append("    oldPassword: ").append(toIndentedString(oldPassword)).append("\n");
    sb.append("    googleLogin: ").append(toIndentedString(googleLogin)).append("\n");
    sb.append("    facebookLogin: ").append(toIndentedString(facebookLogin)).append("\n");
    sb.append("    githubLogin: ").append(toIndentedString(githubLogin)).append("\n");
    sb.append("    data: ").append(toIndentedString(data)).append("\n");
    sb.append("    roleEnum: ").append(toIndentedString(roleEnum)).append("\n");
    sb.append("    statusEnum: ").append(toIndentedString(statusEnum)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}
