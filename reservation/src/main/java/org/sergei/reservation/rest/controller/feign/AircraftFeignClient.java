package org.sergei.reservation.rest.controller.feign;

import org.sergei.reservation.rest.dto.AircraftDTO;
import org.sergei.reservation.rest.dto.AircraftRequestDTO;
import org.sergei.reservation.rest.dto.response.ResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author Sergei Visotsky
 */
@FeignClient(value = "aircraft", url = "${feign.route-url}")
@Component
public interface AircraftFeignClient {

    @GetMapping(value = "/getAllAircrafts")
    ResponseEntity<ResponseDTO<AircraftDTO>> getAllAircrafts();

    @PostMapping(value = "/getAircraftById")
    ResponseEntity<ResponseDTO<AircraftDTO>> getAircraftByModelNumber(@RequestBody AircraftRequestDTO request);
}
