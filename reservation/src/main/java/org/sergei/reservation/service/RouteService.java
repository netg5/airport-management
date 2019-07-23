package org.sergei.reservation.service;

import org.sergei.reservation.rest.dto.RouteRequestDTO;
import org.sergei.reservation.rest.dto.RouteResponseDTO;
import org.sergei.reservation.rest.dto.RouteUpdateRequestDTO;
import org.sergei.reservation.rest.dto.response.ResponseDTO;
import org.springframework.http.ResponseEntity;

import java.util.Map;

/**
 * @author Sergei Visotsky
 */
public interface RouteService {
    ResponseEntity<ResponseDTO<RouteResponseDTO>> findOneRoute(Long routeId);

    ResponseEntity<ResponseDTO<RouteResponseDTO>> findAllRoutes();

    ResponseEntity<ResponseDTO<RouteResponseDTO>> findAllRoutesPaginated(int page, int size);

    ResponseEntity<ResponseDTO<RouteResponseDTO>> save(RouteRequestDTO request);

    ResponseEntity<ResponseDTO<RouteResponseDTO>> update(RouteUpdateRequestDTO request);

    ResponseEntity<ResponseDTO<RouteResponseDTO>> patch(Long routeId, Map<String, Object> params);

    ResponseEntity<ResponseDTO<RouteResponseDTO>> delete(Long routeId);
}
