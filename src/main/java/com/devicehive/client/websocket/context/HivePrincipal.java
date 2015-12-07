package com.devicehive.client.websocket.context;


import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

/**
 * A Device Hive principal. Stores credentials of authenticated device or client.
 */
public class HivePrincipal {

    private final Pair<String, String> principal;

    private final Type type;

    private enum Type {
        USER, DEVICE, ACCESS_KEY
    }

    private HivePrincipal(Pair<String, String> principal, Type type) {
        this.principal = principal;
        this.type = type;
    }

    /**
     * Creates a new Hive principal with user credentials.
     *
     * @param login    login
     * @param password password
     * @return a new {@link HivePrincipal} with the user credentials
     */
    public static HivePrincipal createUser(String login, String password) {
        return new HivePrincipal(ImmutablePair.of(login, password), Type.USER);
    }

    /**
     * Creates a new Hive principal with device credentials.
     *
     * @param id  a device identifier
     * @param key a device key
     * @return a new {@link HivePrincipal} with the device credentials.
     */
    public static HivePrincipal createDevice(String id, String key) {
        return new HivePrincipal(ImmutablePair.of(id, key), Type.DEVICE);
    }

    /**
     * Create a new Hive principal with access key credentials.
     *
     * @param key an access key
     * @return a new {@link HivePrincipal} with access key credentials
     */
    public static HivePrincipal createAccessKey(String key) {
        return new HivePrincipal(ImmutablePair.of((String) null, key), Type.ACCESS_KEY);
    }

    /**
     * Retrieves the current principal credentials.
     *
     * @return principal credentials
     */
    public Pair<String, String> getPrincipal() {
        return principal;
    }

    /**
     * Checks if it's a user principal.
     *
     * @return {@code true} if it's a user principal; {@code false} otherwise
     */
    public boolean isUser() {
        return Type.USER.equals(this.type);
    }

    /**
     * Checks if it's a device principal.
     *
     * @return {@code true} if it's a device principal; {@code false} otherwise
     */
    public boolean isDevice() {
        return Type.DEVICE.equals(this.type);
    }

    /**
     * Checks if it's an access key principal.
     *
     * @return {@code true} if it's an access key principal; {@code false} otherwise
     */
    public boolean isAccessKey() {
        return Type.ACCESS_KEY.equals(this.type);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HivePrincipal that = (HivePrincipal) o;

        if (principal != null ? !principal.equals(that.principal) : that.principal != null) return false;
        if (type != that.type) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = principal != null ? principal.hashCode() : 0;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }
}
