package org.sergei.reservation.rest.dto.mappers;

import org.sergei.reservation.jpa.model.Route;
import org.sergei.reservation.rest.dto.RouteResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Sergei Visotsky
 */
@Component
public class RouteDTOMapper implements IMapper<Route, RouteResponseDTO> {


    private final AircraftDTOMapper aircraftDTOMapper;

    @Autowired
    public RouteDTOMapper(AircraftDTOMapper aircraftDTOMapper) {
        this.aircraftDTOMapper = aircraftDTOMapper;
    }

    @Override
    public RouteResponseDTO apply(Route route) {
        RouteResponseDTO routeResponseDTO = new RouteResponseDTO();

        routeResponseDTO.setRouteId(route.getId());
        routeResponseDTO.setAircraftDTO(aircraftDTOMapper.apply(route.getAircraft()));
        routeResponseDTO.setDepartureTime(route.getDepartureTime());
        routeResponseDTO.setArrivalTime(route.getArrivalTime());
        routeResponseDTO.setPlace(route.getPlace());
        routeResponseDTO.setPrice(route.getPrice());
        routeResponseDTO.setDistance(route.getDistance());

        return routeResponseDTO;
    }
}
