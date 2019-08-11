package org.sergei.reservation.feign;

import org.sergei.reservation.rest.dto.RouteDTO;
import org.sergei.reservation.rest.dto.response.ResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author Sergei Visotsky
 */
@FeignClient(value = "route", url = "${feign.route-url}")
public interface RouteFeignClient {

    @GetMapping(value = "/getAllRoutes")
    ResponseEntity<ResponseDTO<RouteDTO>> getAllRoutes();

    @GetMapping(value = "/getRouteById/{routeId}")
    ResponseEntity<ResponseDTO<RouteDTO>> getRouteById(@PathVariable("routeId") Long routeId);
}