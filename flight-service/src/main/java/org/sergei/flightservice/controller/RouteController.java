package org.sergei.flightservice.controller;

import io.swagger.annotations.*;
import org.sergei.flightservice.dto.RouteDTO;
import org.sergei.flightservice.dto.RouteExtendedDTO;
import org.sergei.flightservice.service.RouteService;
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
        value = "/flight-api/routes",
        produces = "application/json",
        consumes = "application/json"
)
@RestController
@RequestMapping(value = "/routes", produces = "application/json")
public class RouteController {

    @Autowired
    private RouteService routeService;

    @ApiOperation("Get all existing routes")
    @GetMapping
    public ResponseEntity<Resources<RouteExtendedDTO>> getAllRoutes() {
        List<RouteExtendedDTO> routes = routeService.findAllRoutes();
        routes.forEach(route -> {
            Link link = ControllerLinkBuilder.linkTo(
                    ControllerLinkBuilder.methodOn(RouteController.class)
                            .getRouteById(route.getRouteId())).withSelfRel();
            route.add(link);
        });
        Resources<RouteExtendedDTO> resources = new Resources<>(routes);
        String uriString = ServletUriComponentsBuilder.fromCurrentRequest().build().toUriString();
        resources.add(new Link(uriString, "self"));
        return new ResponseEntity<>(resources, HttpStatus.OK);
    }

    @ApiOperation("Get route by ID")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 404, message = "Invalid route ID")
            }
    )
    @GetMapping("/{routeId}")
    public ResponseEntity<RouteDTO> getRouteById(@ApiParam(value = "Route ID which should be found", required = true)
                                                 @PathVariable("routeId") Long routeId) {
        RouteDTO routeDTO = routeService.findOneRoute(routeId);
        return new ResponseEntity<>(setLinks(routeDTO), HttpStatus.OK);
    }

    @ApiOperation(value = "Save route", notes = "Operation allowed for ADMIN only")
    @PostMapping(consumes = "application/json")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<RouteDTO> saveRoute(@ApiParam(value = "Saved route", required = true)
                                              @RequestBody RouteDTO routeDTO) {
        return new ResponseEntity<>(routeService.save(routeDTO), HttpStatus.OK);
    }

    @ApiOperation(value = "Update route information", notes = "Operation allowed for ADMIN only")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 404, message = "Invalid route ID")
            }
    )
    @PutMapping(value = "/{routeId}", consumes = "application/json")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<RouteDTO> updateRoute(@ApiParam(value = "Route ID which should be updated", required = true)
                                                @PathVariable("routeId") Long routeId,
                                                @ApiParam(value = "Saved route", required = true)
                                                @RequestBody RouteDTO routeDTO) {
        RouteDTO route = routeService.update(routeId, routeDTO);
        return new ResponseEntity<>(setLinks(route), HttpStatus.OK);
    }

    @ApiOperation(value = "Update one field for the route", notes = "Operation allowed for ADMIN only")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 404, message = "Invalid route ID")
            }
    )
    @PatchMapping(value = "/{routeId}", consumes = "application/json")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<RouteDTO> patchRoute(@ApiParam(value = "Route ID which should be updated", required = true)
                                               @PathVariable("routeId") Long routeId,
                                               @RequestBody Map<String, Object> params) {

        RouteDTO routeDTO = routeService.patch(routeId, params);
        return new ResponseEntity<>(setLinks(routeDTO), HttpStatus.OK);
    }

    @ApiOperation(value = "Method to delete route", notes = "Operation allowed for ADMIN only")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 404, message = "Invalid route ID")
            }
    )
    @DeleteMapping("/{routeId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<RouteDTO> deleteRoute(@ApiParam(value = "Route ID which should be deleted", required = true)
                                                @PathVariable("routeId") Long routeId) {
        return new ResponseEntity<>(routeService.delete(routeId), HttpStatus.NO_CONTENT);
    }

    /**
     * Method to set HATEOAS links for customer
     *
     * @param routeDTO get route DTO to set links
     * @return DTO with links added
     */
    private RouteDTO setLinks(RouteDTO routeDTO) {
        Link link = ControllerLinkBuilder.linkTo(RouteController.class).withRel("allRoutes");
        Link selfLink = ControllerLinkBuilder.linkTo(
                ControllerLinkBuilder.methodOn(RouteController.class).getRouteById(routeDTO.getRouteId())).withSelfRel();
        routeDTO.add(selfLink);
        routeDTO.add(link);
        return routeDTO;
    }
}
