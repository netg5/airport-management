package org.sergei.reservation.jpa.model.mapper;

import org.sergei.reservation.jpa.model.Route;
import org.sergei.reservation.rest.dto.RouteDTO;
import org.sergei.reservation.utils.IMapper;
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
    public Route apply(RouteDTO route) {
        return Route.builder()
                .departureTime(route.getDepartureTime())
                .arrivalTime(route.getArrivalTime())
                .distance(route.getDistance())
                .place(route.getPlace())
                .price(route.getPrice())
                .aircraft(aircraftModelMapper.apply(route.getAircraft()))
                .build();
    }
}
