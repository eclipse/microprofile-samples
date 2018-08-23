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
