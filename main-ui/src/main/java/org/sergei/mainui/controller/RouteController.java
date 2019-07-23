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

import org.sergei.mainui.model.Route;
import org.sergei.mainui.model.RoutePost;
import org.sergei.mainui.service.RouteService;
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
public class RouteController {

    private final RouteService routeService;

    @Autowired
    public RouteController(RouteService routeService) {
        this.routeService = routeService;
    }

    @GetMapping("/routes/{routeId}")
    public String routeDataPage(@PathVariable Long routeId, Model model) {
        ResponseEntity<Route> route = routeService.getRouteById(routeId);
        Route routeResponseBody = route.getBody();
        model.addAttribute("route", routeResponseBody);
        model.addAttribute("routePost", new RoutePost());
        return "route";
    }

    @PostMapping("/routes")
    public String saveRoute(@ModelAttribute RoutePost routePost) {
        routeService.save(routePost);
        return "success_page";
    }
}