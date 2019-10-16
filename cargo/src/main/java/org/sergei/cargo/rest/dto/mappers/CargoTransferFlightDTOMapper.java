package org.sergei.cargo.rest.dto.mappers;

import org.sergei.cargo.jpa.model.CargoTransferFlight;
import org.sergei.cargo.rest.dto.CargoTransferFlightDTO;
import org.sergei.cargo.utils.IMapper;
import org.springframework.stereotype.Component;

/**
 * @author Sergei Visotsky
 */
@Component
public class CargoTransferFlightDTOMapper implements IMapper<CargoTransferFlight, CargoTransferFlightDTO> {

    @Override
    public CargoTransferFlightDTO apply(CargoTransferFlight cargoTransferFlight) {
        return CargoTransferFlightDTO.builder()
                .departureTime(cargoTransferFlight.getDepartureTime())
                .arrivalTime(cargoTransferFlight.getArrivalTime())
                .distance(cargoTransferFlight.getDistance())
                .place(cargoTransferFlight.getPlace())
                .price(cargoTransferFlight.getPrice())
                .aircraftId(cargoTransferFlight.getAircraftId())
                .build();
    }
}
