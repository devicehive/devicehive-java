/**
 * Device Hive REST API
 * No descripton provided (generated by Swagger Codegen https://github.com/swagger-api/swagger-codegen)
 *
 * OpenAPI spec version: 2.1.1-SNAPSHOT
 * 
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package com.devicehive.client.model;

import com.google.gson.annotations.SerializedName;
import io.swagger.annotations.ApiModelProperty;


/**
 * UserUpdate
 */
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2016-12-09T14:31:42.381+02:00")
public class UserUpdate   {
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

  /**
   * Gets or Sets roleEnum
   */
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
      return String.valueOf(value);
    }
  }

  @SerializedName("roleEnum")
  private RoleEnumEnum roleEnum = null;

  /**
   * Gets or Sets statusEnum
   */
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
      return String.valueOf(value);
    }
  }

  @SerializedName("statusEnum")
  private StatusEnumEnum statusEnum = null;

  public UserUpdate login(String login) {
    this.login = login;
    return this;
  }

   /**
   * Get login
   * @return login
  **/
  @ApiModelProperty(example = "null", value = "")
  public String getLogin() {
    return login;
  }

  public void setLogin(String login) {
    this.login = login;
  }

  public UserUpdate role(Integer role) {
    this.role = role;
    return this;
  }

   /**
   * Get role
   * @return role
  **/
  @ApiModelProperty(example = "null", value = "")
  public Integer getRole() {
    return role;
  }

  public void setRole(Integer role) {
    this.role = role;
  }

  public UserUpdate status(Integer status) {
    this.status = status;
    return this;
  }

   /**
   * Get status
   * @return status
  **/
  @ApiModelProperty(example = "null", value = "")
  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }

  public UserUpdate password(String password) {
    this.password = password;
    return this;
  }

   /**
   * Get password
   * @return password
  **/
  @ApiModelProperty(example = "null", value = "")
  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public UserUpdate oldPassword(String oldPassword) {
    this.oldPassword = oldPassword;
    return this;
  }

   /**
   * Get oldPassword
   * @return oldPassword
  **/
  @ApiModelProperty(example = "null", value = "")
  public String getOldPassword() {
    return oldPassword;
  }

  public void setOldPassword(String oldPassword) {
    this.oldPassword = oldPassword;
  }

  public UserUpdate googleLogin(String googleLogin) {
    this.googleLogin = googleLogin;
    return this;
  }

   /**
   * Get googleLogin
   * @return googleLogin
  **/
  @ApiModelProperty(example = "null", value = "")
  public String getGoogleLogin() {
    return googleLogin;
  }

  public void setGoogleLogin(String googleLogin) {
    this.googleLogin = googleLogin;
  }

  public UserUpdate facebookLogin(String facebookLogin) {
    this.facebookLogin = facebookLogin;
    return this;
  }

   /**
   * Get facebookLogin
   * @return facebookLogin
  **/
  @ApiModelProperty(example = "null", value = "")
  public String getFacebookLogin() {
    return facebookLogin;
  }

  public void setFacebookLogin(String facebookLogin) {
    this.facebookLogin = facebookLogin;
  }

  public UserUpdate githubLogin(String githubLogin) {
    this.githubLogin = githubLogin;
    return this;
  }

   /**
   * Get githubLogin
   * @return githubLogin
  **/
  @ApiModelProperty(example = "null", value = "")
  public String getGithubLogin() {
    return githubLogin;
  }

  public void setGithubLogin(String githubLogin) {
    this.githubLogin = githubLogin;
  }

  public UserUpdate data(JsonStringWrapper data) {
    this.data = data;
    return this;
  }

   /**
   * Get data
   * @return data
  **/
  @ApiModelProperty(example = "null", value = "")
  public JsonStringWrapper getData() {
    return data;
  }

  public void setData(JsonStringWrapper data) {
    this.data = data;
  }

  public UserUpdate roleEnum(RoleEnumEnum roleEnum) {
    this.roleEnum = roleEnum;
    return this;
  }

   /**
   * Get roleEnum
   * @return roleEnum
  **/
  @ApiModelProperty(example = "null", value = "")
  public RoleEnumEnum getRoleEnum() {
    return roleEnum;
  }

  public void setRoleEnum(RoleEnumEnum roleEnum) {
    this.roleEnum = roleEnum;
  }

  public UserUpdate statusEnum(StatusEnumEnum statusEnum) {
    this.statusEnum = statusEnum;
    return this;
  }

   /**
   * Get statusEnum
   * @return statusEnum
  **/
  @ApiModelProperty(example = "null", value = "")
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

