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

import org.sergei.frontendservice.model.Aircraft;
import org.sergei.frontendservice.model.Customer;
import org.sergei.frontendservice.service.AircraftService;
import org.sergei.frontendservice.service.CustomerService;
import org.sergei.frontendservice.service.ReservationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
import java.util.Objects;

/**
 * @author Sergei Visotsky
 */
@Controller
public class MainController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MainController.class);

    private final CustomerService customerService;
    private final ReservationService reservationService;
    private final AircraftService aircraftService;

    @Autowired
    public MainController(CustomerService customerService,
                          ReservationService reservationService,
                          AircraftService aircraftService) {
        this.customerService = customerService;
        this.reservationService = reservationService;
        this.aircraftService = aircraftService;
    }

    @GetMapping("/")
    public String showHomePage() {
        return "index";
    }

    @GetMapping("/customer")
    public String customerDataPage(Model model) throws IOException {
        final Long customerId = 1L;
        ResponseEntity<Customer> customer = customerService.getCustomerById(customerId);
        Customer customerResponseBody = customer.getBody();
        model.addAttribute("customerId", Objects.requireNonNull(customerResponseBody).getCustomerId());
        model.addAttribute("firstName", customerResponseBody.getFirstName());
        model.addAttribute("lastName", customerResponseBody.getLastName());
        model.addAttribute("age", customerResponseBody.getAge());

        /*ResponseEntity<List<Reservation>> reservations = reservationService.getReservationsByCustomerId(customerId);
        model.addAttribute("reservations", reservations);*/
        return "customer";
    }

    @GetMapping("/aircraft")
    public String aircraftDataPage(Model model) {
        final Long aircraftId = 1L;
        ResponseEntity<Aircraft> aircraft = aircraftService.getAircraftById(aircraftId);
        Aircraft aircraftResponseBody = aircraft.getBody();
        model.addAttribute("aircraftId", Objects.requireNonNull(aircraftResponseBody).getAircraftId());
        model.addAttribute("model", aircraftResponseBody.getModel());
        model.addAttribute("aircraftName", aircraftResponseBody.getAircraftName());
        model.addAttribute("aircraftWeight", aircraftResponseBody.getAircraftWeight());
        model.addAttribute("maxPassengers", aircraftResponseBody.getMaxPassengers());
        return "aircraft";
    }
}
