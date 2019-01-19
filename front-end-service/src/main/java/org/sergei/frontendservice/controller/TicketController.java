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

package org.sergei.frontendservice.controller;

import org.sergei.frontendservice.model.Ticket;
import org.sergei.frontendservice.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.IOException;
import java.util.List;

/**
 * @author Sergei Visotsky
 */
@Controller
public class TicketController {

    private final TicketService ticketService;

    @Autowired
    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping("/customers/{customerId}/tickets")
    public String showCustomerTickets(@PathVariable Long customerId, Model model) throws IOException {
        ResponseEntity<List<Ticket>> tickets = ticketService.findAllTicketsByCustomerId(customerId);
        model.addAttribute("tickets", tickets);
        /*List<Ticket> ticketsResponseBody = tickets.getBody();
        model.addAttribute("firstName", Objects.requireNonNull(ticketsResponseBody).get(0).getFirstName());
        model.addAttribute("lastName", ticketsResponseBody.get(0).getLastName());
        model.addAttribute("routeId", ticketsResponseBody.get(0).getRouteId());
        model.addAttribute("place", ticketsResponseBody.get(0).getPlace());
        model.addAttribute("distance", ticketsResponseBody.get(0).getDistance());
        model.addAttribute("price", ticketsResponseBody.get(0).getPrice());
        model.addAttribute("aircraftName", ticketsResponseBody.get(0).getAircraftName());*/
        return "tickets";
    }
}