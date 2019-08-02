package org.sergei.manager.rest.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.sergei.manager.rest.dto.RouteDTO;
import org.sergei.manager.rest.dto.response.ResponseDTO;
import org.sergei.manager.service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @GetMapping(produces = "application/json")
    public ResponseEntity<ResponseDTO<RouteDTO>> getAllRoutes() {
        return routeService.findAllRoutes();
    }

    @ApiOperation("Get all existing routes paginated")
    @GetMapping(params = {"page", "size"}, produces = "application/json")
    public ResponseEntity<ResponseDTO<RouteDTO>>
    getAllRoutesPaginated(@ApiParam("Number of the page")
                          @RequestParam("page") int page,
                          @ApiParam("Maximum number of content blocks on the page")
                          @RequestParam("size") int size) {
        return routeService.findAllRoutesPaginated(page, size);
    }

    @ApiOperation("Get route by ID")
    @GetMapping(value = "/{routeId}", produces = "application/json")
    public ResponseEntity<ResponseDTO<RouteDTO>>
    getRouteById(@ApiParam(value = "Route ID which should be found", required = true)
                 @PathVariable("routeId") Long routeId) {
        return routeService.findOneRoute(routeId);
    }

    @ApiOperation(value = "Save route", notes = "Operation allowed for the ROLE_ADMIN only")
    @PostMapping(consumes = "application/json")
    public ResponseEntity<ResponseDTO<RouteDTO>>
    saveRoute(@ApiParam(value = "Saved route", required = true)
              @RequestBody RouteDTO request) {
        return routeService.save(request);
    }

    @ApiOperation(value = "Update route information", notes = "Operation allowed for the ROLE_ADMIN only")
    @PutMapping(value = "/update", produces = "application/json", consumes = "application/json")
    public ResponseEntity<ResponseDTO<RouteDTO>>
    updateRoute(@RequestBody RouteDTO request) {
        return routeService.update(request);
    }

    @DeleteMapping(value = "/{routeId}", produces = "application/json", consumes = "application/json")
    public ResponseEntity<ResponseDTO<RouteDTO>>
    deleteRoute(@ApiParam(value = "Route ID which should be deleted", required = true)
                @PathVariable("routeId") Long routeId) {
        return routeService.delete(routeId);
    }
}