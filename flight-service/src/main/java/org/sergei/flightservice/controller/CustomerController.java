package org.sergei.flightservice.controller;

import io.swagger.annotations.*;
import org.sergei.flightservice.dto.CustomerDTO;
import org.sergei.flightservice.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

/**
 * @author Sergei Visotsky, 2018
 */
@Api(
        value = "/flight-api/v1/customers",
        produces = "application/json",
        consumes = "application/json"
)
@RestController
@RequestMapping(value = "/v1/customers", produces = "application/json")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @ApiOperation("Get all customers")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 404, message = "Invalid customer ID")
            }
    )
    @GetMapping
    public ResponseEntity<Resources<CustomerDTO>> getAllCustomers() {
        List<CustomerDTO> customerDTOList = customerService.findAll();
        customerDTOList.forEach(customerDTO -> {
            Link link = ControllerLinkBuilder.linkTo(
                    ControllerLinkBuilder.methodOn(CustomerController.class)
                            .getCustomerById(customerDTO.getCustomerId())).withSelfRel();
            Link reservationsLink = ControllerLinkBuilder.linkTo(
                    ControllerLinkBuilder.methodOn(FlightReservationController.class)
                            .getAllForCustomer(customerDTO.getCustomerId())).withRel("reservations");
            customerDTO.add(link);
            customerDTO.add(reservationsLink);
        });
        Resources<CustomerDTO> resources = new Resources<>(customerDTOList);
        String uriString = ServletUriComponentsBuilder.fromCurrentRequest().build().toUriString();
        resources.add(new Link(uriString, "self"));
        return new ResponseEntity<>(resources, HttpStatus.OK);
    }

    @ApiOperation("Get customer by ID")
    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerDTO> getCustomerById(@ApiParam(value = "Customer ID which should be found", required = true)
                                                       @PathVariable("customerId") Long customerId) {
        CustomerDTO customerDTO = customerService.findOne(customerId);
        Link link = ControllerLinkBuilder.linkTo(CustomerController.class).withSelfRel();
        Link reservationsLink = ControllerLinkBuilder.linkTo(
                ControllerLinkBuilder.methodOn(FlightReservationController.class)
                        .getAllForCustomer(customerId)).withRel("reservations");
        customerDTO.add(link);
        customerDTO.add(reservationsLink);
        return new ResponseEntity<>(customerDTO, HttpStatus.OK);
    }

    @ApiOperation("Save customer")
    @PostMapping(consumes = "application/json")
    public ResponseEntity<CustomerDTO> saveCustomer(@ApiParam(value = "Saved customer", required = true)
                                                    @RequestBody CustomerDTO customerDTO) {
        return new ResponseEntity<>(customerService.save(customerDTO), HttpStatus.CREATED);
    }

    @ApiOperation("Update customer data")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 404, message = "Invalid customer ID")
            }
    )
    @PutMapping(value = "/{customerId}", consumes = "application/json")
    public ResponseEntity<CustomerDTO> updateCustomer(@ApiParam(value = "Customer ID which should be updated", required = true)
                                                      @PathVariable("customerId") Long customerId,
                                                      @ApiParam(value = "Updated customer", required = true)
                                                      @RequestBody CustomerDTO customerDTO) {
        return new ResponseEntity<>(customerService.update(customerId, customerDTO), HttpStatus.OK);
    }

    @ApiOperation(value = "Delete customer data", notes = "Operation allowed for ADMIN only")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 404, message = "Invalid customer ID")
            }
    )
    @DeleteMapping(value = "/{customerId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<CustomerDTO> deleteCustomer(@ApiParam(value = "Customer ID which should be deleted", required = true)
                                                      @PathVariable("customerId") Long customerId) {
        return new ResponseEntity<>(customerService.delete(customerId), HttpStatus.NO_CONTENT);
    }
}
