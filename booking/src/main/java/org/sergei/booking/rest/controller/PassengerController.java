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

package org.sergei.booking.rest.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.sergei.booking.rest.dto.PassengerDTO;
import org.sergei.booking.rest.dto.response.ResponseDTO;
import org.sergei.booking.service.PassengerService;
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
    @GetMapping(value = "/getAllPassengers")
    public ResponseEntity<ResponseDTO<PassengerDTO>> getAllPassengers(@RequestParam("page") int page,
                                                                      @RequestParam("size") int size) {
        return passengerService.findAllPassengers(page, size);
    }

    @ApiOperation("Get passenger by ID")
    @GetMapping(value = "/getPassengerById/{passengerId}")
    public ResponseEntity<ResponseDTO<PassengerDTO>> getPassengerById(@PathVariable("passengerId") Long passengerId) {
        return passengerService.findPassengerById(passengerId);
    }

    @PostMapping(value = "/updatePassenger")
    public ResponseEntity<ResponseDTO<PassengerDTO>> updatePassenger(@RequestBody PassengerDTO request) {
        return passengerService.update(request);
    }
}
