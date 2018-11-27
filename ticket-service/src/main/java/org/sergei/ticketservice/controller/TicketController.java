package org.sergei.ticketservice.controller;

import org.sergei.ticketservice.model.Ticket;
import org.sergei.ticketservice.service.TicketService;
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
@RestController
@RequestMapping(value = "/v1/tickets", produces = "application/json")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @GetMapping
    public ResponseEntity<Ticket> getForCustomer(@RequestParam("customerId") Long customerId) {
        return new ResponseEntity<>(ticketService.findAllForCustomer(customerId), HttpStatus.OK);
    }
}
