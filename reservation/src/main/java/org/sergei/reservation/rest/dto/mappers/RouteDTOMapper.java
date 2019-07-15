package org.sergei.reservation.rest.dto.mappers;

import org.sergei.reservation.jpa.model.Route;
import org.sergei.reservation.rest.dto.RouteDTO;
import org.springframework.stereotype.Component;

/**
 * @author Sergei Visotsky
 */
@Component
public class RouteDTOMapper implements IMapper<Route, RouteDTO> {

    @Override
    public RouteDTO apply(Route route) {
        RouteDTO routeDTO = new RouteDTO();

        routeDTO.setRouteId(route.getId());
        routeDTO.setAircraftId(route.getAircraft().getId());
        routeDTO.setDepartureTime(route.getDepartureTime());
        routeDTO.setArrivalTime(route.getArrivalTime());
        routeDTO.setPlace(route.getPlace());
        routeDTO.setPrice(route.getPrice());
        routeDTO.setDistance(route.getDistance());

        return routeDTO;
    }
}
