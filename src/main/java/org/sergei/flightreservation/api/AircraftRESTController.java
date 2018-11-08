/*
 * Copyright (c) Sergei Visotsky, 2018
 */

package org.sergei.flightreservation.api;

import io.swagger.annotations.ApiOperation;
import org.sergei.flightreservation.dto.AircraftDTO;
import org.sergei.flightreservation.service.AircraftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Sergei Visotsky, 2018
 */
@RestController
@RequestMapping(value = "/api/v1/aircraft", produces = "application/json")
public class AircraftRESTController {

    @Autowired
    private AircraftService aircraftService;

    @ApiOperation("Get all existing aircrafts")
    @GetMapping
    public ResponseEntity<List<AircraftDTO>> getAllAircraft() {
        return new ResponseEntity<>(aircraftService.findAll(), HttpStatus.OK);
    }

    @ApiOperation("Get aircraft by ID")
    @GetMapping("/{aircraftId}")
    public ResponseEntity<AircraftDTO> getAircraftById(@PathVariable("aircraftId") Long aircraftId) {
        return new ResponseEntity<>(aircraftService.findOne(aircraftId), HttpStatus.OK);
    }

    @ApiOperation("Save aircraft")
    @PostMapping(consumes = "application/json")
    @PreAuthorize("hasAnyAuthority('ROLE ADMIN')")
    public ResponseEntity<AircraftDTO> saveAircraft(@RequestBody AircraftDTO aircraftDTO) {
        return new ResponseEntity<>(aircraftService.save(aircraftDTO), HttpStatus.CREATED);
    }

    @ApiOperation("Update aircraft data")
    @PutMapping(value = "/{aircraftId}", consumes = "application/json")
    @PreAuthorize("hasAnyAuthority('ROLE ADMIN')")
    public ResponseEntity<AircraftDTO> updateAircraft(@PathVariable("aircraftId") Long aircraftId,
                                                      @RequestBody AircraftDTO customerDTO) {
        return new ResponseEntity<>(aircraftService.update(aircraftId, customerDTO), HttpStatus.OK);
    }

    @ApiOperation("Delete aircraft")
    @DeleteMapping(value = "/{aircraftId}")
    @PreAuthorize("hasAnyAuthority('ROLE ADMIN')")
    public ResponseEntity<AircraftDTO> deleteAircraft(@PathVariable("aircraftId") Long aircraftId) {
        return new ResponseEntity<>(aircraftService.delete(aircraftId), HttpStatus.NO_CONTENT);
    }
}
