package org.sergei.cargo.jpa.model.mappers;

import org.sergei.cargo.jpa.model.CargoTransferFlight;
import org.sergei.cargo.rest.dto.CargoTransferFlightDTO;
import org.sergei.cargo.utils.IMapper;
import org.springframework.stereotype.Component;

/**
 * @author Sergei Visotsky
 */
@Component
public class CargoTransferFlightModelMapper implements IMapper<CargoTransferFlightDTO, CargoTransferFlight> {

    @Override
    public CargoTransferFlight apply(CargoTransferFlightDTO cargoTransferFlightDTO) {
        return CargoTransferFlight.builder()
                .departureTime(cargoTransferFlightDTO.getDepartureTime())
                .arrivalTime(cargoTransferFlightDTO.getArrivalTime())
                .price(cargoTransferFlightDTO.getPrice())
                .place(cargoTransferFlightDTO.getPlace())
                .distance(cargoTransferFlightDTO.getDistance())
                .aircraftId(cargoTransferFlightDTO.getAircraftId())
                .build();
    }
}
