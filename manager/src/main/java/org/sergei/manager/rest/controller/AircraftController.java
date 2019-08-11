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

package org.sergei.manager.rest.controller;

import io.swagger.annotations.Api;
import org.sergei.manager.rest.dto.AircraftDTO;
import org.sergei.manager.rest.dto.request.AircraftRequestDTO;
import org.sergei.manager.rest.dto.response.ResponseDTO;
import org.sergei.manager.service.AircraftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author Sergei Visotsky
 */
@RestController
@Api(tags = {"aircraftCrudOperations"})
public class AircraftController {

    private final AircraftService aircraftService;

    @Autowired
    public AircraftController(AircraftService aircraftService) {
        this.aircraftService = aircraftService;
    }

    @GetMapping(value = "/getAllAircrafts")
    public ResponseEntity<ResponseDTO<AircraftDTO>> getAllAircraft() {
        return aircraftService.findAll();
    }

    @GetMapping(value = "/getAircraftById/{aircraftId}")
    public ResponseEntity<ResponseDTO<AircraftDTO>> getAircraftById(@PathVariable Long aircraftId) {
        return aircraftService.findById(aircraftId);
    }

    @PostMapping(value = "/getAircraftByModelNumber")
    public ResponseEntity<ResponseDTO<AircraftDTO>> getAircraftByModelNumber(@RequestBody AircraftRequestDTO request) {
        return aircraftService.findByModelNumber(request);
    }

    @PostMapping(value = "/saveAircraft")
    public ResponseEntity<ResponseDTO<AircraftDTO>> saveAircraft(@RequestBody AircraftDTO aircraftDTO) {
        return aircraftService.save(aircraftDTO);
    }
}
