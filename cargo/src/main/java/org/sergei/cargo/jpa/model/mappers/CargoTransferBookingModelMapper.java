package org.sergei.cargo.jpa.model.mappers;

import org.sergei.cargo.jpa.model.CargoTransferBooking;
import org.sergei.cargo.rest.dto.CargoTransferBookingDTO;
import org.sergei.cargo.utils.IMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Sergei Visotsky
 */
@Component
public class CargoTransferBookingModelMapper implements IMapper<CargoTransferBookingDTO, CargoTransferBooking> {

    private final CargoTransferFlightModelMapper cargoTransferFlightModelMapper;
    private final CargoModelMapper cargoModelMapper;

    @Autowired
    public CargoTransferBookingModelMapper(CargoTransferFlightModelMapper cargoTransferFlightModelMapper,
                                           CargoModelMapper cargoModelMapper) {
        this.cargoTransferFlightModelMapper = cargoTransferFlightModelMapper;
        this.cargoModelMapper = cargoModelMapper;
    }

    @Override
    public CargoTransferBooking apply(CargoTransferBookingDTO cargoTransferBookingDTO) {
        return CargoTransferBooking.builder()
                .departureTime(cargoTransferBookingDTO.getDepartureTime())
                .arrivalTime(cargoTransferBookingDTO.getArrivalTime())
                .dateOfFlying(cargoTransferBookingDTO.getDateOfFlying())
                .hoursFlying(cargoTransferBookingDTO.getHoursFlying())
                .cargoTransferFlight(
                        cargoTransferFlightModelMapper.apply(cargoTransferBookingDTO.getCargoTransferFlight())
                )
                .cargo(cargoModelMapper.apply(cargoTransferBookingDTO.getCargo()))
                .build();
    }
}
