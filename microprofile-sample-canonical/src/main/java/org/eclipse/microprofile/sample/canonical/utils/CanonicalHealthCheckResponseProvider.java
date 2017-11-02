/*
 * Copyright (C) 2017 Werner Keil and others.
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
package org.eclipse.microprofile.sample.canonical.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.HealthCheckResponseBuilder;
import org.eclipse.microprofile.health.spi.HealthCheckResponseProvider;

/**
 * @author Werner Keil
 */
public class CanonicalHealthCheckResponseProvider implements HealthCheckResponseProvider{
    @Override
    public HealthCheckResponseBuilder createResponseBuilder() {
        return new ResponseBuilder();
    }

    private static class ResponseBuilder extends HealthCheckResponseBuilder {
        private String name;
        private boolean state;
        private Map<String, Object> data = new ConcurrentHashMap<>();

        @Override
        public HealthCheckResponseBuilder name(String name) {
            this.name = name;
            return this;
        }

        @Override
        public HealthCheckResponseBuilder withData(String key, String value) {
            data.put(key, value);
            return this;
        }

        @Override
        public HealthCheckResponseBuilder withData(String key, long value) {
            data.put(key, value);
            return this;
        }

        @Override
        public HealthCheckResponseBuilder withData(String key, boolean value) {
            data.put(key, value);
            return this;
        }

        @Override
        public HealthCheckResponseBuilder up() {
            return state(true);
        }

        @Override
        public HealthCheckResponseBuilder down() {
            return state(false);
        }

        @Override
        public HealthCheckResponseBuilder state(boolean up) {
            this.state = up;
            return this;
        }

        @Override
        public HealthCheckResponse build() {
            return new Response(this.name, this.state, this.data);
        }

        private class Response extends HealthCheckResponse {
            private final String name;
            private final boolean up;
            private final Map<String, Object> data;

            public Response(String name, boolean up, Map<String, Object> map)
            {
                this.name = name;
                this.up = up;
                this.data = setData(map);
            }

            private Map<String,Object> setData(Map<String, Object> map) {
                if (map.isEmpty()) {
                    return null;
                }
                Map<String, Object> data = new HashMap<>();
                for (String key : map.keySet()) {
                    final Object modelValue = map.get(key);
                    final Object value;
                    if (modelValue instanceof Long) {
                            value = ((Long)modelValue).longValue();
                    } else {
                        value = modelValue;
                    }
                    data.put(key, value);
                }
                return data;
            }

            @Override
            public String getName() {
                return name;
            }

            @Override
            public State getState() {
                return up ? State.UP : State.DOWN;
            }

            @Override
            public Optional<Map<String, Object>> getData() {
                return Optional.ofNullable(data);
            }
        }
    }
}
