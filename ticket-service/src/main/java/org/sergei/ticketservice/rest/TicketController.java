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

package org.sergei.ticketservice.rest;

import io.swagger.annotations.*;
import org.sergei.ticketservice.rest.hateoas.LinkUtil;
import org.sergei.ticketservice.model.Ticket;
import org.sergei.ticketservice.service.Constants;
import org.sergei.ticketservice.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

    private final LinkUtil linkUtil;
    private final TicketService ticketService;

    @Autowired
    public TicketController(LinkUtil linkUtil, TicketService ticketService) {
        this.linkUtil = linkUtil;
        this.ticketService = ticketService;
    }

    @ApiOperation("Get ticket for customer by ID")
    @ApiResponses({
            @ApiResponse(code = 404, message = Constants.TICKETS_NOT_FOUND)
    })
    @GetMapping
    public ResponseEntity<Resources<Ticket>> findAllTickets(@ApiParam(value = "Customer ID whose ticket should be found", required = true)
                                                            @RequestParam("customerId") Long customerId,
                                                            @ApiParam(value = "Place with which ticket should be found")
                                                            @RequestParam(value = "place", required = false) String place,
                                                            @ApiParam(value = "Distance with which ticket should be found")
                                                            @RequestParam(value = "distance", required = false) Double distance) {
        List<Ticket> ticketList = ticketService.findAllTickets(customerId, place, distance);
        return new ResponseEntity<>(linkUtil.setLinksForTicket(ticketList, customerId), HttpStatus.OK);
    }

    @ApiOperation("Get ticket for customer by ID")
    @ApiResponses({
            @ApiResponse(code = 404, message = "Customer has no tickets")
    })
    @GetMapping(params = {"page", "size"})
    public ResponseEntity<Resources<Ticket>> findAllTicketsPageable(@ApiParam(value = "Customer ID whose ticket should be found", required = true)
                                                                    @RequestParam("customerId") Long customerId,
                                                                    @ApiParam(value = "Place with which ticket should be found")
                                                                    @RequestParam(value = "place", required = false) String place,
                                                                    @ApiParam(value = "Distance with which ticket should be found")
                                                                    @RequestParam(value = "distance", required = false) Double distance,
                                                                    @ApiParam("Number of page")
                                                                    @RequestParam("page") int page,
                                                                    @ApiParam("Number of elements per page")
                                                                    @RequestParam("size") int size) {
        Page<Ticket> ticketList = ticketService.findAllTicketsPageable(customerId, place, distance, page, size);
        return new ResponseEntity<>(linkUtil.setLinksForTicket(ticketList, customerId), HttpStatus.OK);
    }
}
