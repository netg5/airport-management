package org.sergei.orchestration.rest.controller;

import io.swagger.annotations.Api;
import jdk.jshell.spi.ExecutionControl.NotImplementedException;
import org.sergei.orchestration.rest.dto.TicketDTO;
import org.sergei.orchestration.rest.dto.response.ResponseDTO;
import org.sergei.orchestration.service.TicketService;
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
@Api(tags = {"Ticket information"})
public class TicketRestController {

    private final TicketService ticketService;

    @Autowired
    public TicketRestController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping(value = "/findAllTicketsForPassenger/{passengerId}")
    public ResponseEntity<ResponseDTO<TicketDTO>> findAllTicketsForPassenger(@PathVariable("passengerId") Long passengerId,
                                                                             @RequestParam("currency") String currency) throws NotImplementedException {
        return ticketService.findAllTickets(passengerId, currency);
    }

}
