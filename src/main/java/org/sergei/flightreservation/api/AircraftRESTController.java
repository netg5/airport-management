/*
 * Copyright (c) Sergei Visotsky, 2018
 */

package org.sergei.flightreservation.api;

import org.sergei.flightreservation.dto.AircraftDTO;
import org.sergei.flightreservation.service.AircraftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/aircraft", produces = {"application/json", "application/xml"})
public class AircraftRESTController {

    @Autowired
    private AircraftService aircraftService;

    @GetMapping
    public ResponseEntity<List<AircraftDTO>> getAllAircraft() {
        return new ResponseEntity<>(aircraftService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{aircraftId}")
    public ResponseEntity<AircraftDTO> getAircraftById(@PathVariable("aircraftId") Long aircraftId) {
        return new ResponseEntity<>(aircraftService.findOne(aircraftId), HttpStatus.OK);
    }

    @PostMapping(consumes = {"application/json", "application/xml"})
    public ResponseEntity<AircraftDTO> saveAircraft(@RequestBody AircraftDTO aircraftDTO) {
        return new ResponseEntity<>(aircraftService.save(aircraftDTO), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{aircraftId}", consumes = {"application/json", "application/xml"})
    public ResponseEntity<AircraftDTO> updateAircraft(@PathVariable("aircraftId") Long aircraftId,
                                                      @RequestBody AircraftDTO customerDTO) {
        return new ResponseEntity<>(aircraftService.update(aircraftId, customerDTO), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{aircraftId}")
    public ResponseEntity<AircraftDTO> deleteAircraft(@PathVariable("aircraftId") Long aircraftId) {
        return new ResponseEntity<>(aircraftService.delete(aircraftId), HttpStatus.OK);
    }
}
