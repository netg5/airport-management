package org.sergei.reservation.service;

import org.sergei.reservation.rest.dto.RouteDTO;
import org.sergei.reservation.rest.dto.RouteExtendedDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

/**
 * @author Sergei Visotsky
 */
public interface RouteService {
    ResponseEntity<RouteExtendedDTO> findOneRoute(Long routeId);

    ResponseEntity<List<RouteExtendedDTO>> findAllRoutes();

    ResponseEntity<List<RouteExtendedDTO>> findAllRoutesPaginated(int page, int size);

    ResponseEntity<RouteDTO> save(RouteDTO routeDTO);

    ResponseEntity<RouteDTO> update(Long routeId, RouteDTO routeDTO);

    ResponseEntity<RouteDTO> patch(Long routeId, Map<String, Object> params);

    ResponseEntity<RouteDTO> delete(Long routeId);
}
