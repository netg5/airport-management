package org.sergei.orchestration.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

/**
 * @author Sergei Visotsky
 */
@Component
@FeignClient(value = "booking", url = "${feign.booking-url}")
public interface BookingFeignClient {
    // TODO
}
