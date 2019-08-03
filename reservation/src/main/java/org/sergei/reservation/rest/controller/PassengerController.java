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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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
    public ResponseEntity<ResponseDTO<PassengerResponseDTO>> getAllCustomers(@ApiParam(value = "Number of the page")
                                                                             @RequestParam("page") int page,
                                                                             @ApiParam(value = "Maximum number of content blocks on the page")
                                                                             @RequestParam("size") int size) {
        return passengerService.findAll(page, size);
    }

    @ApiOperation("Get IDs of all existing customers")
    @GetMapping(value = "/getIdsOfAllCustomers")
    public ResponseEntity<ResponseDTO<String>> getIdsOfAllCustomers() {
        return passengerService.findIdsOfAllCustomers();
    }

    @ApiOperation("Get passenger by ID")
    @GetMapping(value = "/getCustomerById/{passengerId}", produces = "application/json")
    public ResponseEntity<ResponseDTO<PassengerResponseDTO>>
    getCustomerById(@ApiParam(value = "Passenger ID which should be found", required = true)
                    @PathVariable("passengerId") Long passengerId) {
        return passengerService.findOne(passengerId);
    }

    @ApiOperation("Save passenger")
    @PostMapping(value = "/saveCustomer", produces = "application/json", consumes = "application/json")
    public ResponseEntity<ResponseDTO<PassengerResponseDTO>>
    saveCustomer(@ApiParam(value = "Saved passenger", required = true)
                 @RequestBody PassengerResponseDTO request) {
        return passengerService.save(request);
    }

    @ApiOperation("Update passenger data")
    @PostMapping(value = "/updateCustomer", produces = "application/json", consumes = "application/json")
    public ResponseEntity<ResponseDTO<PassengerResponseDTO>>
    updateCustomer(@ApiParam(value = "Updated passenger", required = true)
                   @RequestBody PassengerUpdateRequestDTO request) {
        return passengerService.update(request);
    }

    @ApiOperation(value = "Delete passenger data", notes = "Operation allowed for the ROLE_ADMIN only")
    @DeleteMapping("/deleteCustomer/{passengerId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<ResponseDTO<PassengerResponseDTO>>
    deleteCustomer(@ApiParam(value = "Passenger ID which should be deleted", required = true)
                   @PathVariable("passengerId") Long passengerId) {
        return passengerService.delete(passengerId);
    }
}
