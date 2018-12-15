package org.sergei.flightservice.controller;

import io.swagger.annotations.*;
import org.sergei.flightservice.dto.AircraftDTO;
import org.sergei.flightservice.service.AircraftService;
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

    private final AircraftService aircraftService;

    @Autowired
    public AircraftController(AircraftService aircraftService) {
        this.aircraftService = aircraftService;
    }

    @ApiOperation(value = "Get all existing aircrafts")
    @GetMapping
    public ResponseEntity<Resources<AircraftDTO>> getAllAircraft() {
        List<AircraftDTO> aircrafts = aircraftService.findAll();
        aircrafts.forEach(aircraft -> {
            Link link = ControllerLinkBuilder.linkTo(
                    ControllerLinkBuilder.methodOn(AircraftController.class)
                            .getAircraftById(aircraft.getAircraftId())).withSelfRel();
            aircraft.add(link);
        });
        Resources<AircraftDTO> resources = new Resources<>(aircrafts);
        String uriString = ServletUriComponentsBuilder.fromCurrentRequest().build().toUriString();
        resources.add(new Link(uriString, "self"));
        return new ResponseEntity<>(resources, HttpStatus.OK);
    }

    @ApiOperation(value = "Get all existing aircrafts paginated")
    @GetMapping(params = {"page", "size"})
    public ResponseEntity<Resources<AircraftDTO>> getAllAircraftPaginated(@ApiParam(value = "Number of page", required = true)
                                                                          @RequestParam("page") int page,
                                                                          @ApiParam(value = "Number of elements per page", required = true)
                                                                          @RequestParam("size") int size) {
        Page<AircraftDTO> aircrafts = aircraftService.findAllPaginated(page, size);
        aircrafts.forEach(aircraft -> {
            Link link = ControllerLinkBuilder.linkTo(
                    ControllerLinkBuilder.methodOn(AircraftController.class)
                            .getAircraftById(aircraft.getAircraftId())).withSelfRel();
            aircraft.add(link);
        });
        Resources<AircraftDTO> resources = new Resources<>(aircrafts);
        String uriString = ServletUriComponentsBuilder.fromCurrentRequest().build().toUriString();
        resources.add(new Link(uriString, "self"));
        return new ResponseEntity<>(resources, HttpStatus.OK);
    }

    @ApiOperation("Get aircraftDTO by ID")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 404, message = "Invalid aircraft ID")
            }
    )
    @GetMapping("/{aircraftId}")
    public ResponseEntity<AircraftDTO> getAircraftById(@ApiParam(value = "Aircraft ID which should be found", required = true)
                                                       @PathVariable("aircraftId") Long aircraftId) {
        AircraftDTO aircraftDTO = aircraftService.findOne(aircraftId);
        return new ResponseEntity<>(setLinks(aircraftDTO), HttpStatus.OK);
    }

    @ApiOperation(value = "Save aircraft", notes = "Operation allowed for ADMIN only")
    @PostMapping(consumes = "application/json")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<AircraftDTO> saveAircraft(@ApiParam(value = "Aircraft which should be saved", required = true)
                                                    @RequestBody AircraftDTO aircraftDTO) {
        return new ResponseEntity<>(aircraftService.save(aircraftDTO), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Update aircraft data", notes = "Operation allowed for ADMIN only")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 404, message = "Invalid aircraft ID")
            }
    )
    @PutMapping(value = "/{aircraftId}", consumes = "application/json")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<AircraftDTO> updateAircraft(@ApiParam(value = "Aircraft ID which should be updated", required = true)
                                                      @PathVariable("aircraftId") Long aircraftId,
                                                      @ApiParam(value = "Update aircracft", required = true)
                                                      @RequestBody AircraftDTO aircraftDTO) {
        AircraftDTO aircraft = aircraftService.update(aircraftId, aircraftDTO);
        return new ResponseEntity<>(setLinks(aircraft), HttpStatus.OK);
    }

    @ApiOperation(value = "Update one field of the aircraft", notes = "Operation allowed for ADMIN only")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 404, message = "Invalid aircraft ID")
            }
    )
    @PatchMapping(value = "/{aircraftId}/patch", consumes = "application/json")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<AircraftDTO> patchAircraft(@ApiParam(value = "Aircraft ID which should be updated", required = true)
                                                     @PathVariable("aircraftId") Long aircraftId,
                                                     @RequestBody Map<String, Object> params) {

        AircraftDTO aircraftDTO = aircraftService.patch(aircraftId, params);
        return new ResponseEntity<>(setLinks(aircraftDTO), HttpStatus.OK);
    }

    @ApiOperation(value = "Delete aircraft", notes = "Operation allowed for ADMIN only")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 404, message = "Invalid aircraft ID")
            }
    )
    @DeleteMapping(value = "/{aircraftId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<AircraftDTO> deleteAircraft(@ApiParam(value = "Aircraft ID which should be deleted", required = true)
                                                      @PathVariable("aircraftId") Long aircraftId) {
        return new ResponseEntity<>(aircraftService.delete(aircraftId), HttpStatus.NO_CONTENT);
    }

    /**
     * Method to set HATEOAS links for aircraft
     *
     * @param aircraftDTO get DTO to setup links
     * @return DTO with links
     */
    private AircraftDTO setLinks(AircraftDTO aircraftDTO) {
        Link selfLink = ControllerLinkBuilder.linkTo(
                ControllerLinkBuilder.methodOn(AircraftController.class).getAircraftById(aircraftDTO.getAircraftId())).withSelfRel();
        Link link = ControllerLinkBuilder.linkTo(AircraftController.class).withRel("allAircrafts");
        aircraftDTO.add(selfLink);
        aircraftDTO.add(link);
        return aircraftDTO;
    }
}
