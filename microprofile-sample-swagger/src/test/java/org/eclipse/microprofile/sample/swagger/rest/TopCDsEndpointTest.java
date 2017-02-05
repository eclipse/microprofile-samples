/*
 * Copyright (C) 2016, 2017 Antonio Goncalves and others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied.
 *
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.eclipse.microprofile.sample.swagger.rest;

import org.eclipse.microprofile.sample.swagger.rest.RestApplication;
import org.eclipse.microprofile.sample.swagger.rest.TopCDsEndpoint;
import org.eclipse.microprofile.sample.swagger.utils.QLogger;
import org.eclipse.microprofile.sample.swagger.utils.ResourceProducer;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.net.URI;

import static net.javacrumbs.jsonunit.fluent.JsonFluentAssert.assertThatJson;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(Arquillian.class)
@RunAsClient
public class TopCDsEndpointTest {

    // ======================================
    // = Injection Points =
    // ======================================

    @ArquillianResource
    private URI baseURL;
    private Client client;
    private WebTarget webTarget;

    // ======================================
    // = Deployment methods =
    // ======================================

    @Deployment
    public static Archive<?> createDeployment() {
        if (System.getProperty("arquillian.launch", "").equals("arquillian-hammock")) {
            return ShrinkWrap.create(JavaArchive.class)
                    .addClasses(RestApplication.class, TopCDsEndpoint.class, QLogger.class, ResourceProducer.class);
        } else {
            // Import Maven runtime dependencies
            File[] files = Maven.resolver().loadPomFromFile("pom.xml").importRuntimeDependencies().resolve().withTransitivity().asFile();

            return ShrinkWrap.create(WebArchive.class).addClass(RestApplication.class).addClass(TopCDsEndpoint.class)
                    .addClass(ResourceProducer.class).addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml").addAsLibraries(files);
        }
    }

    // ======================================
    // = Lifecycle methods =
    // ======================================

    @Before
    public void initWebTarget() {
        client = ClientBuilder.newClient();
        webTarget = client.target(baseURL);
    }

    // ======================================
    // = Test methods =
    // ======================================

    @Test
    public void should_be_deployed() {
        assertEquals(Response.Status.OK.getStatusCode(), webTarget.request(MediaType.APPLICATION_JSON).get().getStatus());
    }

    @Test
    public void should_have_five_items() {
        String body = webTarget.request(MediaType.APPLICATION_JSON).get(String.class);
        assertThatJson(body).isArray().ofLength(5);
        assertTrue(body.startsWith("[{\"id\":"));
    }
}
