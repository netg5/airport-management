package org.sergei.manager.rest.controller;

import io.swagger.annotations.Api;
import org.sergei.manager.rest.dto.FlightDTO;
import org.sergei.manager.rest.dto.response.ResponseDTO;
import org.sergei.manager.service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author Sergei Visotsky
 */
@RestController
@Api(tags = {"flightCrudOperations"})
public class FlightController {

    private final FlightService flightService;

    @Autowired
    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }

    @GetMapping(value = "/getAllFlights")
    public ResponseEntity<ResponseDTO<FlightDTO>> getAllFlights() {
        return flightService.findAllFlights();
    }

    @GetMapping(value = "/getFlightById/{flightId}")
    public ResponseEntity<ResponseDTO<FlightDTO>> getFlightById(@PathVariable("flightId") Long flightId) {
        return flightService.findOneFlight(flightId);
    }

    @PostMapping(value = "/saveFlight")
    public ResponseEntity<ResponseDTO<FlightDTO>> saveFlight(@RequestBody FlightDTO request) {
        return flightService.save(request);
    }

    @PutMapping(value = "/updateFlight")
    public ResponseEntity<ResponseDTO<FlightDTO>> updateFlight(@RequestBody FlightDTO request) {
        return flightService.update(request);
    }

    @DeleteMapping(value = "/deleteFlight/{flightId}")
    public ResponseEntity<ResponseDTO<FlightDTO>> deleteFlight(@PathVariable("flightId") Long flightId) {
        return flightService.delete(flightId);
    }
}