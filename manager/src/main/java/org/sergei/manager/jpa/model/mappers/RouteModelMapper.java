package org.sergei.manager.jpa.model.mappers;

import org.sergei.manager.jpa.model.Route;
import org.sergei.manager.rest.dto.RouteDTO;
import org.sergei.manager.utils.IMapper;
import org.springframework.stereotype.Component;

/**
 * @author Sergei Visotsky
 */
@Component
public class RouteModelMapper implements IMapper<RouteDTO, Route> {

    @Override
    public Route apply(RouteDTO routeDTO) {
        return Route.builder()
                .id(routeDTO.getRouteId())
                .departureTime(routeDTO.getDepartureTime())
                .arrivalTime(routeDTO.getArrivalTime())
                .distance(routeDTO.getDistance())
                .place(routeDTO.getPlace())
                .price(routeDTO.getPrice())
                .build();
    }
}
