package org.sergei.booking.feign;

import org.sergei.booking.rest.dto.FlightDTO;
import org.sergei.booking.rest.dto.response.ResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author Sergei Visotsky
 */
@FeignClient(value = "flight", url = "${feign.flight-url}")
@Component
public interface FlightFeignClient {

    @GetMapping(value = "/getFlightById/{flightId}")
    ResponseEntity<ResponseDTO<FlightDTO>> getFlightById(@PathVariable("flightId") Long flightId);

}
