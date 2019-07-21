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
import org.sergei.reservation.rest.dto.AircraftResponseDTO;
import org.sergei.reservation.rest.dto.AircraftUpdateRequestDTO;
import org.sergei.reservation.rest.dto.response.ResponseDTO;
import org.sergei.reservation.service.AircraftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author Sergei Visotsky
 */
@Api(
        value = "/reservation/aircrafts",
        produces = "application/json",
        consumes = "application/json"
)
@RestController
@RequestMapping("/aircrafts")
public class AircraftController {

    private final AircraftService aircraftService;

    @Autowired
    public AircraftController(AircraftService aircraftService) {
        this.aircraftService = aircraftService;
    }

    @ApiOperation(value = "Get all existing aircrafts", produces = "application/json", consumes = "application/json")
    @GetMapping
    public ResponseEntity<ResponseDTO<AircraftResponseDTO>> getAllAircraft() {
        return aircraftService.findAll();
    }

    @ApiOperation(value = "Get all existing aircrafts paginated")
    @GetMapping(params = {"page", "size"}, produces = "application/json", consumes = "application/json")
    public ResponseEntity<ResponseDTO<AircraftResponseDTO>>
    getAllAircraftPaginated(@ApiParam("Number of the page")
                            @RequestParam("page") int page,
                            @ApiParam("Maximum number of content blocks on the page")
                            @RequestParam("size") int size) {
        return aircraftService.findAllPaginated(page, size);
    }

    @ApiOperation("Get aircraftDTO by ID")
    @GetMapping(value = "/{aircraftId}", produces = "application/json", consumes = "application/json")
    public ResponseEntity<ResponseDTO<AircraftResponseDTO>>
    getAircraftById(@ApiParam(value = "Aircraft ID which should be found", required = true)
                    @PathVariable("aircraftId") Long aircraftId) {
        return aircraftService.findOne(aircraftId);
    }

    @ApiOperation(value = "Save aircraft", notes = "Operation allowed for the ROLE_ADMIN only")
    @PostMapping(produces = "application/json", consumes = "application/json")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<ResponseDTO<AircraftResponseDTO>>
    saveAircraft(@ApiParam(value = "Aircraft which should be saved", required = true)
                 @RequestBody AircraftResponseDTO aircraftDTO) {
        return aircraftService.save(aircraftDTO);
    }

    @ApiOperation(value = "Update aircraft data", notes = "Operation allowed for the ROLE_ADMIN only")
    @PutMapping(produces = "application/json", consumes = "application/json")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<ResponseDTO<AircraftResponseDTO>>
    updateAircraft(@RequestBody AircraftUpdateRequestDTO request) {
        return aircraftService.update(request);
    }

    @ApiOperation(value = "Update one field of the aircraft", notes = "Operation allowed for the ROLE_ADMIN only")
    @PatchMapping(value = "/{aircraftId}/patch", produces = "application/json", consumes = "application/json")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<ResponseDTO<AircraftResponseDTO>>
    patchAircraft(@ApiParam(value = "Aircraft ID which should be updated", required = true)
                  @PathVariable("aircraftId") Long aircraftId,
                  @RequestBody Map<String, Object> params) {

        return aircraftService.patch(aircraftId, params);
    }

    @ApiOperation(value = "Delete aircraft", notes = "Operation allowed for the ROLE_ADMIN only")
    @DeleteMapping(value = "/{aircraftId}", produces = "application/json", consumes = "application/json")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<ResponseDTO<AircraftResponseDTO>>
    deleteAircraft(@ApiParam(value = "Aircraft ID which should be deleted", required = true)
                   @PathVariable("aircraftId") Long aircraftId) {
        return aircraftService.delete(aircraftId);
    }
}
