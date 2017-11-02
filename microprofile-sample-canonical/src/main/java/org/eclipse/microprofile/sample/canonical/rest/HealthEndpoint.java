/*
 * Copyright (c) 2017 Contributors to the Eclipse Foundation
 *
 * See the NOTICE file(s) distributed with this work for additional
 * information regarding copyright ownership.
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
package org.eclipse.microprofile.sample.canonical.rest;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.sample.canonical.utils.QLogger;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

@Path("/health")
@RequestScoped
public class HealthEndpoint {

    @Inject
    @QLogger
    private Logger logger;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getHealthStatus() {
        final String NAME = "CDs";
        HealthCheckResponse response;
        final List<Integer> randomCDs = getRandomNumbers();
        if (randomCDs.size() > 0) {
            response = HealthCheckResponse.named(NAME).up().build();
        } else {
            response = HealthCheckResponse.named(NAME).down().build();
        }
        return response.toString();
    }

    private List<Integer> getRandomNumbers() {        
        final List<Integer> randomCDs = new ArrayList<>();
        final Random r = new Random();
        randomCDs.add(r.nextInt(100) + 1101);
        randomCDs.add(r.nextInt(100) + 1101);
        randomCDs.add(r.nextInt(100) + 1101);
        randomCDs.add(r.nextInt(100) + 1101);
        randomCDs.add(r.nextInt(100) + 1101);

        logger.info("Top CDs are " + randomCDs);

        return randomCDs;
        
        // Empty result for DOWN
//        return Collections.EMPTY_LIST;
    }
}
