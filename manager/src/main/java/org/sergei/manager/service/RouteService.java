package org.sergei.manager.service;

import org.sergei.manager.rest.dto.RouteDTO;
import org.sergei.manager.rest.dto.request.RouteRequestDTO;
import org.sergei.manager.rest.dto.response.ResponseDTO;
import org.springframework.http.ResponseEntity;

/**
 * @author Sergei Visotsky
 */
public interface RouteService {
    ResponseEntity<ResponseDTO<RouteDTO>> findOneRoute(Long routeId);

    ResponseEntity<ResponseDTO<RouteDTO>> findAllRoutes();

    ResponseEntity<ResponseDTO<RouteDTO>> findAllRoutesPaginated(int page, int size);

    ResponseEntity<ResponseDTO<RouteDTO>> save(RouteDTO request);

    ResponseEntity<ResponseDTO<RouteDTO>> update(RouteDTO request);

    ResponseEntity<ResponseDTO<RouteDTO>> delete(Long routeId);
}
