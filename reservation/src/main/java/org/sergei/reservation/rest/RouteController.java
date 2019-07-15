/*
 * Copyright 2018-2019 the original author.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.sergei.reservation.rest;

import io.swagger.annotations.*;
import org.sergei.reservation.rest.dto.RouteDTO;
import org.sergei.reservation.rest.dto.RouteExtendedDTO;
import org.sergei.reservation.rest.hateoas.LinkUtil;
import org.sergei.reservation.service.RouteService;
import org.sergei.reservation.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author Sergei Visotsky
 */
@Api(
        value = "/flight-api/routes",
        produces = "application/json",
        consumes = "application/json"
)
@RestController
@RequestMapping(value = "/routes", produces = "application/json")
public class RouteController {

    private final LinkUtil linkUtil;
    private final RouteService<RouteDTO, RouteExtendedDTO> routeService;

    @Autowired
    public RouteController(LinkUtil linkUtil, RouteService routeService) {
        this.linkUtil = linkUtil;
        this.routeService = routeService;
    }

    @ApiOperation("Get all existing routes")
    @GetMapping
    public ResponseEntity<Resources> getAllRoutes() {
        List<RouteExtendedDTO> routes = routeService.findAllRoutes();
        return new ResponseEntity<>(linkUtil.setLinksForEachRoute(routes), HttpStatus.OK);
    }

    @ApiOperation("Get all existing routes paginated")
    @GetMapping(params = {"page", "size"})
    public ResponseEntity<Resources> getAllRoutesPaginated(@ApiParam("Number of the page")
                                                           @RequestParam("page") int page,
                                                           @ApiParam("Maximum number of content blocks on the page")
                                                           @RequestParam("size") int size) {
        Page<RouteExtendedDTO> routes = routeService.findAllRoutesPaginated(page, size);
        return new ResponseEntity<>(linkUtil.setLinksForEachRoute(routes), HttpStatus.OK);
    }

    @ApiOperation("Get route by ID")
    @ApiResponses({
            @ApiResponse(code = 404, message = Constants.ROUTE_NOT_FOUND)
    })
    @GetMapping("/{routeId}")
    public ResponseEntity<RouteDTO> getRouteById(@ApiParam(value = "Route ID which should be found", required = true)
                                                 @PathVariable("routeId") Long routeId) {
        RouteDTO routeDTO = routeService.findOneRoute(routeId);
        return new ResponseEntity<>(linkUtil.setLinksForRoute(routeDTO), HttpStatus.OK);
    }

    @ApiOperation(value = "Save route", notes = "Operation allowed for the ROLE_ADMIN only")
    @ApiResponses({
            @ApiResponse(code = 404, message = Constants.AIRCRAFT_NOT_FOUND)
    })
    @PostMapping(consumes = "application/json")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<RouteDTO> saveRoute(@ApiParam(value = "Saved route", required = true)
                                              @RequestBody RouteDTO routeDTO) {
        return new ResponseEntity<>(routeService.save(routeDTO), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Update route information", notes = "Operation allowed for the ROLE_ADMIN only")
    @ApiResponses({
            @ApiResponse(code = 404, message = "Route or aircraft with this ID not found")
    })
    @PutMapping(value = "/{routeId}", consumes = "application/json")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<RouteDTO> updateRoute(@ApiParam(value = "Route ID which should be updated", required = true)
                                                @PathVariable("routeId") Long routeId,
                                                @ApiParam(value = "Saved route", required = true)
                                                @RequestBody RouteDTO routeDTO) {
        RouteDTO route = routeService.update(routeId, routeDTO);
        return new ResponseEntity<>(linkUtil.setLinksForRoute(route), HttpStatus.OK);
    }

    @ApiOperation(value = "Update one field for the route", notes = "Operation allowed for the ROLE_ADMIN only")
    @ApiResponses({
            @ApiResponse(code = 404, message = "Route or aircraft with this ID not found")
    })
    @PatchMapping(value = "/{routeId}/patch", consumes = "application/json")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<RouteDTO> patchRoute(@ApiParam(value = "Route ID which should be updated", required = true)
                                               @PathVariable("routeId") Long routeId,
                                               @RequestBody Map<String, Object> params) {

        RouteDTO routeDTO = routeService.patch(routeId, params);
        return new ResponseEntity<>(linkUtil.setLinksForRoute(routeDTO), HttpStatus.OK);
    }

    @ApiOperation(value = "Method to delete route", notes = "Operation allowed for the ROLE_ADMIN only")
    @ApiResponses({
            @ApiResponse(code = 404, message = Constants.ROUTE_NOT_FOUND)
    })
    @DeleteMapping("/{routeId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<RouteDTO> deleteRoute(@ApiParam(value = "Route ID which should be deleted", required = true)
                                                @PathVariable("routeId") Long routeId) {
        return new ResponseEntity<>(routeService.delete(routeId), HttpStatus.NO_CONTENT);
    }
}
