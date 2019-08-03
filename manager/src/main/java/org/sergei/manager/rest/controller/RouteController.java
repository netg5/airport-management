package org.sergei.manager.rest.controller;

import io.swagger.annotations.Api;
import org.sergei.manager.rest.dto.RouteDTO;
import org.sergei.manager.rest.dto.response.ResponseDTO;
import org.sergei.manager.service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author Sergei Visotsky
 */
@RestController
@Api(tags = {"routeCrudOperations"})
public class RouteController {

    private final RouteService routeService;

    @Autowired
    public RouteController(RouteService routeService) {
        this.routeService = routeService;
    }

    @GetMapping(value = "/getAllRoutes")
    public ResponseEntity<ResponseDTO<RouteDTO>> getAllRoutes() {
        return routeService.findAllRoutes();
    }

    @GetMapping(value = "/getAllRoutesPaginated", params = {"page", "size"})
    public ResponseEntity<ResponseDTO<RouteDTO>> getAllRoutesPaginated(@RequestParam("page") int page,
                                                                       @RequestParam("size") int size) {
        return routeService.findAllRoutesPaginated(page, size);
    }

    @GetMapping(value = "/getRouteById/{routeId}")
    public ResponseEntity<ResponseDTO<RouteDTO>> getRouteById(@PathVariable("routeId") Long routeId) {
        return routeService.findOneRoute(routeId);
    }

    @PostMapping(value = "/saveRoute")
    public ResponseEntity<ResponseDTO<RouteDTO>> saveRoute(@RequestBody RouteDTO request) {
        return routeService.save(request);
    }

    @PutMapping(value = "/updateRoute")
    public ResponseEntity<ResponseDTO<RouteDTO>> updateRoute(@RequestBody RouteDTO request) {
        return routeService.update(request);
    }

    @DeleteMapping(value = "/deleteRoute/{routeId}")
    public ResponseEntity<ResponseDTO<RouteDTO>> deleteRoute(@PathVariable("routeId") Long routeId) {
        return routeService.delete(routeId);
    }
}