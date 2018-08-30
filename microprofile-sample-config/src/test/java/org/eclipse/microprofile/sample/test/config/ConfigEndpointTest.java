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
package org.eclipse.microprofile.sample.test.config;

import org.eclipse.microprofile.config.spi.Converter;
import org.eclipse.microprofile.sample.config.configuration.converter.UserConfigConverter;
import org.eclipse.microprofile.sample.config.configuration.model.User;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.resteasy.plugins.providers.jackson.ResteasyJackson2Provider;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.FileAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.wildfly.swarm.jaxrs.JAXRSArchive;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(Arquillian.class)
@RunAsClient
public class ConfigEndpointTest {

    // ======================================
    // = Injection Points =
    // ======================================

    @ArquillianResource
    private URI baseURI;
    private Client client;
    private WebTarget webTarget;

    // ======================================
    // = Deployment methods =
    // ======================================
    @Deployment
    public static Archive<?> archive() {
        Archive<?> archive;
        if (System.getProperty("arquillian.launch", "").equals("arquillian-hammock")) {
            archive = ShrinkWrap.create(JavaArchive.class)
                                .addPackages(true, "org.eclipse.microprofile.sample.config")
                                // No CDI producer available for Config type in hammock
                                .addClass(ConfigProducer.class)
                                .addAsManifestResource(new FileAsset(new File("src/main/resources/META-INF/beans.xml")), "beans.xml")
                                // UserConverter not loaded and empty list is injected into ConfigResourceImpl
                                .addAsServiceProvider(Converter.class, UserConfigConverter.class);
        } else if (System.getProperty("arquillian.launch", "").equals("arquillian-tomee-managed")) {
            archive = ShrinkWrap.create(WebArchive.class)
                                .addPackages(true, "org.eclipse.microprofile.sample.config")
                                // No CDI producer available for Config type in hammock
                                .addClass(ConfigProducer.class)
                                .addAsWebInfResource(new FileAsset(new File("src/main/resources/META-INF/beans.xml")), "beans.xml")
                                // UserConverter not loaded and empty list is injected into ConfigResourceImpl
                                .addAsServiceProvider(Converter.class, UserConfigConverter.class);
        } else {
            archive = ShrinkWrap.create(JAXRSArchive.class)
                                .addPackages(true, "org.eclipse.microprofile.sample.config")
                                .addAsManifestResource(new FileAsset(new File("src/main/resources/META-INF/beans.xml")), "beans.xml")
                                .addAsResource(new FileAsset(new File("src/main/resources/project-stages.yml")), "project-stages.yml")
                                .addAsServiceProvider(Converter.class, UserConfigConverter.class);
        }

        return archive;
    }

    @Before
    public void initWebTarget() {
        client = ClientBuilder.newBuilder().register(ResteasyJackson2Provider.class).build();
        webTarget = client.target(baseURI);
    }

    // ======================================
    // = Test methods =
    // ======================================

    @Test
    public void should_be_deployed() {
        // -- Given --
        final int expectedStatus = Response.Status.OK.getStatusCode();

        // -- When --
        final Response response = webTarget.request().get();

        // -- Then --
        assertEquals(expectedStatus, response.getStatus());
    }

    @Test
    public void is_alive_endpoint_up() {
        // -- Given --
        final int expectedStatus = Response.Status.OK.getStatusCode();
        final String expectedEntity = "alive";

        // -- When --
        final Response response = webTarget.request(MediaType.TEXT_PLAIN).get();
        final String actualEntity = response.readEntity(String.class);

        // -- Then --
        assertEquals(expectedStatus, response.getStatus());
        assertEquals(expectedEntity, actualEntity);
    }

    @Test
    public void is_list_users_up() {
        // -- Given --
        final int expectedStatus = Response.Status.OK.getStatusCode();
        final List<User> expectedEntity = Collections.singletonList(new User("het", "het"));

        // -- When --
        final Response response = webTarget.path("/list/users").request(MediaType.APPLICATION_JSON).get();
        final List<User> actualEntity = response.readEntity(new GenericType<List<User>>() {});

        // -- Then --
        assertEquals(expectedStatus, response.getStatus());
        assertEquals(expectedEntity, actualEntity);
    }

    @Test
    public void is_list_all_config_sources_up() {
        // -- Given --
        final int expectedStatus = Response.Status.FORBIDDEN.getStatusCode();

        // -- When --
        final Response response = webTarget.path("/list/all").request(MediaType.APPLICATION_JSON).get();

        // -- Then --
        assertEquals(expectedStatus, response.getStatusInfo().getStatusCode());
    }

    @Test
    public void is_list_config_source_by_name_up() {
        // -- Given --
        final int expectedStatus = Response.Status.OK.getStatusCode();

        // -- When --
        final Response response = webTarget.path("/list/source/{name}").resolveTemplate("name", "cli").request(MediaType.APPLICATION_JSON).get();
        final Map<String, String> actualEntity = response.readEntity(new GenericType<Map<String, String>>() {});

        // -- Then --
        assertEquals(expectedStatus, response.getStatus());
        assertTrue(actualEntity.isEmpty());
    }
}
