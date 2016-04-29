package com.devicehive.client2.model;

import java.util.Objects;

import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gson.annotations.SerializedName;





public class User   {
  
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


public enum RoleEnum {
  @SerializedName("ADMIN")
  ADMIN("ADMIN"),

  @SerializedName("CLIENT")
  CLIENT("CLIENT");

  private String value;

  RoleEnum(String value) {
    this.value = value;
  }

  @Override
  public String toString() {
    return value;
  }
}

  @SerializedName("role")
  private RoleEnum role = null;


public enum StatusEnum {
  @SerializedName("ACTIVE")
  ACTIVE("ACTIVE"),

  @SerializedName("LOCKED_OUT")
  LOCKED_OUT("LOCKED_OUT"),

  @SerializedName("DISABLED")
  DISABLED("DISABLED"),

  @SerializedName("DELETED")
  DELETED("DELETED");

  private String value;

  StatusEnum(String value) {
    this.value = value;
  }

  @Override
  public String toString() {
    return value;
  }
}

  @SerializedName("status")
  private StatusEnum status = null;

  @SerializedName("networks")
  private List<Network> networks = new ArrayList<Network>();

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

  /**
   **/
  @ApiModelProperty(value = "")
  public Long getId() {
    return id;
  }
  public void setId(Long id) {
    this.id = id;
  }

  /**
   **/
  @ApiModelProperty(required = true, value = "")
  public String getLogin() {
    return login;
  }
  public void setLogin(String login) {
    this.login = login;
  }

  /**
   **/
  @ApiModelProperty(value = "")
  public String getPasswordHash() {
    return passwordHash;
  }
  public void setPasswordHash(String passwordHash) {
    this.passwordHash = passwordHash;
  }

  /**
   **/
  @ApiModelProperty(value = "")
  public String getPasswordSalt() {
    return passwordSalt;
  }
  public void setPasswordSalt(String passwordSalt) {
    this.passwordSalt = passwordSalt;
  }

  /**
   **/
  @ApiModelProperty(value = "")
  public Integer getLoginAttempts() {
    return loginAttempts;
  }
  public void setLoginAttempts(Integer loginAttempts) {
    this.loginAttempts = loginAttempts;
  }

  /**
   **/
  @ApiModelProperty(value = "")
  public RoleEnum getRole() {
    return role;
  }
  public void setRole(RoleEnum role) {
    this.role = role;
  }

  /**
   **/
  @ApiModelProperty(value = "")
  public StatusEnum getStatus() {
    return status;
  }
  public void setStatus(StatusEnum status) {
    this.status = status;
  }

  /**
   **/
  @ApiModelProperty(value = "")
  public List<Network> getNetworks() {
    return networks;
  }
  public void setNetworks(List<Network> networks) {
    this.networks = networks;
  }

  /**
   **/
  @ApiModelProperty(value = "")
  public Date getLastLogin() {
    return lastLogin;
  }
  public void setLastLogin(Date lastLogin) {
    this.lastLogin = lastLogin;
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
  public Long getEntityVersion() {
    return entityVersion;
  }
  public void setEntityVersion(Long entityVersion) {
    this.entityVersion = entityVersion;
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
  public Boolean getAdmin() {
    return admin;
  }
  public void setAdmin(Boolean admin) {
    this.admin = admin;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    User user = (User) o;
    return Objects.equals(id, user.id) &&
        Objects.equals(login, user.login) &&
        Objects.equals(passwordHash, user.passwordHash) &&
        Objects.equals(passwordSalt, user.passwordSalt) &&
        Objects.equals(loginAttempts, user.loginAttempts) &&
        Objects.equals(role, user.role) &&
        Objects.equals(status, user.status) &&
        Objects.equals(networks, user.networks) &&
        Objects.equals(lastLogin, user.lastLogin) &&
        Objects.equals(googleLogin, user.googleLogin) &&
        Objects.equals(facebookLogin, user.facebookLogin) &&
        Objects.equals(githubLogin, user.githubLogin) &&
        Objects.equals(entityVersion, user.entityVersion) &&
        Objects.equals(data, user.data) &&
        Objects.equals(admin, user.admin);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, login, passwordHash, passwordSalt, loginAttempts, role, status, networks, lastLogin, googleLogin, facebookLogin, githubLogin, entityVersion, data, admin);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class User {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    login: ").append(toIndentedString(login)).append("\n");
    sb.append("    passwordHash: ").append(toIndentedString(passwordHash)).append("\n");
    sb.append("    passwordSalt: ").append(toIndentedString(passwordSalt)).append("\n");
    sb.append("    loginAttempts: ").append(toIndentedString(loginAttempts)).append("\n");
    sb.append("    role: ").append(toIndentedString(role)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
    sb.append("    networks: ").append(toIndentedString(networks)).append("\n");
    sb.append("    lastLogin: ").append(toIndentedString(lastLogin)).append("\n");
    sb.append("    googleLogin: ").append(toIndentedString(googleLogin)).append("\n");
    sb.append("    facebookLogin: ").append(toIndentedString(facebookLogin)).append("\n");
    sb.append("    githubLogin: ").append(toIndentedString(githubLogin)).append("\n");
    sb.append("    entityVersion: ").append(toIndentedString(entityVersion)).append("\n");
    sb.append("    data: ").append(toIndentedString(data)).append("\n");
    sb.append("    admin: ").append(toIndentedString(admin)).append("\n");
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
