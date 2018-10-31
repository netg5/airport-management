/*
 * Copyright (c) Sergei Visotsky, 2018
 */

package org.sergei.flightreservation.api;

import org.sergei.flightreservation.dto.CustomerDTO;
import org.sergei.flightreservation.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Sergei Visotsky, 2018
 */
@RestController
@RequestMapping(value = "/api/v1/customers", produces = {"application/json", "application/xml"})
public class CustomerRESTController {

    @Autowired
    private CustomerService customerService;

    @GetMapping
    public ResponseEntity<List<CustomerDTO>> getAllCustomers() {
        return new ResponseEntity<>(customerService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable("customerId") Long customerId) {
        return new ResponseEntity<>(customerService.findOne(customerId), HttpStatus.OK);
    }

    @PostMapping(consumes = {"application/json", "application/xml"})
    public ResponseEntity<CustomerDTO> saveCustomer(@RequestBody CustomerDTO customerDTO) {
        return new ResponseEntity<>(customerService.save(customerDTO), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{customerId}", consumes = {"application/json", "application/xml"})
    public ResponseEntity<CustomerDTO> updateCustomer(@PathVariable("customerId") Long customerId,
                                                      @RequestBody CustomerDTO customerDTO) {
        return new ResponseEntity<>(customerService.update(customerId, customerDTO), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{customerId}")
    public ResponseEntity<CustomerDTO> deleteCustomer(@PathVariable("customerId") Long customerId) {
        return new ResponseEntity<>(customerService.delete(customerId), HttpStatus.OK);
    }
}
