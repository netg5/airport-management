package org.sergei.ticketservice.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.sergei.ticketservice.model.Ticket;
import org.sergei.ticketservice.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
        value = "/ticket-api/tickets",
        produces = "application/json"
)
@RestController
@RequestMapping(value = "/tickets", produces = "application/json")
public class TicketController {

    @Autowired
    private TicketRepository ticketRepository;

    @ApiOperation("Get ticket for customer by ID")
    @GetMapping
    public ResponseEntity<Resources<Ticket>> getAllForCustomer(@ApiParam(value = "Customer ID whose ticket should be found", required = true)
                                                               @RequestParam("customerId") Long customerId,
                                                               @ApiParam(value = "Place with which ticket should be found")
                                                               @RequestParam(value = "place", required = false) String place,
                                                               @ApiParam(value = "Distance with which ticket should be found")
                                                               @RequestParam(value = "distance", required = false) Double distance) {
        List<Ticket> ticketList = ticketRepository.findByCustomerIdPlaceOrDistance(customerId, place, distance);
        Resources<Ticket> resources = new Resources<>(ticketList);
        String uriString = ServletUriComponentsBuilder.fromCurrentRequest().build().toUriString();
        resources.add(new Link(uriString, "self"));
        return new ResponseEntity<>(resources, HttpStatus.OK);
    }

    @ApiOperation("Get ticket for customer by ID")
    @GetMapping(params = {"page", "size"})
    public ResponseEntity<Resources<Ticket>> getAllForCustomerPaginated(@ApiParam(value = "Customer ID whose ticket should be found", required = true)
                                                                        @RequestParam("customerId") Long customerId,
                                                                        @ApiParam(value = "Place with which ticket should be found")
                                                                        @RequestParam(value = "place", required = false) String place,
                                                                        @ApiParam(value = "Distance with which ticket should be found")
                                                                        @RequestParam(value = "distance", required = false) Double distance,
                                                                        @ApiParam(value = "Number of page", required = true)
                                                                        @RequestParam("page") int page,
                                                                        @ApiParam(value = "Number of elements per page", required = true)
                                                                        @RequestParam("size") int size) {
        Page<Ticket> ticketList = ticketRepository.findByCustomerIdPlaceOrDistancePageable(customerId, place, distance, PageRequest.of(page, size));
        Resources<Ticket> resources = new Resources<>(ticketList);
        String uriString = ServletUriComponentsBuilder.fromCurrentRequest().build().toUriString();
        resources.add(new Link(uriString, "self"));
        return new ResponseEntity<>(resources, HttpStatus.OK);
    }
}
