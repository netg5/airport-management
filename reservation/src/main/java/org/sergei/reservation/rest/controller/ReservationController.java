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
import org.sergei.reservation.rest.dto.ReservationRequestDTO;
import org.sergei.reservation.rest.dto.ReservationResponseDTO;
import org.sergei.reservation.rest.dto.response.ResponseDTO;
import org.sergei.reservation.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author Sergei Visotsky
 */
@RestController
@Api(tags = {"reservationCrudOperations"})
public class ReservationController {

    private final ReservationService reservationService;

    @Autowired
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @ApiOperation("Get all reservations for passenger")
    @GetMapping(value = "/getAllReservationsForCustomer/{passengerId}")
    public ResponseEntity<ResponseDTO<ReservationResponseDTO>>
    getAllReservationForCustomer(@ApiParam(value = "Passenger ID whose reservations should be found", required = true)
                                 @PathVariable("passengerId") Long passengerId) {
        return reservationService.findAllForPassenger(passengerId);
    }

    @ApiOperation("Get one reservation by ID for the passenger")
    @GetMapping(value = "/getAllReservationsForCustomer/{passengerId}", produces = "application/json")
    public ResponseEntity<ResponseDTO<ReservationResponseDTO>>
    getOneReservationForCustomer(@ApiParam(value = "Passenger ID who made a reservation", required = true)
                                 @PathVariable("passengerId") Long passengerId,
                                 @ApiParam(value = "Reservation ID which which was made", required = true)
                                 @RequestParam("reservationId") Long reservationId) {
        return reservationService.findOneForPassenger(passengerId, reservationId);
    }

    @ApiOperation("Create reservation for passenger")
    @PostMapping(value = "/makeReservation/{passengerId}")
    public ResponseEntity<ResponseDTO<ReservationResponseDTO>>
    makeReservation(@PathVariable Long passengerId,
                    @ApiParam(value = "Request to delete reservation", required = true)
                    @RequestBody ReservationRequestDTO request) {
        return reservationService.saveReservation(passengerId, request);
    }

    @ApiOperation("Delete reservation")
    @DeleteMapping("/deleteReservation/{passengerId}")
    public ResponseEntity<ResponseDTO<ReservationResponseDTO>>
    deleteReservation(@ApiParam(value = "Passenger ID who made reservation", required = true)
                      @PathVariable("passengerId") Long passengerId,
                      @ApiParam(value = "Reservation ID which should be deleted", required = true)
                      @RequestParam("reservationId") Long reservationId) {
        return reservationService.deleteReservation(passengerId, reservationId);
    }
}
