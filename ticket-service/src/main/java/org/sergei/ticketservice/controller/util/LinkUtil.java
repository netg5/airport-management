package org.sergei.ticketservice.controller.util;

import org.sergei.ticketservice.model.Ticket;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resources;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 * @author Sergei Visotsky
 * @since 12/17/2018
 */
public final class LinkUtil {

    /**
     * Method to set links for ticket
     *
     * @param ticketList List of tickets
     * @param customerId Customer ID whose tickets are taken
     * @return ticket resource
     */
    public static Resources<Ticket> setLinksForTicket(Iterable<Ticket> ticketList, Long customerId) {
        Resources<Ticket> resources = new Resources<>(ticketList);
        String uriString = ServletUriComponentsBuilder.fromCurrentRequest().build().toUriString();
        resources.add(new Link(uriString, "self"));
        resources.add(new Link("http://127.0.0.1:8080/flight-api/customers/" + customerId, "customer"));
        return resources;
    }
}
