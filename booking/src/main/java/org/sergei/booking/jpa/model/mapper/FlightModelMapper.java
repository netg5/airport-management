package org.sergei.booking.jpa.model.mapper;

import org.sergei.booking.jpa.model.Flight;
import org.sergei.booking.rest.dto.FlightDTO;
import org.sergei.booking.utils.IMapper;
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
    public Flight apply(FlightDTO flight) {
        return Flight.builder()
                .departureTime(flight.getDepartureTime())
                .arrivalTime(flight.getArrivalTime())
                .distance(flight.getDistance())
                .place(flight.getPlace())
                .price(flight.getPrice())
                .aircraft(aircraftModelMapper.apply(flight.getAircraft()))
                .build();
    }
}
