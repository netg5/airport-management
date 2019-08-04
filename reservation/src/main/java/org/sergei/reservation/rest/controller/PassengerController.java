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
import org.sergei.reservation.rest.dto.PassengerResponseDTO;
import org.sergei.reservation.rest.dto.PassengerUpdateRequestDTO;
import org.sergei.reservation.rest.dto.response.ResponseDTO;
import org.sergei.reservation.service.PassengerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author Sergei Visotsky
 */
@RestController
@Api(tags = {"passengerCrudOperations"})
public class PassengerController {

    private final PassengerService passengerService;

    @Autowired
    public PassengerController(PassengerService passengerService) {
        this.passengerService = passengerService;
    }

    @ApiOperation("Get all customers")
    @GetMapping(value = "/getAllCustomers")
    public ResponseEntity<ResponseDTO<PassengerResponseDTO>> getAllCustomers(@RequestParam("page") int page,
                                                                             @RequestParam("size") int size) {
        return passengerService.findAllPassengers(page, size);
    }

    @ApiOperation("Get passenger by ID")
    @GetMapping(value = "/getPassengerById/{passengerId}")
    public ResponseEntity<ResponseDTO<PassengerResponseDTO>> getPassengerById(@PathVariable("passengerId") Long passengerId) {
        return passengerService.findOne(passengerId);
    }

    /**
     * Should be done during the reservation
     *
     * @param request
     * @return
     */
    @Deprecated(forRemoval = true)
    @ApiOperation("Save passenger")
    @PostMapping(value = "/saveCustomer")
    public ResponseEntity<ResponseDTO<PassengerResponseDTO>> savePassenger(@ApiParam(value = "Saved passenger", required = true)
                                                                           @RequestBody PassengerResponseDTO request) {
        return passengerService.save(request);
    }

    @PostMapping(value = "/updatePassenger")
    public ResponseEntity<ResponseDTO<PassengerResponseDTO>> updatePassenger(@RequestBody PassengerUpdateRequestDTO request) {
        return passengerService.update(request);
    }

    /**
     * Should be done during the reservation deletion
     *
     * @param passengerId
     * @return
     */
    @Deprecated(forRemoval = true)
    @DeleteMapping(value = "/deletePassenger/{passengerId}")
    public ResponseEntity<ResponseDTO<PassengerResponseDTO>> deletePassenger(@PathVariable("passengerId") Long passengerId) {
        return passengerService.delete(passengerId);
    }
}
