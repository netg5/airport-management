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

package org.sergei.reservation.rest.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.sergei.reservation.rest.dto.RouteRequestDTO;
import org.sergei.reservation.rest.dto.RouteResponseDTO;
import org.sergei.reservation.rest.dto.RouteUpdateRequestDTO;
import org.sergei.reservation.rest.dto.response.ResponseDTO;
import org.sergei.reservation.service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author Sergei Visotsky
 */
@Api(
        value = "/reservation/routes",
        produces = "application/json",
        consumes = "application/json"
)
@RestController
@RequestMapping("/routes")
public class RouteController {

    private final RouteService routeService;

    @Autowired
    public RouteController(RouteService routeService) {
        this.routeService = routeService;
    }

    @ApiOperation("Get all existing routes")
    @GetMapping(produces = "application/json", consumes = "application/json")
    public ResponseEntity<ResponseDTO<RouteResponseDTO>> getAllRoutes() {
        return routeService.findAllRoutes();
    }

    @ApiOperation("Get all existing routes paginated")
    @GetMapping(params = {"page", "size"}, produces = "application/json", consumes = "application/json")
    public ResponseEntity<ResponseDTO<RouteResponseDTO>> getAllRoutesPaginated(@ApiParam("Number of the page")
                                                                               @RequestParam("page") int page,
                                                                               @ApiParam("Maximum number of content blocks on the page")
                                                                               @RequestParam("size") int size) {
        return routeService.findAllRoutesPaginated(page, size);
    }

    @ApiOperation("Get route by ID")
    @GetMapping(value = "/{routeId}", produces = "application/json", consumes = "application/json")
    public ResponseEntity<ResponseDTO<RouteResponseDTO>> getRouteById(@ApiParam(value = "Route ID which should be found", required = true)
                                                                      @PathVariable("routeId") Long routeId) {
        return routeService.findOneRoute(routeId);
    }

    @ApiOperation(value = "Save route", notes = "Operation allowed for the ROLE_ADMIN only")
    @PostMapping(consumes = "application/json")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<ResponseDTO<RouteResponseDTO>> saveRoute(@ApiParam(value = "Saved route", required = true)
                                                                   @RequestBody RouteRequestDTO request) {
        return routeService.save(request);
    }

    @ApiOperation(value = "Update route information", notes = "Operation allowed for the ROLE_ADMIN only")
    @PutMapping(produces = "application/json", consumes = "application/json")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<ResponseDTO<RouteResponseDTO>> updateRoute(@RequestBody RouteUpdateRequestDTO request) {
        return routeService.update(request);
    }

    @ApiOperation(value = "Update one field for the route", notes = "Operation allowed for the ROLE_ADMIN only")
    @PatchMapping(value = "/{routeId}/patch", produces = "application/json", consumes = "application/json")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<ResponseDTO<RouteResponseDTO>> patchRoute(@ApiParam(value = "Route ID which should be updated", required = true)
                                                                    @PathVariable("routeId") Long routeId,
                                                                    @RequestBody Map<String, Object> params) {

        return routeService.patch(routeId, params);
    }

    @ApiOperation(value = "Method to delete route", notes = "Operation allowed for the ROLE_ADMIN only")
    @DeleteMapping(value = "/{routeId}", produces = "application/json", consumes = "application/json")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<ResponseDTO<RouteResponseDTO>> deleteRoute(@ApiParam(value = "Route ID which should be deleted", required = true)
                                                                     @PathVariable("routeId") Long routeId) {
        return routeService.delete(routeId);
    }
}
