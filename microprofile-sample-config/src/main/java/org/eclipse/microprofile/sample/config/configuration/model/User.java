package org.eclipse.microprofile.sample.config.configuration.model;

import java.io.Serializable;
import java.util.Objects;

/**
 * This is a configuration item model, which represents a User instance, which is created
 * by a microprofile-config converter {@link org.eclipse.microprofile.sample.config.configuration.converter.UserConfigConverter}
 *
 * @author Thomas Herzog <herzog.thomas81@gmail.com>
 * @since 8/2/2018
 */
public class User implements Serializable {

    private final String username;
    private final String pass;

    public User(String username,
                String pass) {
        this.username = Objects.requireNonNull(username, "Username must not be null");
        this.pass = Objects.requireNonNull(pass, "Pass must not be null");
    }

    public String getUsername() {
        return username;
    }

    public String getPass() {
        return pass;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User that = (User) o;

        if (!username.equals(that.username)) return false;
        return pass.equals(that.pass);
    }

    @Override
    public int hashCode() {
        int result = username.hashCode();
        result = 31 * result + pass.hashCode();
        return result;
    }
}
