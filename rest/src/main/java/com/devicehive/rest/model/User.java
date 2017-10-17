package com.devicehive.rest.model;

import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import lombok.Data;

import java.io.IOException;
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

    @JsonAdapter(RoleEnum.Adapter.class)
    public enum RoleEnum {
        ADMIN(0),

        CLIENT(1);

        private Integer value;

        RoleEnum(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }

        @Override
        public String toString() {
            return String.valueOf(value);
        }

        public static RoleEnum fromValue(String text) {
            for (RoleEnum b : RoleEnum.values()) {
                if (String.valueOf(b.value).equals(text)) {
                    return b;
                }
            }
            return null;
        }

        public static class Adapter extends TypeAdapter<RoleEnum> {
            @Override
            public void write(final JsonWriter jsonWriter, final RoleEnum enumeration) throws IOException {
                jsonWriter.value(enumeration.getValue());
            }

            @Override
            public RoleEnum read(final JsonReader jsonReader) throws IOException {
                Integer value = jsonReader.nextInt();
                return RoleEnum.fromValue(String.valueOf(value));
            }
        }
    }

    @SerializedName("role")
    private RoleEnum role = null;


    @JsonAdapter(StatusEnum.Adapter.class)
    public enum StatusEnum {
        NUMBER_0(0),

        NUMBER_1(1),

        NUMBER_2(2);

        private Integer value;

        StatusEnum(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }

        @Override
        public String toString() {
            return String.valueOf(value);
        }

        public static StatusEnum fromValue(String text) {
            for (StatusEnum b : StatusEnum.values()) {
                if (String.valueOf(b.value).equals(text)) {
                    return b;
                }
            }
            return null;
        }

        public static class Adapter extends TypeAdapter<StatusEnum> {
            @Override
            public void write(final JsonWriter jsonWriter, final StatusEnum enumeration) throws IOException {
                jsonWriter.value(enumeration.getValue());
            }

            @Override
            public StatusEnum read(final JsonReader jsonReader) throws IOException {
                Integer value = jsonReader.nextInt();
                return StatusEnum.fromValue(String.valueOf(value));
            }
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
