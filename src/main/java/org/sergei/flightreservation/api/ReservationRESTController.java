/*
 * Copyright (c) 2018 Sergei Visotsky
 */

package org.sergei.flightreservation.api;

import org.sergei.flightreservation.dto.ReservationDTO;
import org.sergei.flightreservation.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author Sergei Visotsky, 2018
 */
@RestController
@RequestMapping(value = "/api/v1", produces = "application/json")
public class ReservationRESTController {

    @Autowired
    private ReservationService reservationService;

    @PostMapping("/customers/{customerId}/reservation")
    public ResponseEntity<ReservationDTO> createReservation(@PathVariable("customerId") Long customerId,
                                                            @RequestBody ReservationDTO reservationDTO) {
        return new ResponseEntity<>(reservationService.saveForCustomer(customerId, reservationDTO), HttpStatus.CREATED);
    }
}
