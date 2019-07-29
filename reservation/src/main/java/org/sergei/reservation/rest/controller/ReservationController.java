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

import java.util.Map;

/**
 * @author Sergei Visotsky
 */
@Api(
        value = "/reservation/customers/{passengerId}/reservations/",
        produces = "application/json",
        consumes = "application/json"
)
@RestController
@RequestMapping(value = "/passengers")
public class ReservationController {

    private final ReservationService reservationService;

    @Autowired
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @ApiOperation("Get all reservations for passenger")
    @GetMapping(value = "/{passengerId}/reservations", produces = "application/json")
    public ResponseEntity<ResponseDTO<ReservationResponseDTO>>
    getAllForCustomer(@ApiParam(value = "Passenger ID whose reservations should be found", required = true)
                      @PathVariable("passengerId") Long passengerId) {
        return reservationService.findAllForPassenger(passengerId);
    }

    @ApiOperation("Get all reservations for passenger")
    @GetMapping(value = "/{passengerId}/reservations", params = {"page", "size"}, produces = "application/json")
    public ResponseEntity<ResponseDTO<ReservationResponseDTO>>
    getAllForCustomerPaginated(@ApiParam(value = "Passenger ID whose reservations should be found", required = true)
                               @PathVariable("passengerId") Long passengerId,
                               @ApiParam("Number of the page")
                               @RequestParam("page") int page,
                               @ApiParam("Maximum number of content blocks on the page")
                               @RequestParam("size") int size) {
        return reservationService.findAllForPassengerPaginated(passengerId, page, size);
    }

    @ApiOperation("Get one reservation by ID for the passenger")
    @GetMapping(value = "/{passengerId}/reservations/{reservationId}", produces = "application/json")
    public ResponseEntity<ResponseDTO<ReservationResponseDTO>>
    getOneForCustomer(@ApiParam(value = "Passenger ID who made a reservation", required = true)
                      @PathVariable("passengerId") Long passengerId,
                      @ApiParam(value = "Reservation ID which which was made", required = true)
                      @PathVariable("reservationId") Long reservationId) {
        return reservationService.findOneForPassenger(passengerId, reservationId);
    }

    @ApiOperation("Create reservation for passenger")
    @PostMapping(value = "/{passengerId}/reservation", produces = "application/json", consumes = "application/json")
    public ResponseEntity<ResponseDTO<ReservationResponseDTO>>
    makeReservation(@PathVariable Long passengerId,
                    @ApiParam(value = "Request to delete reservation", required = true)
                    @RequestBody ReservationRequestDTO request) {
        return reservationService.saveReservation(passengerId, request);
    }

    @ApiOperation(value = "Update reservation by passenger ID")
    @PatchMapping(value = "/{passengerId}/reservations/{reservationId}", produces = "application/json", consumes = "application/json")
    public ResponseEntity<ResponseDTO<ReservationResponseDTO>>
    updateReservation(@ApiParam(value = "Passenger ID who made reservation", required = true)
                      @PathVariable("passengerId") Long passengerId,
                      @ApiParam(value = "Reservation ID which should be updated", required = true)
                      @PathVariable("reservationId") Long reservationId,
                      @RequestBody Map<String, Object> params) {
        return reservationService.updateReservation(passengerId, reservationId, params);
    }

    @ApiOperation("Delete reservation")
    @DeleteMapping("/{passengerId}/reservations/{reservationId}")
    public ResponseEntity<ResponseDTO<ReservationResponseDTO>>
    deleteReservation(@ApiParam(value = "Passenger ID who made reservation", required = true)
                      @PathVariable("passengerId") Long passengerId,
                      @ApiParam(value = "Reservation ID which should be deleted", required = true)
                      @PathVariable("reservationId") Long reservationId) {
        return reservationService.deleteReservation(passengerId, reservationId);
    }
}
