/*
 * Copyright (c) Sergei Visotsky, 2018
 */

package org.sergei.flightreservation.api;

import io.swagger.annotations.ApiOperation;
import org.sergei.flightreservation.dto.RouteDTO;
import org.sergei.flightreservation.dto.RouteExtendedDTO;
import org.sergei.flightreservation.service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Sergei Visotsky, 2018
 */
@RestController
@RequestMapping(value = "/api/v1/routes", produces = "application/json")
public class RouteRESTController {

    @Autowired
    private RouteService routeService;

    @ApiOperation("Get all existing routes")
    @GetMapping
    public ResponseEntity<List<RouteExtendedDTO>> getAllRoutes() {
        return new ResponseEntity<>(routeService.findAll(), HttpStatus.OK);
    }

    @ApiOperation("Get route by ID")
    @GetMapping("/{routeId}")
    public ResponseEntity<RouteDTO> getRouteById(@PathVariable("routeId") Long routeId) {
        return new ResponseEntity<>(routeService.findOne(routeId), HttpStatus.OK);
    }

    @ApiOperation("Save route")
    @PostMapping(consumes = "application/json")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<RouteDTO> saveRoute(@RequestBody RouteDTO routeDTO) {
        return new ResponseEntity<>(routeService.save(routeDTO), HttpStatus.OK);
    }

    @ApiOperation("Update route information")
    @PutMapping(value = "/{routeId}", consumes = "application/json")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<RouteDTO> updateRoute(@PathVariable("routeId") Long routeId,
                                                @RequestBody RouteDTO routeDTO) {
        return new ResponseEntity<>(routeService.update(routeId, routeDTO), HttpStatus.OK);
    }

    @ApiOperation("Method to delete route")
    @DeleteMapping("/{routeId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<RouteDTO> deleteRoute(@PathVariable("routeId") Long routeId) {
        return new ResponseEntity<>(routeService.delete(routeId), HttpStatus.NO_CONTENT);
    }
}
