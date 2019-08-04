package org.sergei.manager.rest.controller;

import io.swagger.annotations.Api;
import org.sergei.manager.rest.dto.AirportContactDTO;
import org.sergei.manager.rest.dto.AirportDTO;
import org.sergei.manager.rest.dto.request.AirportRequestDTO;
import org.sergei.manager.rest.dto.response.ResponseDTO;
import org.sergei.manager.service.AirportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Sergei Visotsky
 */
@RestController
@Api(tags = {"getAndSaveAirportData"})
public class AirportController {

    private final AirportService airportService;

    @Autowired
    public AirportController(AirportService airportService) {
        this.airportService = airportService;
    }

    @PostMapping(value = "/getAirportByName")
    public ResponseEntity<ResponseDTO<AirportDTO>> getAirportByName(@RequestBody AirportRequestDTO request) {
        return airportService.getAirportByName(request);
    }

    @PostMapping(value = "/getAirportContactByAirportName")
    public ResponseEntity<ResponseDTO<AirportContactDTO>> getAirportContactByAirportName(@RequestBody AirportRequestDTO request) {
        return airportService.getAirportContactByAirportName(request);
    }

    @PostMapping(value = "/saveAirportData")
    public ResponseEntity<ResponseDTO<AirportDTO>> saveAirportData(@RequestBody AirportDTO request) {
        return airportService.saveAirportData(request);
    }
}
