package org.sergei.ticketservice.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.sergei.ticketservice.model.Ticket;
import org.sergei.ticketservice.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.sergei.ticketservice.controller.util.LinkUtil.setLinksForTicket;

/**
 * @author Sergei Visotsky
 */
@Api(
        value = "/ticket-api/tickets",
        produces = "application/json"
)
@RestController
@RequestMapping(value = "/tickets", produces = "application/json")
public class TicketController {

    private final TicketRepository ticketRepository;

    @Autowired
    public TicketController(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @ApiOperation("Get ticket for customer by ID")
    @GetMapping
    public ResponseEntity<Resources<Ticket>> getAllForCustomer(@ApiParam(value = "Customer ID whose ticket should be found", required = true)
                                                               @RequestParam("customerId") Long customerId,
                                                               @ApiParam(value = "Place with which ticket should be found")
                                                               @RequestParam(value = "place", required = false) String place,
                                                               @ApiParam(value = "Distance with which ticket should be found")
                                                               @RequestParam(value = "distance", required = false) Double distance) {
        List<Ticket> ticketList = ticketRepository.findByCustomerIdPlaceOrDistance(customerId, place, distance);
        return new ResponseEntity<>(setLinksForTicket(ticketList, customerId), HttpStatus.OK);
    }

    @ApiOperation("Get ticket for customer by ID")
    @GetMapping(params = {"page", "size"})
    public ResponseEntity<Resources<Ticket>> getAllForCustomerPaginated(@ApiParam(value = "Customer ID whose ticket should be found", required = true)
                                                                        @RequestParam("customerId") Long customerId,
                                                                        @ApiParam(value = "Place with which ticket should be found")
                                                                        @RequestParam(value = "place", required = false) String place,
                                                                        @ApiParam(value = "Distance with which ticket should be found")
                                                                        @RequestParam(value = "distance", required = false) Double distance,
                                                                        @ApiParam("Number of page")
                                                                        @RequestParam("page") int page,
                                                                        @ApiParam("Number of elements per page")
                                                                        @RequestParam("size") int size) {
        Page<Ticket> ticketList = ticketRepository.findByCustomerIdPlaceOrDistancePageable(customerId, place, distance, PageRequest.of(page, size));
        return new ResponseEntity<>(setLinksForTicket(ticketList, customerId), HttpStatus.OK);
    }
}
