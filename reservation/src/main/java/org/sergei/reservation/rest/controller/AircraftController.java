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
import org.sergei.reservation.rest.dto.AircraftDTO;
import org.sergei.reservation.rest.dto.AircraftRequestDTO;
import org.sergei.reservation.rest.dto.response.ResponseDTO;
import org.sergei.reservation.service.AircraftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author Sergei Visotsky
 */
@RestController
@Api(tags = {"getAircraftData"})
public class AircraftController {

    private final AircraftService aircraftService;

    @Autowired
    public AircraftController(AircraftService aircraftService) {
        this.aircraftService = aircraftService;
    }

    @ApiOperation(value = "Get all existing aircrafts", produces = "application/json", consumes = "application/json")
    @GetMapping(value = "/getAllAircrafts", produces = "application/json")
    public ResponseEntity<ResponseDTO<AircraftDTO>> getAllAircraft() {
        return aircraftService.findAll();
    }

    @ApiOperation("Get aircraftDTO by ID")
    @PostMapping(value = "/getAircraftById")
    public ResponseEntity<ResponseDTO<AircraftDTO>> getAircraftById(@RequestBody AircraftRequestDTO request) {
        return aircraftService.findOne(request);
    }
}
