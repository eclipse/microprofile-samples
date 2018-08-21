package org.eclipse.microprofile.sample.config.configuration.converter;

import org.eclipse.microprofile.config.spi.Converter;
import org.eclipse.microprofile.sample.config.configuration.model.User;

/**
 * Expects a string in the format: 'username:pass' and converts it to a list of {@link User}.
 * The item separator is supposed to be ',', which is handled in the microprofile-config implementation.
 *
 * @author Thomas Herzog <herzog.thomas81@gmail.com>
 * @since 8/2/2018
 */
public class UserConfigConverter implements Converter<User> {

    @Override
    public User convert(String value) {
        if (value != null && !value.trim().isEmpty()) {
            final String[] userParts = value.split(":");
            return new User(userParts[0], userParts[1]);
        }

        throw new IllegalArgumentException(String.format("Cannot convert null or empty string to user: '%s'", value));
    }
}
