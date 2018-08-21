package org.eclipse.microprofile.sample.config.configuration;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 * This class represents the jax-rs application.
 *
 * @author Thomas Herzog <herzog.thomas81@gmail.com>
 * @since 8/2/2018
 */
@ApplicationPath("/")
@ApplicationScoped
public class RestApplication extends Application {
}
