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
package org.eclipse.microprofile.sample.config.rest;

import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.ConfigProvider;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.config.spi.ConfigSource;
import org.eclipse.microprofile.sample.config.configuration.converter.UserConfigConverter;
import org.eclipse.microprofile.sample.config.configuration.model.User;

import javax.annotation.Generated;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * This is a example JAX-RS endpoint, which provides access to runtime available configurations,
 * which are provided via microprofile-config specification.
 *
 * @author Thomas Herzog <herzog.thomas81@gmail.com>
 * @since 8/2/2018
 */
@Path("/")
@ApplicationScoped
public class ConfigResourceImpl {

    /**
     * Allows to access all ConfigSources and configurations programmatically
     */
    @Inject
    private Config config;

    /**
     * Injects a converted configuration.
     *
     * @see UserConfigConverter
     */
    @Inject
    @ConfigProperty(name = "allowedUsers")
    private List<User> allowedUsers;

    /**
     * Injects a converted boolean value, which controls listing of all configurations.
     * The implementation of the microprofile-config-api provides converters for common java types such as boolean.
     */
    @Inject
    @ConfigProperty(name = "listAllConfigAllowed", defaultValue = "false")
    private boolean listAllConfigAllowed;

    @GET
    @Path("/")
    @Produces({ MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON})
    public String alive(){
        return "alive";
    }
    /**
     * @return all microprofile-config provided configurations
     */
    @GET
    @Path("/list/all")
    @Produces({ MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON})
    public Map<String, Map<String, String>> listAll() {
        if (!listAllConfigAllowed) {
            throw new ForbiddenException("Access of listing all configurations is not enabled");
        }

        return Stream.of(config.getConfigSources())
                     .flatMap(s -> StreamSupport.stream(s.spliterator(),
                                                        false))
                     .collect(Collectors.toMap(s -> s.getName(), s -> s.getProperties()));
    }

    /**
     * @return all via configuration provided users.
     */
    @GET
    @Path("/list/users")
    @Produces({ MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON})
    public List<User> listUsers() {
        return allowedUsers;
    }

    /**
     * @param name the name of the config source to fetch configurations from.
     * @return the config source provided configurations
     * @throws NotFoundException if no config source exists for the given name
     */
    @GET
    @Path("/list/source/{name}")
    @Produces({ MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON})
    public Map<String, String> listConfigSource(@PathParam("name") String name) {
        // Get config from current ClassLoader instead of injection point
        final Config config = ConfigProvider.getConfig();
        final Optional<ConfigSource> source = Stream.of(config.getConfigSources())
                                                    .flatMap(s -> StreamSupport.stream(s.spliterator(),
                                                                                       false))
                                                    .filter(s -> s.getName().equalsIgnoreCase(name))
                                                    .findFirst();

        if(source.isPresent()) {
            return source.get().getProperties();
        }
        return Collections.emptyMap();
    }
}
