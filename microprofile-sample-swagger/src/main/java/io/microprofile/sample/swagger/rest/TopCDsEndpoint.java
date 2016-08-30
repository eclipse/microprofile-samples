/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package io.microprofile.sample.swagger.rest;

/*-
 * #%L
 * Microprofile Samples :: Swagger
 * %%
 * Copyright (C) 2016 Microprofile.io
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import io.microprofile.sample.swagger.utils.QLogger;
import io.swagger.annotations.Api;

import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

@Path("/")
@Api(description = "Top CDs Endpoint")
public class TopCDsEndpoint {

    @Inject
    @QLogger
    private Logger logger;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getTopCDs() {

        JsonArrayBuilder array = Json.createArrayBuilder();
        List<Integer> randomCDs = getRandomNumbers();
        for (Integer randomCD : randomCDs) {
            array.add(Json.createObjectBuilder().add("id", randomCD));
        }
        return array.build().toString();
    }

    private List<Integer> getRandomNumbers() {
        List<Integer> randomCDs = new ArrayList<>();
        Random r = new Random();
        randomCDs.add(r.nextInt(100) + 1101);
        randomCDs.add(r.nextInt(100) + 1101);
        randomCDs.add(r.nextInt(100) + 1101);
        randomCDs.add(r.nextInt(100) + 1101);
        randomCDs.add(r.nextInt(100) + 1101);

        logger.info("Top CDs are " + randomCDs);

        return randomCDs;
    }
}
