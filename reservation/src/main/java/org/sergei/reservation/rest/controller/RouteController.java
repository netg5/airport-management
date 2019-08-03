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

package org.sergei.reservation.rest.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.sergei.reservation.rest.dto.RouteResponseDTO;
import org.sergei.reservation.rest.dto.response.ResponseDTO;
import org.sergei.reservation.service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Sergei Visotsky
 */
@RestController
@RequestMapping("/routes")
@Api(tags = {"getRouteData"})
public class RouteController {

    private final RouteService routeService;

    @Autowired
    public RouteController(RouteService routeService) {
        this.routeService = routeService;
    }

    @ApiOperation("Get all existing routes")
    @GetMapping(value = "/getAllRoutes")
    public ResponseEntity<ResponseDTO<RouteResponseDTO>> getAllRoutes() {
        return routeService.findAllRoutes();
    }

    @ApiOperation("Get route by ID")
    @GetMapping(value = "/getRouteById/{routeId}")
    public ResponseEntity<ResponseDTO<RouteResponseDTO>> getRouteById(@ApiParam(value = "Route ID which should be found", required = true)
                                                                      @PathVariable("routeId") Long routeId) {
        return routeService.findOneRoute(routeId);
    }
}
