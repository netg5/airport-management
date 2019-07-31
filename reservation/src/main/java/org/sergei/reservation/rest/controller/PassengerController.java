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
@RequestMapping("/passengers")
@Api(tags = {"passengerCrudOperations"})
public class PassengerController {

    private final PassengerService passengerService;

    @Autowired
    public PassengerController(PassengerService passengerService) {
        this.passengerService = passengerService;
    }

    @ApiOperation("Get all customers")
    @GetMapping(produces = "application/json")
    public ResponseEntity<ResponseDTO<PassengerResponseDTO>> getAllCustomers() {
        return passengerService.findAll();
    }

    @ApiOperation("Get IDs of all existing customers")
    @GetMapping(value = "/ids", produces = "application/json")
    public ResponseEntity<ResponseDTO<String>> getIdsOfAllCustomers() {
        return passengerService.findIdsOfAllCustomers();
    }

    @ApiOperation("Get all customers paginated")
    @GetMapping(params = {"page", "size"}, produces = "application/json")
    public ResponseEntity<ResponseDTO<PassengerResponseDTO>>
    getAllCustomersPaginated(@ApiParam(value = "Number of the page")
                             @RequestParam("page") int page,
                             @ApiParam(value = "Maximum number of content blocks on the page")
                             @RequestParam("size") int size) {
        return passengerService.findAllPaginated(page, size);
    }

    @ApiOperation("Get passenger by ID")
    @GetMapping(value = "/{passengerId}", produces = "application/json")
    public ResponseEntity<ResponseDTO<PassengerResponseDTO>>
    getCustomerById(@ApiParam(value = "Passenger ID which should be found", required = true)
                    @PathVariable("passengerId") Long passengerId) {
        return passengerService.findOne(passengerId);
    }

    @ApiOperation("Save passenger")
    @PostMapping(value = "/save", produces = "application/json", consumes = "application/json")
    public ResponseEntity<ResponseDTO<PassengerResponseDTO>>
    saveCustomer(@ApiParam(value = "Saved passenger", required = true)
                 @RequestBody PassengerResponseDTO request) {
        return passengerService.save(request);
    }

    @ApiOperation("Update passenger data")
    @PostMapping(value = "/update", produces = "application/json", consumes = "application/json")
    public ResponseEntity<ResponseDTO<PassengerResponseDTO>>
    updateCustomer(@ApiParam(value = "Updated passenger", required = true)
                   @RequestBody PassengerUpdateRequestDTO request) {
        return passengerService.update(request);
    }

    @ApiOperation("Update one field for a passenger")
    @PatchMapping(value = "/{passengerId}/patch", produces = "application/json", consumes = "application/json")
    public ResponseEntity<ResponseDTO<PassengerResponseDTO>>
    patchCustomer(@ApiParam(value = "Passenger ID which should be updated", required = true)
                  @PathVariable("passengerId") Long passengerId,
                  @RequestBody Map<String, Object> params) {
        return passengerService.patch(passengerId, params);
    }

    @ApiOperation(value = "Delete passenger data", notes = "Operation allowed for the ROLE_ADMIN only")
    @DeleteMapping("/{passengerId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<ResponseDTO<PassengerResponseDTO>>
    deleteCustomer(@ApiParam(value = "Passenger ID which should be deleted", required = true)
                   @PathVariable("passengerId") Long passengerId) {
        return passengerService.delete(passengerId);
    }
}
