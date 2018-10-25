package org.sergei.flightreservation.api;

import org.sergei.flightreservation.dto.RouteDTO;
import org.sergei.flightreservation.service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    // TODO: Implement all methods needed
}
