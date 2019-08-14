package org.sergei.processor.jdbc.model.mappers;

import org.sergei.processor.jdbc.model.Route;
import org.sergei.processor.rest.dto.RouteDTO;
import org.sergei.processor.utils.IMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Sergei Visotsky
 */
@Component
public class RouteModelMapper implements IMapper<RouteDTO, Route> {

    private final AircraftModelMapper aircraftModelMapper;

    @Autowired
    public RouteModelMapper(AircraftModelMapper aircraftModelMapper) {
        this.aircraftModelMapper = aircraftModelMapper;
    }

    @Override
    public Route apply(RouteDTO routeDTO) {
        return Route.builder()
                .id(routeDTO.getRouteId())
                .departureTime(routeDTO.getDepartureTime())
                .arrivalTime(routeDTO.getArrivalTime())
                .distance(routeDTO.getDistance())
                .place(routeDTO.getPlace())
                .price(routeDTO.getPrice())
                .aircraft(aircraftModelMapper.apply(routeDTO.getAircraft()))
                .build();
    }
}
