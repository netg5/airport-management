package org.sergei.flightservice.controller;

import io.swagger.annotations.*;
import org.sergei.flightservice.controller.utils.LinkSetters;
import org.sergei.flightservice.dto.CustomerDTO;
import org.sergei.flightservice.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.Map;

import static org.sergei.flightservice.controller.utils.LinkSetters.setLinksForCustomer;

/**
 * @author Sergei Visotsky, 2018
 */
@Api(
        value = "/flight-api/customers",
        produces = "application/json",
        consumes = "application/json"
)
@RestController
@RequestMapping(value = "/customers", produces = "application/json")
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @ApiOperation("Get all customers")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 404, message = "Invalid customer ID")
            }
    )
    @GetMapping
    public ResponseEntity<Resources<CustomerDTO>> getAllCustomers() {

        List<CustomerDTO> customerList = customerService.findAll();
        customerList.forEach(customer -> {
            Link link = ControllerLinkBuilder.linkTo(
                    ControllerLinkBuilder.methodOn(CustomerController.class)
                            .getCustomerById(customer.getCustomerId())).withSelfRel();
            Link reservationsLink = ControllerLinkBuilder.linkTo(
                    ControllerLinkBuilder.methodOn(ReservationController.class)
                            .getAllForCustomer(customer.getCustomerId())).withRel("reservations");
            Link ticketsLink = new Link(
                    "http://127.0.0.1:8080/ticket-api/tickets?customerId=" + customer.getCustomerId()).withRel("tickets");
            customer.add(link);
            customer.add(reservationsLink);
            customer.add(ticketsLink);
        });
        Resources<CustomerDTO> resources = new Resources<>(customerList);
        String uriString = ServletUriComponentsBuilder.fromCurrentRequest().build().toUriString();
        resources.add(new Link(uriString, "self"));
        return new ResponseEntity<>(resources, HttpStatus.OK);
    }

    @ApiOperation("Get all customers paginated")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 404, message = "Invalid customer ID")
            }
    )
    @GetMapping(params = {"page", "size"})
    public ResponseEntity<Resources<CustomerDTO>> getAllCustomersPaginated(@ApiParam(value = "Number of page", required = true)
                                                                           @RequestParam(value = "page") int page,
                                                                           @ApiParam(value = "Number of elements per page", required = true)
                                                                           @RequestParam(value = "size") int size) {

        Page<CustomerDTO> customerList = customerService.findAllPaginated(page, size);

        customerList.forEach(customer -> {
            Link link = ControllerLinkBuilder.linkTo(
                    ControllerLinkBuilder.methodOn(CustomerController.class)
                            .getCustomerById(customer.getCustomerId())).withSelfRel();
            Link reservationsLink = ControllerLinkBuilder.linkTo(
                    ControllerLinkBuilder.methodOn(ReservationController.class)
                            .getAllForCustomer(customer.getCustomerId())).withRel("reservations");
            Link ticketsLink = new Link(
                    "http://127.0.0.1:8080/ticket-api/tickets?customerId=" + customer.getCustomerId()).withRel("tickets");
            customer.add(link);
            customer.add(reservationsLink);
            customer.add(ticketsLink);
        });
        Resources<CustomerDTO> resources = new Resources<>(customerList);
        String uriString = ServletUriComponentsBuilder.fromCurrentRequest().build().toUriString();
        resources.add(new Link(uriString, "self"));
        return new ResponseEntity<>(resources, HttpStatus.OK);
    }

    @ApiOperation("Get customer by ID")
    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerDTO> getCustomerById(@ApiParam(value = "Customer ID which should be found", required = true)
                                                       @PathVariable("customerId") Long customerId) {
        CustomerDTO customer = customerService.findOne(customerId);
        return new ResponseEntity<>(setLinksForCustomer(customer), HttpStatus.OK);
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
        CustomerDTO customer = customerService.update(customerId, customerDTO);
        return new ResponseEntity<>(setLinksForCustomer(customer), HttpStatus.OK);
    }

    @ApiOperation("Update one field for a customer")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 404, message = "Invalid customer ID")
            }
    )
    @PatchMapping(value = "/{customerId}/patch", consumes = "application/json")
    public ResponseEntity<CustomerDTO> patchCustomer(@ApiParam(value = "Customer ID which should be updated", required = true)
                                                     @PathVariable("customerId") Long customerId,
                                                     @RequestBody Map<String, Object> params) {
        CustomerDTO customerDTO = customerService.patch(customerId, params);
        return new ResponseEntity<>(setLinksForCustomer(customerDTO), HttpStatus.OK);
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
