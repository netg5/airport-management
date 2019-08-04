package org.sergei.reservation.rest.controller.feign;

import org.sergei.reservation.rest.dto.RouteResponseDTO;
import org.sergei.reservation.rest.dto.response.ResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author Sergei Visotsky
 */
@FeignClient(value = "route", url = "http://localhost:8088/")
@Component
public interface RouteFeignClient {

    @GetMapping(value = "/getAllRoutes")
    ResponseEntity<ResponseDTO<RouteResponseDTO>> getAllRoutes();

    @GetMapping(value = "/getRouteById/{routeId}")
    ResponseEntity<ResponseDTO<RouteResponseDTO>> getRouteById(@PathVariable("routeId") Long routeId);
}
