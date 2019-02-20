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

package org.sergei.reservationservice.rest;

import io.swagger.annotations.*;
import org.sergei.library.Constants;
import org.sergei.reservationservice.rest.dto.CustomerDTO;
import org.sergei.reservationservice.rest.hateoas.LinkUtil;
import org.sergei.reservationservice.service.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author Sergei Visotsky
 */
@Api(
        value = "/flight-api/customers",
        produces = "application/json",
        consumes = "application/json"
)
@RestController
@RequestMapping(value = "/customers", produces = "application/json")
public class CustomerController {

    private final LinkUtil linkUtil;
    private final ICustomerService<CustomerDTO> customerService;

    @Autowired
    public CustomerController(LinkUtil linkUtil, ICustomerService<CustomerDTO> customerService) {
        this.linkUtil = linkUtil;
        this.customerService = customerService;
    }

    @ApiOperation("Get all customers")
    @GetMapping
    public ResponseEntity<Resources> getAllCustomers() {
        List<CustomerDTO> customerList = customerService.findAll();
        return new ResponseEntity<>(linkUtil.setLinksForAllCustomers(customerList), HttpStatus.OK);
    }

    @ApiOperation("Get IDs of all existing customers")
    @GetMapping("/ids")
    public ResponseEntity<Resources> getIdsOfAllCustomers() {
        List<String> customerIdDTOList = customerService.findIdsOfAllCustomers();
        return new ResponseEntity<>(linkUtil.setLinksForIdsOfCustomers(customerIdDTOList), HttpStatus.OK);
    }

    @ApiOperation("Get all customers paginated")
    @GetMapping(params = {"page", "size"})
    public ResponseEntity<Resources> getAllCustomersPaginated(@ApiParam(value = "Number of the page")
                                                              @RequestParam("page") int page,
                                                              @ApiParam(value = "Maximum number of content blocks on the page")
                                                              @RequestParam("size") int size) {
        Page<CustomerDTO> customerList = customerService.findAllPaginated(page, size);
        return new ResponseEntity<>(linkUtil.setLinksForAllCustomers(customerList), HttpStatus.OK);
    }

    @ApiOperation("Get customer by ID")
    @ApiResponses({
            @ApiResponse(code = 404, message = Constants.CUSTOMER_NOT_FOUND)
    })
    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerDTO> getCustomerById(@ApiParam(value = "Customer ID which should be found", required = true)
                                                       @PathVariable("customerId") Long customerId) {
        CustomerDTO customer = customerService.findOne(customerId);
        return new ResponseEntity<>(linkUtil.setLinksForCustomer(customer), HttpStatus.OK);
    }

    @ApiOperation("Save customer")
    @PostMapping(consumes = "application/json")
    public ResponseEntity<CustomerDTO> saveCustomer(@ApiParam(value = "Saved customer", required = true)
                                                    @RequestBody CustomerDTO customerDTO) {
        return new ResponseEntity<>(customerService.save(customerDTO), HttpStatus.CREATED);
    }

    @ApiOperation("Update customer data")
    @ApiResponses({
            @ApiResponse(code = 404, message = Constants.CUSTOMER_NOT_FOUND)
    })
    @PutMapping(value = "/{customerId}", consumes = "application/json")
    public ResponseEntity<CustomerDTO> updateCustomer(@ApiParam(value = "Customer ID which should be updated", required = true)
                                                      @PathVariable("customerId") Long customerId,
                                                      @ApiParam(value = "Updated customer", required = true)
                                                      @RequestBody CustomerDTO customerDTO) {
        CustomerDTO customer = customerService.update(customerId, customerDTO);
        return new ResponseEntity<>(linkUtil.setLinksForCustomer(customer), HttpStatus.OK);
    }

    @ApiOperation("Update one field for a customer")
    @ApiResponses({
            @ApiResponse(code = 404, message = Constants.CUSTOMER_NOT_FOUND)
    })
    @PatchMapping(value = "/{customerId}/patch", consumes = "application/json")
    public ResponseEntity<CustomerDTO> patchCustomer(@ApiParam(value = "Customer ID which should be updated", required = true)
                                                     @PathVariable("customerId") Long customerId,
                                                     @RequestBody Map<String, Object> params) {
        CustomerDTO customerDTO = customerService.patch(customerId, params);
        return new ResponseEntity<>(linkUtil.setLinksForCustomer(customerDTO), HttpStatus.OK);
    }

    @ApiOperation(value = "Delete customer data", notes = "Operation allowed for the ROLE_ADMIN only")
    @ApiResponses({
            @ApiResponse(code = 404, message = Constants.CUSTOMER_NOT_FOUND)
    })
    @DeleteMapping(value = "/{customerId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<CustomerDTO> deleteCustomer(@ApiParam(value = "Customer ID which should be deleted", required = true)
                                                      @PathVariable("customerId") Long customerId) {
        return new ResponseEntity<>(customerService.delete(customerId), HttpStatus.NO_CONTENT);
    }
}
