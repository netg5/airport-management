package org.sergei.reservation.service;

import org.sergei.reservation.rest.dto.RouteDTO;
import org.sergei.reservation.rest.dto.response.ResponseDTO;
import org.springframework.http.ResponseEntity;

import java.util.Map;

/**
 * @author Sergei Visotsky
 */
public interface RouteService {
    ResponseEntity<ResponseDTO<RouteDTO>> findOneRoute(Long routeId);

    ResponseEntity<ResponseDTO<RouteDTO>> findAllRoutes();

    ResponseEntity<ResponseDTO<RouteDTO>> findAllRoutesPaginated(int page, int size);

    ResponseEntity<ResponseDTO<RouteDTO>> save(RouteDTO routeDTO);

    ResponseEntity<ResponseDTO<RouteDTO>> update(Long routeId, RouteDTO routeDTO);

    ResponseEntity<ResponseDTO<RouteDTO>> patch(Long routeId, Map<String, Object> params);

    ResponseEntity<ResponseDTO<RouteDTO>> delete(Long routeId);
}
