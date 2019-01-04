package org.sergei.reservationservice.controller;

import io.swagger.annotations.*;
import org.sergei.reservationservice.dto.CustomerDTO;
import org.sergei.reservationservice.dto.CustomerIdsDTO;
import org.sergei.reservationservice.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static org.sergei.reservationservice.controller.util.LinkUtil.*;

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

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @ApiOperation("Get all customers")
    @GetMapping
    public ResponseEntity<Resources> getAllCustomers() {
        List<CustomerDTO> customerList = customerService.findAll();
        return new ResponseEntity<>(setLinksForAllCustomers(customerList), HttpStatus.OK);
    }

    @ApiOperation("Get IDs of all existing customers")
    @GetMapping("/ids")
    public ResponseEntity<Resources> getIdsOfAllCustomers() {
        List<CustomerIdsDTO> customerIdDTOList = customerService.findIdsOfAllCustomers();
        return new ResponseEntity<>(setLinksForIdsOfCustomers(customerIdDTOList), HttpStatus.OK);
    }

    @ApiOperation("Get IDs of all existing customers paginated")
    @GetMapping(value = "/ids", params = {"page", "size"})
    public ResponseEntity<Resources> getIdsOfAllCustomersPaginated(@ApiParam(value = "Number of the page")
                                                                   @RequestParam("page") int page,
                                                                   @ApiParam(value = "Maximum number of content blocks on the page")
                                                                   @RequestParam("size") int size) {
        Page<CustomerIdsDTO> customerIdPage = customerService.findIdsOfAllCustomersPaginated(page, size);
        return new ResponseEntity<>(setLinksForIdsOfCustomers(customerIdPage), HttpStatus.OK);
    }

    @ApiOperation("Get all customers paginated")
    @GetMapping(params = {"page", "size"})
    public ResponseEntity<Resources> getAllCustomersPaginated(@ApiParam(value = "Number of the page")
                                                              @RequestParam("page") int page,
                                                              @ApiParam(value = "Maximum number of content blocks on the page")
                                                              @RequestParam("size") int size) {
        Page<CustomerDTO> customerList = customerService.findAllPaginated(page, size);
        return new ResponseEntity<>(setLinksForAllCustomers(customerList), HttpStatus.OK);
    }

    @ApiOperation("Get customer by ID")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 404, message = "Customer with this ID not found")
            }
    )
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
                    @ApiResponse(code = 404, message = "Customer with this ID not found")
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
                    @ApiResponse(code = 404, message = "Customer with this ID not found")
            }
    )
    @PatchMapping(value = "/{customerId}/patch", consumes = "application/json")
    public ResponseEntity<CustomerDTO> patchCustomer(@ApiParam(value = "Customer ID which should be updated", required = true)
                                                     @PathVariable("customerId") Long customerId,
                                                     @RequestBody Map<String, Object> params) {
        CustomerDTO customerDTO = customerService.patch(customerId, params);
        return new ResponseEntity<>(setLinksForCustomer(customerDTO), HttpStatus.OK);
    }

    @ApiOperation(value = "Delete customer data", notes = "Operation allowed for the ROLE_ADMIN only")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 404, message = "Customer with this ID not found")
            }
    )
    @DeleteMapping(value = "/{customerId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<CustomerDTO> deleteCustomer(@ApiParam(value = "Customer ID which should be deleted", required = true)
                                                      @PathVariable("customerId") Long customerId) {
        return new ResponseEntity<>(customerService.delete(customerId), HttpStatus.NO_CONTENT);
    }
}
