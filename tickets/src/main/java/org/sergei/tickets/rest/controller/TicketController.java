package org.sergei.tickets.rest.controller;

import io.swagger.annotations.Api;
import org.sergei.tickets.rest.dto.TicketDTO;
import org.sergei.tickets.rest.dto.response.ResponseDTO;
import org.sergei.tickets.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Sergei Visotsky
 */
@RestController
@Api(tags = {"findAllTicketsForPassenger"})
public class TicketController {

    private final TicketService ticketService;

    @Autowired
    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping(value = "/findAllTicketsForPassenger/{passengerId}")
    public ResponseEntity<ResponseDTO<TicketDTO>> findAllTicketsForPassenger(@PathVariable("passengerId") Long passengerId,
                                                                             @RequestParam("currency") String currency) {
        return ticketService.findAllTickets(passengerId, currency);
    }
}
