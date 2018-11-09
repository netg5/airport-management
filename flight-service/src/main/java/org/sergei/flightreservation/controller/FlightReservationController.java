/*
 * Copyright (c) 2018 Sergei Visotsky
 */

package org.sergei.flightreservation.controller;

import io.swagger.annotations.ApiOperation;
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
@RequestMapping(value = "/api/v1/customers", produces = "application/json")
public class FlightReservationController {

    @Autowired
    private FlightReservationService flightReservationService;

    @ApiOperation("Get one reservation by ID for the customer")
    @GetMapping("/{customerId}/reservation/{reservationId}")
    public ResponseEntity<FlightReservationExtendedDTO> getOneForCustomer(@PathVariable("customerId") Long customerId,
                                                                          @PathVariable("reservationId") Long reservationId) {
        return new ResponseEntity<>(
                flightReservationService.getOneForCustomerById(customerId, reservationId),
                HttpStatus.OK);
    }

    @ApiOperation("Get all reservations for customer")
    @GetMapping("/{customerId}/reservation")
    public ResponseEntity<List<FlightReservationExtendedDTO>> getAllForCustomer(@PathVariable("customerId") Long customerId) {
        return new ResponseEntity<>(
                flightReservationService.getAllReservationsForCustomer(customerId),
                HttpStatus.OK);
    }

    @ApiOperation("Create reservation for customer")
    @PostMapping(value = "/{customerId}/reservation", consumes = "application/json")
    public ResponseEntity<FlightReservationDTO> createReservation(@PathVariable("customerId") Long customerId,
                                                                  @RequestBody FlightReservationDTO flightReservationDTO) {
        return new ResponseEntity<>(
                flightReservationService.createReservationForCustomer(customerId, flightReservationDTO),
                HttpStatus.CREATED);
    }

    @ApiOperation("Update reservation by customer ID")
    @PutMapping(value = "/{customerId}/reservation/{reservationId}", consumes = "application/json")
    public ResponseEntity<FlightReservationDTO> updateReservation(@PathVariable("customerId") Long customerId,
                                                                  @PathVariable("reservationId") Long reservationId,
                                                                  @RequestBody FlightReservationDTO flightReservationDTO) {
        return new ResponseEntity<>(
                flightReservationService.updateReservationForCustomer(customerId, reservationId, flightReservationDTO),
                HttpStatus.ACCEPTED);
    }

    @ApiOperation("Delete reservation")
    @DeleteMapping("/reservation/{reservationId}")
    public ResponseEntity<FlightReservationExtendedDTO> deleteReservation(@PathVariable("reservationId") Long reservationId) {
        return new ResponseEntity<>(
                flightReservationService.delete(reservationId),
                HttpStatus.NO_CONTENT);
    }
}
