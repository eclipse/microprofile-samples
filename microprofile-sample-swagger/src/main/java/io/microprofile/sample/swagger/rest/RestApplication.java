package io.microprofile.sample.swagger.rest;

import io.swagger.jaxrs.config.BeanConfig;
import io.swagger.jaxrs.listing.ApiListingResource;
import io.swagger.jaxrs.listing.SwaggerSerializers;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/")
public class RestApplication extends Application {

    public RestApplication() {
        BeanConfig beanConfig = new BeanConfig();
        beanConfig.setTitle("TOP CDs");
        beanConfig.setDescription("Gives the TOP selling CDs");
        beanConfig.setVersion("1.0.0");
        beanConfig.setSchemes(new String[]{"http"});
        beanConfig.setHost("localhost:8080/msTopCDs");
        beanConfig.setBasePath("/");
        beanConfig.setResourcePackage("org.agoncal.application.topcds.rest");
        beanConfig.setPrettyPrint(true);
        beanConfig.setScan(true);
    }

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new HashSet();

        resources.add(TopCDsEndpoint.class);
        resources.add(ApiListingResource.class);
        resources.add(SwaggerSerializers.class);

        return resources;
    }
}
