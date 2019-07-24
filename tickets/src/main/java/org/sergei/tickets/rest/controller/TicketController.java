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

package org.sergei.tickets.rest.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.sergei.tickets.jpa.model.Ticket;
import org.sergei.tickets.rest.dto.TicketRequestDTO;
import org.sergei.tickets.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Sergei Visotsky
 */
@Api(
        value = "/ticket-rest/tickets",
        produces = "application/json"
)
@RestController
@RequestMapping(value = "/tickets", produces = "application/json")
public class TicketController {

    private final TicketService ticketService;

    @Autowired
    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @ApiOperation("Get ticket for customer by ID")
    @GetMapping
    public ResponseEntity<Resources<Ticket>> findAllTickets(@RequestBody TicketRequestDTO request) {
        List<Ticket> ticketList = ticketService.findAllTickets(request);
        return new ResponseEntity<>(linkUtil.setLinksForTicket(ticketList, customerId), HttpStatus.OK);
    }

    @ApiOperation("Get ticket for customer by ID")
    @GetMapping(params = {"page", "size"})
    public ResponseEntity<Resources<Ticket>> findAllTicketsPageable(@RequestBody TicketRequestDTO request,
                                                                    @ApiParam("Number of page")
                                                                    @RequestParam("page") int page,
                                                                    @ApiParam("Number of elements per page")
                                                                    @RequestParam("size") int size) {
        Page<Ticket> ticketList = ticketService.findAllTicketsPageable(request, page, size);
        return new ResponseEntity<>(linkUtil.setLinksForTicket(ticketList, customerId), HttpStatus.OK);
    }
}
