package org.sergei.orchestration.feign;

import org.sergei.orchestration.rest.dto.BookingDTO;
import org.sergei.orchestration.rest.dto.response.ResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @author Sergei Visotsky
 */
@Component
@FeignClient(value = "booking", url = "${feign.booking-url}")
public interface BookingFeignClient {

    @GetMapping(value = "/getAllBookingsForPassenger/{passengerId}")
    ResponseEntity<ResponseDTO<List<BookingDTO>>> getAllBookingsForPassenger(@PathVariable("passengerId") Long passengerId);
}
