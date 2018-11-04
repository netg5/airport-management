/*
 * Copyright (c) 2018 Sergei Visotsky
 */

package org.sergei.flightreservation.api;

import org.sergei.flightreservation.dto.FlightReservationDTO;
import org.sergei.flightreservation.dto.FlightReservationExtendedDTO;
import org.sergei.flightreservation.service.FlightReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Sergei Visotsky, 2018
 */
@RestController
@RequestMapping(value = "/api/v1", produces = "application/json")
public class FlightReservationRESTController {

    @Autowired
    private FlightReservationService flightReservationService;

    @GetMapping("/customers/{customerId}/reservation/{reservationId}")
    public ResponseEntity<FlightReservationExtendedDTO> getOneForCustomer(@PathVariable("customerId") Long customerId,
                                                                          @PathVariable("reservationId") Long reservationId) {
        return new ResponseEntity<>(flightReservationService.getOneForCustomerById(customerId, reservationId), HttpStatus.OK);
    }

    @GetMapping("/customers/{customerId}/reservation")
    public ResponseEntity<List<FlightReservationExtendedDTO>> getAllForCustomer(@PathVariable("customerId") Long customerId) {
        return new ResponseEntity<>(flightReservationService.getAllReservationsForCustomer(customerId), HttpStatus.OK);
    }

    @PostMapping(value = "/customers/{customerId}/reservation", consumes = "application/json")
    public ResponseEntity<FlightReservationDTO> createReservation(@PathVariable("customerId") Long customerId,
                                                                  @RequestBody FlightReservationDTO flightReservationDTO) {
        return new ResponseEntity<>(flightReservationService.createReservationForCustomer(customerId, flightReservationDTO), HttpStatus.CREATED);
    }
}
