package org.sergei.reservation.rest.dto.mappers;

import org.sergei.reservation.jpa.model.Route;
import org.sergei.reservation.rest.dto.RouteResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Sergei Visotsky
 */
@Component
public class RouteListDTOMapper implements IMapper<List<Route>, List<RouteResponseDTO>> {

    private final RouteDTOMapper routeDTOMapper;

    @Autowired
    public RouteListDTOMapper(RouteDTOMapper routeDTOMapper) {
        this.routeDTOMapper = routeDTOMapper;
    }

    @Override
    public List<RouteResponseDTO> apply(List<Route> routes) {
        return routeDTOMapper.applyList(routes);
    }
}
