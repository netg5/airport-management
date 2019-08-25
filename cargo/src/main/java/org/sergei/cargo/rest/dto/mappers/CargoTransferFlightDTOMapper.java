package org.sergei.cargo.rest.dto.mappers;

import org.sergei.cargo.jpa.model.CargoTransferFlight;
import org.sergei.cargo.rest.dto.AircraftDTO;
import org.sergei.cargo.rest.dto.CargoTransferFlightDTO;
import org.sergei.cargo.utils.IMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Sergei Visotsky
 */
@Component
public class CargoTransferFlightDTOMapper implements IMapper<CargoTransferFlight, CargoTransferFlightDTO> {

    private final AircraftDTOMapper aircraftDTOMapper;

    @Autowired
    public CargoTransferFlightDTOMapper(AircraftDTOMapper aircraftDTOMapper) {
        this.aircraftDTOMapper = aircraftDTOMapper;
    }

    @Override
    public CargoTransferFlightDTO apply(CargoTransferFlight cargoTransferFlight) {
        return CargoTransferFlightDTO.builder()
                .departureTime(cargoTransferFlight.getDepartureTime())
                .arrivalTime(cargoTransferFlight.getArrivalTime())
                .distance(cargoTransferFlight.getDistance())
                .place(cargoTransferFlight.getPlace())
                .price(cargoTransferFlight.getPrice())
                .aircraft(aircraftDTOMapper.apply(cargoTransferFlight.getAircraft()))
                .build();
    }
}
