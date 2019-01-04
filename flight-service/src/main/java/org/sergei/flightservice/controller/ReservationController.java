package org.sergei.flightservice.controller;

import io.swagger.annotations.*;
import org.sergei.flightservice.dto.ReservationDTO;
import org.sergei.flightservice.dto.ReservationExtendedDTO;
import org.sergei.flightservice.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.Map;

import static org.sergei.flightservice.controller.util.LinkUtil.setLinksForAllReservations;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * @author Sergei Visotsky
 */
@Api(
        value = "/flight-api/customers/{customerId}/reservations/",
        produces = "application/json",
        consumes = "application/json"
)
@RestController
@RequestMapping(value = "/customers", produces = "application/json")
public class ReservationController {

    private final ReservationService reservationService;

    @Autowired
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @ApiOperation("Get all reservations for customer")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 404, message = "Customer with this ID not found or no reservations made"),
            }
    )
    @GetMapping("/{customerId}/reservations")
    public ResponseEntity<Resources> getAllForCustomer(@ApiParam(value = "Customer ID whose reservations should be found", required = true)
                                                       @PathVariable("customerId") Long customerId) {
        List<ReservationExtendedDTO> reservations =
                reservationService.findAllForCustomer(customerId);
        return new ResponseEntity<>(setLinksForAllReservations(reservations), HttpStatus.OK);
    }

    @ApiOperation("Get all reservations for customer")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 404, message = "Customer with this ID not found or no reservations made"),
            }
    )
    @GetMapping(value = "/{customerId}/reservations", params = {"page", "size"})
    public ResponseEntity<Resources> getAllForCustomerPaginated(@ApiParam(value = "Customer ID whose reservations should be found", required = true)
                                                                @PathVariable("customerId") Long customerId,
                                                                @ApiParam("Number of the page")
                                                                @RequestParam("page") int page,
                                                                @ApiParam("Maximum number of content blocks on the page")
                                                                @RequestParam("size") int size) {
        Page<ReservationExtendedDTO> reservations =
                reservationService.findAllForCustomerPaginated(customerId, page, size);
        return new ResponseEntity<>(setLinksForAllReservations(reservations), HttpStatus.OK);
    }

    @ApiOperation("Get one reservation by ID for the customer")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 404, message = "Customer with this ID not found or no reservations made"),
            }
    )
    @GetMapping("/{customerId}/reservations/{reservationId}")
    public ResponseEntity<ReservationExtendedDTO> getOneForCustomer(@ApiParam(value = "Customer ID who made a reservation", required = true)
                                                                    @PathVariable("customerId") Long customerId,
                                                                    @ApiParam(value = "Reservation ID which which was made", required = true)
                                                                    @PathVariable("reservationId") Long reservationId) {
        ReservationExtendedDTO reservationExtendedDTO =
                reservationService.findOneForCustomer(customerId, reservationId);
        Link link = linkTo(
                methodOn(CustomerController.class)
                        .getCustomerById(customerId)).withRel("customer");
        reservationExtendedDTO.add(link);
        String uriString = ServletUriComponentsBuilder.fromCurrentRequest().build().toUriString();
        reservationExtendedDTO.add(new Link(uriString, "self"));
        return new ResponseEntity<>(reservationExtendedDTO, HttpStatus.OK);
    }

    @ApiOperation("Create reservation for customer")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 404, message = "Customer or route with this ID not found")
            }
    )
    @PostMapping(value = "/{customerId}/reservations", consumes = "application/json")
    public ResponseEntity<ReservationDTO> createReservation(@ApiParam(value = "Customer ID for whom reservation should be created", required = true)
                                                            @PathVariable("customerId") Long customerId,
                                                            @ApiParam(value = "Created reservation", required = true)
                                                            @RequestBody ReservationDTO reservationDTO) {
        return new ResponseEntity<>(
                reservationService.saveReservation(customerId, reservationDTO),
                HttpStatus.CREATED);
    }

    @ApiOperation(value = "Update reservation by customer ID")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 404, message = "Customer or route with this ID not found")
            }
    )
    @PatchMapping(value = "/{customerId}/reservations/{reservationId}", consumes = "application/json")
    public ResponseEntity<ReservationDTO> updateReservation(@ApiParam(value = "Customer ID who made reservation", required = true)
                                                            @PathVariable("customerId") Long customerId,
                                                            @ApiParam(value = "Reservation ID which should be updated", required = true)
                                                            @PathVariable("reservationId") Long reservationId,
                                                            @RequestBody Map<String, Object> params) {
        ReservationDTO reservationDTO = reservationService.updateReservation(customerId, reservationId, params);
        return new ResponseEntity<>(reservationDTO, HttpStatus.OK);
    }

    @ApiOperation("Delete reservation")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 404, message = "Customer with this ID not found or no reservations made")
            }
    )
    @DeleteMapping("/{customerId}/reservations/{reservationId}")
    public ResponseEntity<ReservationExtendedDTO> deleteReservation(@ApiParam(value = "Customer ID who made reservation", required = true)
                                                                    @PathVariable("customerId") Long customerId,
                                                                    @ApiParam(value = "Reservation ID which should be deleted", required = true)
                                                                    @PathVariable("reservationId") Long reservationId) {
        return new ResponseEntity<>(
                reservationService.deleteReservation(customerId, reservationId),
                HttpStatus.NO_CONTENT);
    }
}
