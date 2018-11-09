/*
 * Copyright (c) Sergei Visotsky, 2018
 */

package org.sergei.flightreservation.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.sergei.flightreservation.dto.CustomerDTO;
import org.sergei.flightreservation.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Sergei Visotsky, 2018
 */
@Api(value = "/api/v1/customers", description = "Customer API methods")
@RestController
@RequestMapping(value = "/api/v1/customers", produces = "application/json")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @ApiOperation("Get all customers")
    @GetMapping
    public ResponseEntity<List<CustomerDTO>> getAllCustomers() {
        return new ResponseEntity<>(customerService.findAll(), HttpStatus.OK);
    }

    @ApiOperation("Get customer by ID")
    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable("customerId") Long customerId) {
        return new ResponseEntity<>(customerService.findOne(customerId), HttpStatus.OK);
    }

    @ApiOperation("Save customer")
    @PostMapping(consumes = "application/json")
    public ResponseEntity<CustomerDTO> saveCustomer(@RequestBody CustomerDTO customerDTO) {
        return new ResponseEntity<>(customerService.save(customerDTO), HttpStatus.CREATED);
    }

    @ApiOperation("Update customer data")
    @PutMapping(value = "/{customerId}", consumes = "application/json")
    public ResponseEntity<CustomerDTO> updateCustomer(@PathVariable("customerId") Long customerId,
                                                      @RequestBody CustomerDTO customerDTO) {
        return new ResponseEntity<>(customerService.update(customerId, customerDTO), HttpStatus.OK);
    }

    @ApiOperation("Delete customer data")
    @DeleteMapping(value = "/{customerId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<CustomerDTO> deleteCustomer(@PathVariable("customerId") Long customerId) {
        return new ResponseEntity<>(customerService.delete(customerId), HttpStatus.NO_CONTENT);
    }
}
