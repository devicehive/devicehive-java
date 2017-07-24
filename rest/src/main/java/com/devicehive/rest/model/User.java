package com.devicehive.rest.model;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
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
