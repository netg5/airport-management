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

package org.sergei.manui.controller;

import org.sergei.manui.model.Aircraft;
import org.sergei.manui.service.AircraftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author Sergei Visotsky
 */
@Controller
public class AircraftController {

    private final AircraftService aircraftService;

    @Autowired
    public AircraftController(AircraftService aircraftService) {
        this.aircraftService = aircraftService;
    }

    @GetMapping("/aircrafts/{aircraftId}")
    public String aircraftDataPage(@PathVariable Long aircraftId, Model model) {
        ResponseEntity<Aircraft> aircraft = aircraftService.getAircraftById(aircraftId);
        Aircraft aircraftResponseBody = aircraft.getBody();
        model.addAttribute("aircraft", aircraftResponseBody);
        model.addAttribute("aircraftPost", new Aircraft());
        return "aircraft";
    }

    @PostMapping("/aircrafts")
    public String saveAircraft(@ModelAttribute Aircraft aircraft) {
        aircraftService.save(aircraft);
        return "success_page";
    }
}
