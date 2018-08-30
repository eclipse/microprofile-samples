/**********************************************************************
 * Copyright (c) 2018 Contributors to the Eclipse Foundation
 *
 * See the NOTICES file(s) distributed with this work for additional
 * information regarding copyright ownership.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * You may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * SPDX-License-Identifier: Apache-2.0
 **********************************************************************/
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
