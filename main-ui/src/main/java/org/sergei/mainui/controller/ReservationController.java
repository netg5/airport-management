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

package org.sergei.mainui.controller;

import org.sergei.mainui.model.Reservation;
import org.sergei.mainui.model.ReservationPost;
import org.sergei.mainui.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

/**
 * @author Sergei Visotsky
 */
@Controller
public class ReservationController {

    private final ReservationService reservationService;

    @Autowired
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping("/customers/{customerId}/reservations")
    public String showCustomerReservation(@PathVariable Long customerId, Model model) throws Exception {
        List<Reservation> reservations = reservationService.getReservationsByCustomerId(customerId);
        model.addAttribute("reservations", reservations);
        model.addAttribute("reservationPost", new ReservationPost());
        return "reservation";
    }

    @PostMapping("/reservation/post")
    public String saveReservation(@ModelAttribute ReservationPost reservationPost) {
        reservationService.save(reservationPost);
        return "success_page";
    }
}
