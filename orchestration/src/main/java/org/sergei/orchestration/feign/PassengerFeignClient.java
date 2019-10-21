package org.sergei.orchestration.feign;

import org.sergei.orchestration.rest.dto.PassengerDTO;
import org.sergei.orchestration.rest.dto.response.ResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author Sergei Visotsky
 */
@Component
@FeignClient(value = "passenger", url = "${feign.booking-url}")
public interface PassengerFeignClient {

    @GetMapping(value = "/getPassengerById/{passengerId}")
    ResponseEntity<ResponseDTO<PassengerDTO>> getPassengerById(@PathVariable("passengerId") Long passengerId);
}
