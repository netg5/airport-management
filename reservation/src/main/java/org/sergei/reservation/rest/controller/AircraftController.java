package org.sergei.reservation.rest.controller;

import io.swagger.annotations.Api;
import org.sergei.reservation.rest.controller.feign.AircraftFeignClient;
import org.sergei.reservation.rest.dto.AircraftDTO;
import org.sergei.reservation.rest.dto.AircraftRequestDTO;
import org.sergei.reservation.rest.dto.response.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Sergei Visotsky
 */
@RestController
@Api(tags = {"getAircraftData"})
public class AircraftController {

    private final AircraftFeignClient aircraftFeignClient;

    @Autowired
    public AircraftController(AircraftFeignClient aircraftFeignClient) {
        this.aircraftFeignClient = aircraftFeignClient;
    }

    @GetMapping(value = "/getAllAircrafts")
    public ResponseEntity<ResponseDTO<AircraftDTO>> getAllAircrafts() {
        return aircraftFeignClient.getAllAircrafts();
    }

    @PostMapping(value = "/getAircraftById")
    public ResponseEntity<ResponseDTO<AircraftDTO>> getAircraftById(@RequestBody AircraftRequestDTO request) {
        return aircraftFeignClient.getAircraftByModelNumber(request);
    }
}
