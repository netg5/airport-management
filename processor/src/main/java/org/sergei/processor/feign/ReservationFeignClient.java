package org.sergei.processor.feign;

import org.sergei.processor.rest.dto.ReservationDTO;
import org.sergei.processor.rest.dto.response.ResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Sergei Visotsky
 */
@FeignClient(value = "reservation", url = "${feign.reservation-url}")
public interface ReservationFeignClient {

    @GetMapping(value = "/getAllReservations")
    ResponseEntity<ResponseDTO<ReservationDTO>> getAllReservations();

}
