package io.microprofile.sample.canonical.rest;

import io.microprofile.sample.canonical.utils.ResourceProducer;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
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
    // =          Injection Points          =
    // ======================================

    @ArquillianResource
    private URI baseURL;
    private Client client;
    private WebTarget webTarget;


    // ======================================
    // =         Deployment methods         =
    // ======================================

    @Deployment(testable = false)
    public static WebArchive createDeployment() {

        // Import Maven runtime dependencies
        File[] files = Maven.resolver().loadPomFromFile("pom.xml")
                .importRuntimeDependencies().resolve().withTransitivity().asFile();

        return ShrinkWrap
                .create(WebArchive.class)
                .addClass(RestApplication.class)
                .addClass(TopCDsEndpoint.class)
                .addClass(ResourceProducer.class)
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsLibraries(files);
    }

    // ======================================
    // =          Lifecycle methods         =
    // ======================================

    @Before
    public void initWebTarget() {
        client = ClientBuilder.newClient();
        webTarget = client.target(baseURL);
    }

    // ======================================
    // =            Test methods            =
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
