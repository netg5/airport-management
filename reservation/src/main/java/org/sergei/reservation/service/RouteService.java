package org.sergei.reservation.service;

import org.sergei.reservation.rest.dto.RouteDTO;
import org.sergei.reservation.rest.dto.RouteExtendedDTO;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

/**
 * @author Sergei Visotsky
 */
public interface RouteService {
    RouteExtendedDTO findOneRoute(Long routeId);

    List<RouteExtendedDTO> findAllRoutes();

    List<RouteExtendedDTO> findAllRoutesPaginated(int page, int size);

    RouteDTO save(RouteDTO routeDTO);

    RouteDTO update(Long routeId, RouteDTO routeDTO);

    RouteDTO patch(Long routeId, Map<String, Object> params);

    RouteDTO delete(Long routeId);

    RouteDTO findOne(Long aLong);

    List<RouteDTO> findAll();

    Page<RouteDTO> findAllPaginated(int page, int size);
}
