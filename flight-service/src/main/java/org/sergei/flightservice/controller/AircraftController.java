/*
 * Copyright (c) Sergei Visotsky, 2018
 */

package org.sergei.flightservice.controller;

import io.swagger.annotations.*;
import org.sergei.flightservice.dto.AircraftDTO;
import org.sergei.flightservice.service.AircraftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Sergei Visotsky, 2018
 */
@Api(
        value = "/api/v1/aircrafts",
        description = "Aircraft API methods",
        produces = "application/json",
        consumes = "application/json"
)
@RestController
@RequestMapping(value = "/api/v1/aircrafts", produces = "application/json")
public class AircraftController {

    @Autowired
    private AircraftService aircraftService;

    @ApiOperation(value = "Get all existing aircrafts")
    @GetMapping
    public ResponseEntity<List<AircraftDTO>> getAllAircraft() {
        return new ResponseEntity<>(aircraftService.findAll(), HttpStatus.OK);
    }

    @ApiOperation("Get aircraft by ID")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 404, message = "Invalid aircraft ID")
            }
    )
    @GetMapping("/{aircraftId}")
    public ResponseEntity<AircraftDTO> getAircraftById(@ApiParam(value = "Aircraft ID which should be found", required = true)
                                                       @PathVariable("aircraftId") Long aircraftId) {
        return new ResponseEntity<>(aircraftService.findOne(aircraftId), HttpStatus.OK);
    }

    @ApiOperation(value = "Save aircraft", notes = "Operation allowed for ADMIN only")
    @PostMapping(consumes = "application/json")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<AircraftDTO> saveAircraft(@ApiParam(value = "Aircraft which should be saved", required = true)
                                                    @RequestBody AircraftDTO aircraftDTO) {
        return new ResponseEntity<>(aircraftService.save(aircraftDTO), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Update aircraft data", notes = "Operation allowed for ADMIN only")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 404, message = "Invalid aircraft ID")
            }
    )
    @PutMapping(value = "/{aircraftId}", consumes = "application/json")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<AircraftDTO> updateAircraft(@ApiParam(value = "Aircraft ID which should be updated", required = true)
                                                      @PathVariable("aircraftId") Long aircraftId,
                                                      @ApiParam(value = "Update aircracft", required = true)
                                                      @RequestBody AircraftDTO customerDTO) {
        return new ResponseEntity<>(aircraftService.update(aircraftId, customerDTO), HttpStatus.OK);
    }

    @ApiOperation(value = "Delete aircraft", notes = "Operation allowed for ADMIN only")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 404, message = "Invalid aircraft ID")
            }
    )
    @DeleteMapping(value = "/{aircraftId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<AircraftDTO> deleteAircraft(@ApiParam(value = "Aircraft ID which should be deleted", required = true)
                                                      @PathVariable("aircraftId") Long aircraftId) {
        return new ResponseEntity<>(aircraftService.delete(aircraftId), HttpStatus.NO_CONTENT);
    }
}
