package org.sergei.flightservice.controller;

import io.swagger.annotations.*;
import org.sergei.flightservice.dto.ReservationDTO;
import org.sergei.flightservice.dto.ReservationExtendedDTO;
import org.sergei.flightservice.service.ReservationService;
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
import java.util.Map;

/**
 * @author Sergei Visotsky, 2018
 */
@Api(
        value = "/flight-api/customers/{customerId}/reservations/",
        produces = "application/json",
        consumes = "application/json"
)
@RestController
@RequestMapping(value = "/customers", produces = "application/json")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @ApiOperation("Get all reservations for customer")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 404, message = "Invalid customer ID")
            }
    )
    @GetMapping("/{customerId}/reservations")
    public ResponseEntity<Resources<ReservationExtendedDTO>> getAllForCustomer(@ApiParam(value = "Customer ID whose reservations should be found", required = true)
                                                                               @PathVariable("customerId") Long customerId) {
        List<ReservationExtendedDTO> flightReservations =
                reservationService.findAllForCustomer(customerId);
        flightReservations.forEach(flightReservation -> {
            Link link = ControllerLinkBuilder.linkTo(
                    ControllerLinkBuilder.methodOn(ReservationController.class)
                            .getOneForCustomer(flightReservation.getCustomerId(),
                                    flightReservation.getReservationId())).withRel("reservation");
            flightReservation.add(link);
        });
        Resources<ReservationExtendedDTO> resources = new Resources<>(flightReservations);
        String uriString = ServletUriComponentsBuilder.fromCurrentRequest().build().toUriString();
        resources.add(new Link(uriString, "self"));
        return new ResponseEntity<>(resources, HttpStatus.OK);
    }

    @ApiOperation("Get one reservation by ID for the customer")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 404, message = "Invalid customer or reservation ID")
            }
    )
    @GetMapping("/{customerId}/reservations/{reservationId}")
    public ResponseEntity<ReservationExtendedDTO> getOneForCustomer(@ApiParam(value = "Customer ID who made a reservation", required = true)
                                                                    @PathVariable("customerId") Long customerId,
                                                                    @ApiParam(value = "Reservation ID which which was made", required = true)
                                                                    @PathVariable("reservationId") Long reservationId) {
        ReservationExtendedDTO flightReservationExtendedDTO =
                reservationService.findOneForCustomer(customerId, reservationId);
        Link link = ControllerLinkBuilder.linkTo(
                ControllerLinkBuilder.methodOn(CustomerController.class).getCustomerById(customerId)).withRel("customer");
        flightReservationExtendedDTO.add(link);
        String uriString = ServletUriComponentsBuilder.fromCurrentRequest().build().toUriString();
        flightReservationExtendedDTO.add(new Link(uriString, "self"));
        return new ResponseEntity<>(flightReservationExtendedDTO, HttpStatus.OK);
    }

    @ApiOperation("Create reservation for customer")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 404, message = "Invalid customer ID")
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

    @ApiOperation("Update reservation by customer ID")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 404, message = "Invalid customer or reservation ID")
            }
    )
    @PutMapping(value = "/{customerId}/reservations/{reservationId}", consumes = "application/json")
    public ResponseEntity<ReservationDTO> updateReservation(@ApiParam(value = "Customer ID who made reservation", required = true)
                                                            @PathVariable("customerId") Long customerId,
                                                            @ApiParam(value = "Reservation ID which should be updated", required = true)
                                                            @PathVariable("reservationId") Long reservationId,
                                                            @ApiParam(value = "Saved flight reservation", required = true)
                                                            @RequestBody ReservationDTO reservationDTO) {
        return new ResponseEntity<>(
                reservationService.updateReservation(customerId, reservationId, reservationDTO),
                HttpStatus.ACCEPTED);
    }

    @ApiOperation(value = "Update one field for the reservation", notes = "Operation allowed for ADMIN only")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 404, message = "Invalid reservation ID")
            }
    )
    @PutMapping(value = "/{reservationId}", consumes = "application/json")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<ReservationDTO> patchRoute(@ApiParam(value = "Reservation ID which should be updated", required = true)
                                                     @PathVariable("reservationId") Long reservationId,
                                                     @RequestBody Map<String, Object> params) {

        ReservationDTO reservationDTO = reservationService.patchReservation(reservationId, params);
        return new ResponseEntity<>(reservationDTO, HttpStatus.OK);
    }

    @ApiOperation("Delete reservation")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 404, message = "Invalid reservation ID")
            }
    )
    @DeleteMapping("/reservations/{reservationId}")
    public ResponseEntity<ReservationExtendedDTO> deleteReservation(@ApiParam(value = "Reservation ID which should be deleted", required = true)
                                                                    @PathVariable("reservationId") Long reservationId) {
        return new ResponseEntity<>(
                reservationService.delete(reservationId),
                HttpStatus.NO_CONTENT);
    }
}
