package org.sergei.reports.rest.dto.mappers;

import org.sergei.reports.jpa.model.Flight;
import org.sergei.reports.rest.dto.FlightDTO;
import org.sergei.reports.utils.IMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Sergei Visotsky
 */
@Component
public class FlightDTOMapper implements IMapper<Flight, FlightDTO> {

    private final AircraftDTOMapper aircraftDTOMapper;

    @Autowired
    public FlightDTOMapper(AircraftDTOMapper aircraftDTOMapper) {
        this.aircraftDTOMapper = aircraftDTOMapper;
    }

    @Override
    public FlightDTO apply(Flight flight) {
        return FlightDTO.builder()
                .flightId(flight.getId())
                .departureTime(flight.getDepartureTime())
                .arrivalTime(flight.getArrivalTime())
                .price(flight.getPrice())
                .place(flight.getPlace())
                .distance(flight.getDistance())
                .aircraft(aircraftDTOMapper.apply(flight.getAircraft()))
                .build();
    }
}
