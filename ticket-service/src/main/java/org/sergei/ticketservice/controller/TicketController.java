package org.sergei.ticketservice.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.sergei.ticketservice.model.Ticket;
import org.sergei.ticketservice.repository.TicketRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

/**
 * @author Sergei Visotsky
 * @since 11/27/2018
 */
@Api(
        value = "/ticket-api/v1/tickets",
        produces = "application/json"
)
@RestController
@RequestMapping(value = "/v1/tickets", produces = "application/json")
public class TicketController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TicketController.class);

    @Autowired
    private TicketRepository ticketRepository;

    @ApiOperation("Get ticket for customer by ID")
    @GetMapping
    public ResponseEntity<Resources<Ticket>> getAllForCustomer(@ApiParam(value = "Customer ID whose ticket should be found", required = true)
                                                               @RequestParam("customerId") Long customerId) {
        List<Ticket> ticketList = ticketRepository.findAllByCustomerId(customerId);
        ticketList.forEach(
                ticket ->
                        LOGGER.info(ticket.getAircraftName())
        );
        Resources<Ticket> resources = new Resources<>(ticketList);
        String uriString = ServletUriComponentsBuilder.fromCurrentRequest().build().toUriString();
        resources.add(new Link(uriString, "self"));
        return new ResponseEntity<>(resources, HttpStatus.OK);
    }
}
