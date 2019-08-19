package org.sergei.manager.jpa.model.mappers;

import org.sergei.manager.jpa.model.Flight;
import org.sergei.manager.rest.dto.FlightDTO;
import org.sergei.manager.utils.IMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Sergei Visotsky
 */
@Component
public class FlightModelMapper implements IMapper<FlightDTO, Flight> {

    private final AircraftModelMapper aircraftModelMapper;

    @Autowired
    public FlightModelMapper(AircraftModelMapper aircraftModelMapper) {
        this.aircraftModelMapper = aircraftModelMapper;
    }

    @Override
    public Flight apply(FlightDTO flightDTO) {
        return Flight.builder()
                .id(flightDTO.getFlightId())
                .departureTime(flightDTO.getDepartureTime())
                .arrivalTime(flightDTO.getArrivalTime())
                .distance(flightDTO.getDistance())
                .place(flightDTO.getPlace())
                .price(flightDTO.getPrice())
                .aircraft(aircraftModelMapper.apply(flightDTO.getAircraft()))
                .build();
    }
}
