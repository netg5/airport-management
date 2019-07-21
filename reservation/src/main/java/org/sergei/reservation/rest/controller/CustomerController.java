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

import io.swagger.annotations.*;
import org.sergei.reservation.rest.dto.CustomerResponseDTO;
import org.sergei.reservation.rest.dto.CustomerUpdateRequestDTO;
import org.sergei.reservation.rest.dto.response.ResponseDTO;
import org.sergei.reservation.service.CustomerService;
import org.sergei.reservation.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author Sergei Visotsky
 */
@Api(
        value = "/reservation/customers",
        produces = "application/json",
        consumes = "application/json"
)
@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @ApiOperation("Get all customers")
    @GetMapping
    public ResponseEntity<ResponseDTO<CustomerResponseDTO>> getAllCustomers() {
        return customerService.findAll();
    }

    @ApiOperation("Get IDs of all existing customers")
    @GetMapping(value = "/ids", produces = "application/json", consumes = "application/json")
    public ResponseEntity<ResponseDTO<String>> getIdsOfAllCustomers() {
        return customerService.findIdsOfAllCustomers();
    }

    @ApiOperation("Get all customers paginated")
    @GetMapping(params = {"page", "size"}, produces = "application/json", consumes = "application/json")
    public ResponseEntity<ResponseDTO<CustomerResponseDTO>> getAllCustomersPaginated(@ApiParam(value = "Number of the page")
                                                                             @RequestParam("page") int page,
                                                                                     @ApiParam(value = "Maximum number of content blocks on the page")
                                                                             @RequestParam("size") int size) {
        return customerService.findAllPaginated(page, size);
    }

    @ApiOperation("Get customer by ID")
    @GetMapping(value = "/{customerId}", produces = "application/json", consumes = "application/json")
    public ResponseEntity<ResponseDTO<CustomerResponseDTO>> getCustomerById(@ApiParam(value = "Customer ID which should be found", required = true)
                                                                    @PathVariable("customerId") Long customerId) {
        return customerService.findOne(customerId);
    }

    @ApiOperation("Save customer")
    @PostMapping(produces = "application/json", consumes = "application/json")
    public ResponseEntity<ResponseDTO<CustomerResponseDTO>> saveCustomer(@ApiParam(value = "Saved customer", required = true)
                                                                 @RequestBody CustomerResponseDTO request) {
        return customerService.save(request);
    }

    @ApiOperation("Update customer data")
    @PostMapping(produces = "application/json", consumes = "application/json")
    public ResponseEntity<ResponseDTO<CustomerResponseDTO>> updateCustomer(@ApiParam(value = "Updated customer", required = true)
                                                                   @RequestBody CustomerUpdateRequestDTO request) {
        return customerService.update(request);
    }

    @ApiOperation("Update one field for a customer")
    @ApiResponses({
            @ApiResponse(code = 404, message = Constants.CUSTOMER_NOT_FOUND)
    })
    @PatchMapping(value = "/{customerId}/patch", produces = "application/json", consumes = "application/json")
    public ResponseEntity<ResponseDTO<CustomerResponseDTO>> patchCustomer(@ApiParam(value = "Customer ID which should be updated", required = true)
                                                                  @PathVariable("customerId") Long customerId,
                                                                          @RequestBody Map<String, Object> params) {
        return customerService.patch(customerId, params);
    }

    @ApiOperation(value = "Delete customer data", notes = "Operation allowed for the ROLE_ADMIN only")
    @ApiResponses({
            @ApiResponse(code = 404, message = Constants.CUSTOMER_NOT_FOUND)
    })
    @DeleteMapping(value = "/{customerId}", produces = "application/json", consumes = "application/json")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<ResponseDTO<CustomerResponseDTO>> deleteCustomer(@ApiParam(value = "Customer ID which should be deleted", required = true)
                                                                   @PathVariable("customerId") Long customerId) {
        return customerService.delete(customerId);
    }
}
