package org.sergei.manager.rest.dto.mappers;

import org.sergei.manager.jpa.model.Route;
import org.sergei.manager.rest.dto.RouteDTO;
import org.sergei.manager.utils.IMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Sergei Visotsky
 */
@Component
public class RouteDTOMapper implements IMapper<Route, RouteDTO> {


    private final AircraftDTOMapper aircraftDTOMapper;

    @Autowired
    public RouteDTOMapper(AircraftDTOMapper aircraftDTOMapper) {
        this.aircraftDTOMapper = aircraftDTOMapper;
    }

    @Override
    public RouteDTO apply(Route route) {
        RouteDTO routeDTO = new RouteDTO();

        routeDTO.setRouteId(route.getId());
        routeDTO.setAircraftDTO(aircraftDTOMapper.apply(route.getAircraft()));
        routeDTO.setDepartureTime(route.getDepartureTime());
        routeDTO.setArrivalTime(route.getArrivalTime());
        routeDTO.setPlace(route.getPlace());
        routeDTO.setPrice(route.getPrice());
        routeDTO.setDistance(route.getDistance());

        return routeDTO;
    }
}
