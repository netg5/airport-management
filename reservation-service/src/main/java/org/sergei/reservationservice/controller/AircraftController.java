/*
 * Copyright 2018-2019 the original author or authors.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package org.sergei.reservationservice.controller;

import io.swagger.annotations.*;
import org.sergei.reservationservice.dto.AircraftDTO;
import org.sergei.reservationservice.service.AircraftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static org.sergei.reservationservice.controller.util.LinkUtil.setLinksForAircraft;
import static org.sergei.reservationservice.controller.util.LinkUtil.setLinksForAllAircrafts;

/**
 * @author Sergei Visotsky
 */
@Api(
        value = "/flight-api/aircrafts",
        produces = "application/json",
        consumes = "application/json"
)
@RestController
@RequestMapping(value = "/aircrafts", produces = "application/json")
public class AircraftController {

    private final AircraftService aircraftService;

    @Autowired
    public AircraftController(AircraftService aircraftService) {
        this.aircraftService = aircraftService;
    }

    @ApiOperation(value = "Get all existing aircrafts")
    @GetMapping
    public ResponseEntity<Resources> getAllAircraft() {
        List<AircraftDTO> aircrafts = aircraftService.findAll();
        return new ResponseEntity<>(setLinksForAllAircrafts(aircrafts), HttpStatus.OK);
    }

    @ApiOperation(value = "Get all existing aircrafts paginated")
    @GetMapping(params = {"page", "size"})
    public ResponseEntity<Resources> getAllAircraftPaginated(@ApiParam("Number of the page")
                                                             @RequestParam("page") int page,
                                                             @ApiParam("Maximum number of content blocks on the page")
                                                             @RequestParam("size") int size) {
        Page<AircraftDTO> aircrafts = aircraftService.findAllPaginated(page, size);
        return new ResponseEntity<>(setLinksForAllAircrafts(aircrafts), HttpStatus.OK);
    }

    @ApiOperation("Get aircraftDTO by ID")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 404, message = "Aircraft with this ID not found")
            }
    )
    @GetMapping("/{aircraftId}")
    public ResponseEntity<AircraftDTO> getAircraftById(@ApiParam(value = "Aircraft ID which should be found", required = true)
                                                       @PathVariable("aircraftId") Long aircraftId) {
        AircraftDTO aircraftDTO = aircraftService.findOne(aircraftId);
        return new ResponseEntity<>(setLinksForAircraft(aircraftDTO), HttpStatus.OK);
    }

    @ApiOperation(value = "Save aircraft", notes = "Operation allowed for the ROLE_ADMIN only")
    @PostMapping(consumes = "application/json")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<AircraftDTO> saveAircraft(@ApiParam(value = "Aircraft which should be saved", required = true)
                                                    @RequestBody AircraftDTO aircraftDTO) {
        return new ResponseEntity<>(aircraftService.save(aircraftDTO), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Update aircraft data", notes = "Operation allowed for the ROLE_ADMIN only")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 404, message = "Aircraft with this ID not found")
            }
    )
    @PutMapping(value = "/{aircraftId}", consumes = "application/json")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<AircraftDTO> updateAircraft(@ApiParam(value = "Aircraft ID which should be updated", required = true)
                                                      @PathVariable("aircraftId") Long aircraftId,
                                                      @ApiParam(value = "Update aircraft", required = true)
                                                      @RequestBody AircraftDTO aircraftDTO) {
        AircraftDTO aircraft = aircraftService.update(aircraftId, aircraftDTO);
        return new ResponseEntity<>(setLinksForAircraft(aircraft), HttpStatus.OK);
    }

    @ApiOperation(value = "Update one field of the aircraft", notes = "Operation allowed for the ROLE_ADMIN only")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 404, message = "Aircraft with this ID not found")
            }
    )
    @PatchMapping(value = "/{aircraftId}/patch", consumes = "application/json")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<AircraftDTO> patchAircraft(@ApiParam(value = "Aircraft ID which should be updated", required = true)
                                                     @PathVariable("aircraftId") Long aircraftId,
                                                     @RequestBody Map<String, Object> params) {

        AircraftDTO aircraftDTO = aircraftService.patch(aircraftId, params);
        return new ResponseEntity<>(setLinksForAircraft(aircraftDTO), HttpStatus.OK);
    }

    @ApiOperation(value = "Delete aircraft", notes = "Operation allowed for the ROLE_ADMIN only")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 404, message = "Aircraft with this ID not found")
            }
    )
    @DeleteMapping(value = "/{aircraftId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<AircraftDTO> deleteAircraft(@ApiParam(value = "Aircraft ID which should be deleted", required = true)
                                                      @PathVariable("aircraftId") Long aircraftId) {
        return new ResponseEntity<>(aircraftService.delete(aircraftId), HttpStatus.NO_CONTENT);
    }
}
