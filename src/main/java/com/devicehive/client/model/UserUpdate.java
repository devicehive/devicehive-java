package com.devicehive.client.model;

import com.devicehive.client.StringUtil;
import com.google.gson.annotations.SerializedName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;



@ApiModel(description = "")
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
  public String toString()  {
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
