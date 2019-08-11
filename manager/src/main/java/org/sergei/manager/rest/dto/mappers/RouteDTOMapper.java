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
        return RouteDTO.builder()
                .routeId(route.getId())
                .departureTime(route.getDepartureTime())
                .arrivalTime(route.getArrivalTime())
                .price(route.getPrice())
                .place(route.getPlace())
                .distance(route.getDistance())
                .aircraft(aircraftDTOMapper.apply(route.getAircraft()))
                .build();
    }
}
