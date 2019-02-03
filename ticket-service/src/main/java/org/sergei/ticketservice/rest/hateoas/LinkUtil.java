/*
 * Copyright 2018-2019 the original author.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.sergei.ticketservice.rest.hateoas;

import org.sergei.ticketservice.model.Ticket;
import org.sergei.ticketservice.properties.GatewayProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resources;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 * @author Sergei Visotsky
 */
@Component
public class LinkUtil {

    private final GatewayProperties gatewayProperties;

    @Autowired
    public LinkUtil(GatewayProperties gatewayProperties) {
        this.gatewayProperties = gatewayProperties;
    }

    /**
     * Method to set links for ticket
     *
     * @param ticketList List of tickets
     * @param customerId Customer ID whose tickets are taken
     * @return ticket resource
     */
    public Resources<Ticket> setLinksForTicket(Iterable<Ticket> ticketList, Long customerId) {
        Resources<Ticket> resources = new Resources<>(ticketList);
        String uriString = ServletUriComponentsBuilder.fromCurrentRequest().build().toUriString();
        resources.add(new Link(uriString, "self"));
        resources.add(new Link("http://127.0.0.1:" +
                gatewayProperties.getPort() + "/flight-api/customers/" + customerId, "customer"));
        return resources;
    }
}
