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
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.sergei.manager.rest.dto.AircraftDTO;
import org.sergei.manager.rest.dto.AircraftRequestDTO;
import org.sergei.manager.rest.dto.response.ResponseDTO;
import org.sergei.manager.service.AircraftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * @author Sergei Visotsky
 */
@RestController
@RequestMapping("/aircrafts")
@Api(tags = {"aircraftCrudOperations"})
public class AircraftController {

    private final AircraftService aircraftService;

    @Autowired
    public AircraftController(AircraftService aircraftService) {
        this.aircraftService = aircraftService;
    }

    @ApiOperation(value = "Get all existing aircrafts", produces = "application/json", consumes = "application/json")
    @GetMapping(produces = "application/json")
    public ResponseEntity<ResponseDTO<AircraftDTO>> getAllAircraft() {
        return aircraftService.findAll();
    }

    @ApiOperation("Get aircraftDTO by ID")
    @PostMapping(produces = "application/json", consumes = "application/json")
    public ResponseEntity<ResponseDTO<AircraftDTO>>
    getAircraftById(@RequestBody AircraftRequestDTO request) {
        return aircraftService.findById(request);
    }

    @PostMapping(value = "/save", produces = "application/json", consumes = "application/json")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<ResponseDTO<AircraftDTO>>
    saveAircraft(@ApiParam(value = "Aircraft which should be saved", required = true)
                 @RequestBody AircraftDTO aircraftDTO) {
        return aircraftService.save(aircraftDTO);
    }

    @PostMapping(value = "/update", produces = "application/json", consumes = "application/json")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<ResponseDTO<AircraftDTO>>
    updateAircraft(@RequestBody AircraftDTO request) {
        return aircraftService.update(request);
    }

    @DeleteMapping("/{aircraftId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<ResponseDTO<AircraftDTO>>
    deleteAircraft(@ApiParam(value = "Aircraft ID which should be deleted", required = true)
                   @PathVariable("aircraftId") Long aircraftId) {
        return aircraftService.delete(aircraftId);
    }
}
