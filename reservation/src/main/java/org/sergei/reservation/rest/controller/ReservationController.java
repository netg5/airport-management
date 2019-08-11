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
import org.sergei.reservation.rest.dto.ReservationDTO;
import org.sergei.reservation.rest.dto.request.ReservationDTORequest;
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

    @GetMapping(value = "/getAllReservationForPassenger/{passengerId}")
    public ResponseEntity<ResponseDTO<ReservationDTO>> getAllReservationForPassenger(@PathVariable("passengerId") Long passengerId) {
        return reservationService.findAllForPassenger(passengerId);
    }

    @GetMapping(value = "/getOneReservationForPassenger/{passengerId}", produces = "application/json")
    public ResponseEntity<ResponseDTO<ReservationDTO>> getOneReservationForPassenger(@PathVariable("passengerId") Long passengerId,
                                                                                     @RequestParam("reservationId") Long reservationId) {
        return reservationService.findOneForPassenger(passengerId, reservationId);
    }

    @PostMapping(value = "/makeReservation")
    public ResponseEntity<ResponseDTO<ReservationDTO>> makeReservation(@RequestBody ReservationDTORequest request) {
        return reservationService.saveReservation(request);
    }

    @ApiOperation("Delete reservation")
    @DeleteMapping("/discardReservation/{passengerId}")
    public ResponseEntity<ResponseDTO<ReservationDTO>> discardReservation(@PathVariable("passengerId") Long passengerId,
                                                                          @RequestParam("reservationId") Long reservationId) {
        return reservationService.discardReservation(passengerId, reservationId);
    }
}
