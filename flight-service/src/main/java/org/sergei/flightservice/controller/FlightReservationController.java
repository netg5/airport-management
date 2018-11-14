/*
 * Copyright (c) 2018 Sergei Visotsky
 */

package org.sergei.flightservice.controller;

import io.swagger.annotations.*;
import org.sergei.flightservice.dto.FlightReservationDTO;
import org.sergei.flightservice.dto.FlightReservationExtendedDTO;
import org.sergei.flightservice.service.FlightReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Sergei Visotsky, 2018
 */
@Api(
        value = "/api/v1/customers/{customerId}/reservation/",
        description = "Flight reservation API methods",
        produces = "application/json",
        consumes = "application/json"
)
@RestController
@RequestMapping(value = "/api/v1/customers", produces = "application/json")
public class FlightReservationController {

    @Autowired
    private FlightReservationService flightReservationService;

    @ApiOperation("Get one reservation by ID for the customer")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 404, message = "Invalid customer or reservation ID")
            }
    )
    @GetMapping("/{customerId}/reservation/{reservationId}")
    public ResponseEntity<FlightReservationExtendedDTO> getOneForCustomer(@ApiParam(value = "Customer ID whoķ made a reservation", required = true)
                                                                          @PathVariable("customerId") Long customerId,
                                                                          @ApiParam(value = "Reservation ID which which was made", required = true)
                                                                          @PathVariable("reservationId") Long reservationId) {
        return new ResponseEntity<>(
                flightReservationService.getOneForCustomerById(customerId, reservationId),
                HttpStatus.OK);
    }

    @ApiOperation("Get all reservations for customer")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 404, message = "Invalid customer ID")
            }
    )
    @GetMapping("/{customerId}/reservation")
    public ResponseEntity<List<FlightReservationExtendedDTO>> getAllForCustomer(@ApiParam(value = "Customer ID whose reservations should be found", required = true)
                                                                                @PathVariable("customerId") Long customerId) {
        return new ResponseEntity<>(
                flightReservationService.getAllReservationsForCustomer(customerId),
                HttpStatus.OK);
    }

    @ApiOperation("Create reservation for customer")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 404, message = "Invalid customer ID")
            }
    )
    @PostMapping(value = "/{customerId}/reservation", consumes = "application/json")
    public ResponseEntity<FlightReservationDTO> createReservation(@ApiParam(value = "Customer ID for whom reservation should be created", required = true)
                                                                  @PathVariable("customerId") Long customerId,
                                                                  @ApiParam(value = "Created reservation", required = true)
                                                                  @RequestBody FlightReservationDTO flightReservationDTO) {
        return new ResponseEntity<>(
                flightReservationService.createReservationForCustomer(customerId, flightReservationDTO),
                HttpStatus.CREATED);
    }

    @ApiOperation("Update reservation by customer ID")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 404, message = "Invalid customer or reservation ID")
            }
    )
    @PutMapping(value = "/{customerId}/reservation/{reservationId}", consumes = "application/json")
    public ResponseEntity<FlightReservationDTO> updateReservation(@ApiParam(value = "Customer ID who made reservation", required = true)
                                                                  @PathVariable("customerId") Long customerId,
                                                                  @ApiParam(value = "Reservation ID which should be updated", required = true)
                                                                  @PathVariable("reservationId") Long reservationId,
                                                                  @ApiParam(value = "Saved flight reservation", required = true)
                                                                  @RequestBody FlightReservationDTO flightReservationDTO) {
        return new ResponseEntity<>(
                flightReservationService.updateReservationForCustomer(customerId, reservationId, flightReservationDTO),
                HttpStatus.ACCEPTED);
    }

    @ApiOperation("Delete reservation")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 404, message = "Invalid reservation ID")
            }
    )
    @DeleteMapping("/reservation/{reservationId}")
    public ResponseEntity<FlightReservationExtendedDTO> deleteReservation(@ApiParam(value = "Reservation ID which should be deleted", required = true)
                                                                          @PathVariable("reservationId") Long reservationId) {
        return new ResponseEntity<>(
                flightReservationService.delete(reservationId),
                HttpStatus.NO_CONTENT);
    }
}
