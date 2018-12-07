package org.sergei.flightservice.controller;

import io.swagger.annotations.*;
import org.sergei.flightservice.model.Aircraft;
import org.sergei.flightservice.service.AircraftService;
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
        value = "/flight-api/aircrafts",
        produces = "application/json",
        consumes = "application/json"
)
@RestController
@RequestMapping(value = "/aircrafts", produces = "application/json")
public class AircraftController {

    @Autowired
    private AircraftService aircraftService;

    @ApiOperation(value = "Get all existing aircrafts")
    @GetMapping
    public ResponseEntity<Resources<Aircraft>> getAllAircraft() {
        List<Aircraft> aircrafts = aircraftService.findAll();
        aircrafts.forEach(aircraft -> {
            Link link = ControllerLinkBuilder.linkTo(
                    ControllerLinkBuilder.methodOn(AircraftController.class)
                            .getAircraftById(aircraft.getAircraftId())).withSelfRel();
            aircraft.add(link);
        });
        Resources<Aircraft> resources = new Resources<>(aircrafts);
        String uriString = ServletUriComponentsBuilder.fromCurrentRequest().build().toUriString();
        resources.add(new Link(uriString, "self"));
        return new ResponseEntity<>(resources, HttpStatus.OK);
    }

    @ApiOperation("Get aircraft by ID")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 404, message = "Invalid aircraft ID")
            }
    )
    @GetMapping("/{aircraftId}")
    public ResponseEntity<Aircraft> getAircraftById(@ApiParam(value = "Aircraft ID which should be found", required = true)
                                                    @PathVariable("aircraftId") Long aircraftId) {
        Aircraft aircraft = aircraftService.findOne(aircraftId);
        Link link = ControllerLinkBuilder.linkTo(AircraftController.class).withSelfRel();
        aircraft.add(link);
        return new ResponseEntity<>(aircraft, HttpStatus.OK);
    }

    @ApiOperation(value = "Save aircraft", notes = "Operation allowed for ADMIN only")
    @PostMapping(consumes = "application/json")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Aircraft> saveAircraft(@ApiParam(value = "Aircraft which should be saved", required = true)
                                                 @RequestBody Aircraft aircraft) {
        return new ResponseEntity<>(aircraftService.save(aircraft), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Update aircraft data", notes = "Operation allowed for ADMIN only")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 404, message = "Invalid aircraft ID")
            }
    )
    @PutMapping(value = "/{aircraftId}", consumes = "application/json")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Aircraft> updateAircraft(@ApiParam(value = "Aircraft ID which should be updated", required = true)
                                                   @PathVariable("aircraftId") Long aircraftId,
                                                   @ApiParam(value = "Update aircracft", required = true)
                                                   @RequestBody Aircraft aircraft) {
        return new ResponseEntity<>(aircraftService.update(aircraftId, aircraft), HttpStatus.OK);
    }

    @ApiOperation(value = "Delete aircraft", notes = "Operation allowed for ADMIN only")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 404, message = "Invalid aircraft ID")
            }
    )
    @DeleteMapping(value = "/{aircraftId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Aircraft> deleteAircraft(@ApiParam(value = "Aircraft ID which should be deleted", required = true)
                                                   @PathVariable("aircraftId") Long aircraftId) {
        return new ResponseEntity<>(aircraftService.delete(aircraftId), HttpStatus.NO_CONTENT);
    }
}
