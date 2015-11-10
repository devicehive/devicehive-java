package com.devicehive.client.model;

import com.devicehive.client.StringUtil;
import com.google.gson.annotations.SerializedName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;



@ApiModel(description = "")
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
  public String toString()  {
    StringBuilder sb = new StringBuilder();
    sb.append("class User {\n");
    
    sb.append("    id: ").append(StringUtil.toIndentedString(id)).append("\n");
    sb.append("    login: ").append(StringUtil.toIndentedString(login)).append("\n");
    sb.append("    passwordHash: ").append(StringUtil.toIndentedString(passwordHash)).append("\n");
    sb.append("    passwordSalt: ").append(StringUtil.toIndentedString(passwordSalt)).append("\n");
    sb.append("    loginAttempts: ").append(StringUtil.toIndentedString(loginAttempts)).append("\n");
    sb.append("    role: ").append(StringUtil.toIndentedString(role)).append("\n");
    sb.append("    status: ").append(StringUtil.toIndentedString(status)).append("\n");
    sb.append("    networks: ").append(StringUtil.toIndentedString(networks)).append("\n");
    sb.append("    lastLogin: ").append(StringUtil.toIndentedString(lastLogin)).append("\n");
    sb.append("    googleLogin: ").append(StringUtil.toIndentedString(googleLogin)).append("\n");
    sb.append("    facebookLogin: ").append(StringUtil.toIndentedString(facebookLogin)).append("\n");
    sb.append("    githubLogin: ").append(StringUtil.toIndentedString(githubLogin)).append("\n");
    sb.append("    entityVersion: ").append(StringUtil.toIndentedString(entityVersion)).append("\n");
    sb.append("    data: ").append(StringUtil.toIndentedString(data)).append("\n");
    sb.append("    admin: ").append(StringUtil.toIndentedString(admin)).append("\n");
    sb.append("}");
    return sb.toString();
  }
}
