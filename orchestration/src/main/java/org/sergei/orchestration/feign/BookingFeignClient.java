package org.sergei.orchestration.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

/**
 * @author Sergei Visotsky
 */
@Component
@FeignClient(value = "booking", url = "dummy")
public interface BookingFeignClient {
    // TODO
}
