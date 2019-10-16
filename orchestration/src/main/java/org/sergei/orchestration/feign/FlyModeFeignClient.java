package org.sergei.orchestration.feign;

import org.sergei.orchestration.rest.dto.FlyModeDTO;
import org.sergei.orchestration.rest.dto.response.ResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Sergei Visotsky
 */
@Component
@FeignClient(value = "flyMode", url = "${feign.manager-url}")
public interface FlyModeFeignClient {

    @GetMapping(value = "/getFlyModeByCode")
    ResponseEntity<ResponseDTO<FlyModeDTO>> getFlyModeByCode(@RequestParam("code") String code);
}
