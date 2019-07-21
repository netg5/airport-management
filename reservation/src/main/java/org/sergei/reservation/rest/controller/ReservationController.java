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
        value = "/reservation/customers/{customerId}/reservations/",
        produces = "application/json",
        consumes = "application/json"
)
@RestController
@RequestMapping(value = "/customers")
public class ReservationController {

    private final ReservationService reservationService;

    @Autowired
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @ApiOperation("Get all reservations for customer")
    @GetMapping(value = "/{customerId}/reservations", produces = "application/json", consumes = "application/json")
    public ResponseEntity<ResponseDTO<ReservationResponseDTO>> getAllForCustomer(@ApiParam(value = "Customer ID whose reservations should be found", required = true)
                                                                                 @PathVariable("customerId") Long customerId) {
        return reservationService.findAllForCustomer(customerId);
    }

    @ApiOperation("Get all reservations for customer")
    @GetMapping(value = "/{customerId}/reservations", params = {"page", "size"}, produces = "application/json", consumes = "application/json")
    public ResponseEntity<ResponseDTO<ReservationResponseDTO>> getAllForCustomerPaginated(@ApiParam(value = "Customer ID whose reservations should be found", required = true)
                                                                                          @PathVariable("customerId") Long customerId,
                                                                                          @ApiParam("Number of the page")
                                                                                          @RequestParam("page") int page,
                                                                                          @ApiParam("Maximum number of content blocks on the page")
                                                                                          @RequestParam("size") int size) {
        return reservationService.findAllForCustomerPaginated(customerId, page, size);
    }

    @ApiOperation("Get one reservation by ID for the customer")
    @GetMapping(value = "/{customerId}/reservations/{reservationId}", produces = "application/json", consumes = "application/json")
    public ResponseEntity<ResponseDTO<ReservationResponseDTO>> getOneForCustomer(@ApiParam(value = "Customer ID who made a reservation", required = true)
                                                                                 @PathVariable("customerId") Long customerId,
                                                                                 @ApiParam(value = "Reservation ID which which was made", required = true)
                                                                                 @PathVariable("reservationId") Long reservationId) {
        return reservationService.findOneForCustomer(customerId, reservationId);
    }

    @ApiOperation("Create reservation for customer")
    @PostMapping(value = "/{customerId}/reservations", produces = "application/json", consumes = "application/json")
    public ResponseEntity<ResponseDTO<ReservationResponseDTO>> createReservation(@ApiParam(value = "Request to delete reservation", required = true)
                                                                                 @RequestBody ReservationRequestDTO request) {
        return reservationService.saveReservation(request);
    }

    @ApiOperation(value = "Update reservation by customer ID")
    @PatchMapping(value = "/{customerId}/reservations/{reservationId}", produces = "application/json", consumes = "application/json")
    public ResponseEntity<ResponseDTO<ReservationResponseDTO>> updateReservation(@ApiParam(value = "Customer ID who made reservation", required = true)
                                                                                 @PathVariable("customerId") Long customerId,
                                                                                 @ApiParam(value = "Reservation ID which should be updated", required = true)
                                                                                 @PathVariable("reservationId") Long reservationId,
                                                                                 @RequestBody Map<String, Object> params) {
        return reservationService.updateReservation(customerId, reservationId, params);
    }

    @ApiOperation("Delete reservation")
    @DeleteMapping(value = "/{customerId}/reservations/{reservationId}", produces = "application/json", consumes = "application/json")
    public ResponseEntity<ResponseDTO<ReservationResponseDTO>> deleteReservation(@ApiParam(value = "Customer ID who made reservation", required = true)
                                                                                 @PathVariable("customerId") Long customerId,
                                                                                 @ApiParam(value = "Reservation ID which should be deleted", required = true)
                                                                                 @PathVariable("reservationId") Long reservationId) {
        return reservationService.deleteReservation(customerId, reservationId);
    }
}
