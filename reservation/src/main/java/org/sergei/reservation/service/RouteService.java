package org.sergei.reservation.service;

import org.sergei.reservation.rest.dto.RouteDTO;
import org.sergei.reservation.rest.dto.RouteExtendedDTO;
import org.sergei.reservation.rest.dto.response.ResponseDTO;
import org.springframework.http.ResponseEntity;

import java.util.Map;

/**
 * @author Sergei Visotsky
 */
public interface RouteService {
    ResponseEntity<ResponseDTO<RouteExtendedDTO>> findOneRoute(Long routeId);

    ResponseEntity<ResponseDTO<RouteExtendedDTO>> findAllRoutes();

    ResponseEntity<ResponseDTO<RouteExtendedDTO>> findAllRoutesPaginated(int page, int size);

    ResponseEntity<ResponseDTO<RouteDTO>> save(RouteDTO routeDTO);

    ResponseEntity<ResponseDTO<RouteDTO>> update(Long routeId, RouteDTO routeDTO);

    ResponseEntity<ResponseDTO<RouteDTO>> patch(Long routeId, Map<String, Object> params);

    ResponseEntity<ResponseDTO<RouteDTO>> delete(Long routeId);
}
