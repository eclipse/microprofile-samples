package io.microprofile.sample.canonical.utils;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

@ApplicationScoped
public class ResourceProducer {

    @Produces
    @QLogger
    public java.util.logging.Logger produceLogger(final InjectionPoint injectionPoint) {
        return java.util.logging.Logger.getLogger(injectionPoint.getMember().getDeclaringClass().getName());
    }
}
