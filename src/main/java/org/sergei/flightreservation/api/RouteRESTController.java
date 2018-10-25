package org.sergei.flightreservation.api;

import org.sergei.flightreservation.dto.RouteDTO;
import org.sergei.flightreservation.service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/routes", produces = {"application/json", "application/xml"})
public class RouteRESTController {

    @Autowired
    private RouteService routeService;

    @GetMapping
    public ResponseEntity<List<RouteDTO>> getAllRoutes() {
        return new ResponseEntity<>(routeService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{routeId}")
    public ResponseEntity<RouteDTO> getRouteById(@PathVariable("routeId") Long routeId) {
        return new ResponseEntity<>(routeService.findOne(routeId), HttpStatus.OK);
    }

    @PostMapping(consumes = {"application/json", "application/xml"})
    public ResponseEntity<RouteDTO> saveRoute(@RequestBody RouteDTO routeDTO) {
        return new ResponseEntity<>(routeService.save(routeDTO), HttpStatus.OK);
    }

    @PutMapping(value = "/{routeId}", consumes = {"application/json", "application/xml"})
    public ResponseEntity<RouteDTO> updateCustomer(@PathVariable("routeId") Long routeId,
                                                   @RequestBody RouteDTO routeDTO) {
        return new ResponseEntity<>(routeService.update(routeId, routeDTO), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{routeId}")
    public ResponseEntity<RouteDTO> deleteCustomer(@PathVariable("routeId") Long routeId) {
        return new ResponseEntity<>(routeService.delete(routeId), HttpStatus.OK);
    }
}
