package org.sergei.ticketservice.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.sergei.ticketservice.model.Ticket;
import org.sergei.ticketservice.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Sergei Visotsky, 2018
 */
@Api(
        value = "/ticket-api/v1/tickets",
        produces = "application/json"
)
@RestController
@RequestMapping(value = "/v1/tickets", produces = "application/json")
public class TicketController {

    @Autowired
    private TicketRepository ticketRepository;

    @ApiOperation("Get ticket for customer by ID")
    @GetMapping
    public ResponseEntity<Ticket> getForCustomer(@ApiParam(value = "Customer ID whose ticket should be found", required = true)
                                                 @RequestParam("customerId") Long customerId) {
        return new ResponseEntity<>(ticketRepository.findOneForCustomer(customerId), HttpStatus.OK);
    }
}
