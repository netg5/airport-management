package org.sergei.reservation.service;

import org.sergei.reservation.rest.dto.RouteResponseDTO;
import org.sergei.reservation.rest.dto.response.ResponseDTO;
import org.springframework.http.ResponseEntity;

/**
 * @author Sergei Visotsky
 */
public interface RouteService {
    ResponseEntity<ResponseDTO<RouteResponseDTO>> findOneRoute(Long routeId);

    ResponseEntity<ResponseDTO<RouteResponseDTO>> findAllRoutes();
}
