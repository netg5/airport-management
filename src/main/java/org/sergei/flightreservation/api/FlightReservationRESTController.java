/*
 * Copyright (c) 2018 Sergei Visotsky
 */

package org.sergei.flightreservation.api;

import org.sergei.flightreservation.dto.FlightReservationDTO;
import org.sergei.flightreservation.service.FlightReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author Sergei Visotsky, 2018
 */
@RestController
@RequestMapping(value = "/api/v1", produces = "application/json")
public class FlightReservationRESTController {

    @Autowired
    private FlightReservationService flightReservationService;

    @PostMapping("/customers/{customerId}/reservation")
    public ResponseEntity<FlightReservationDTO> createReservation(@PathVariable("customerId") Long customerId,
                                                                  @RequestBody FlightReservationDTO flightReservationDTO) {
        return new ResponseEntity<>(flightReservationService.saveForCustomer(customerId, flightReservationDTO), HttpStatus.CREATED);
    }
}
