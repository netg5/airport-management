package org.sergei.cargo.jpa.model.mappers;

import org.sergei.cargo.jpa.model.CargoTransferBooking;
import org.sergei.cargo.rest.dto.response.CargoTransferBookingResponseDTO;
import org.sergei.cargo.rest.dto.response.FacetFieldsDTO;
import org.sergei.cargo.utils.IMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Sergei Visotsky
 */
@Component
public class CargoTransferBookingModelMapper implements IMapper<CargoTransferBookingResponseDTO, CargoTransferBooking> {

    private final CargoTransferFlightModelMapper cargoTransferFlightModelMapper;
    private final CargoModelMapper cargoModelMapper;

    @Autowired
    public CargoTransferBookingModelMapper(CargoTransferFlightModelMapper cargoTransferFlightModelMapper,
                                           CargoModelMapper cargoModelMapper) {
        this.cargoTransferFlightModelMapper = cargoTransferFlightModelMapper;
        this.cargoModelMapper = cargoModelMapper;
    }

    @Override
    public CargoTransferBooking apply(CargoTransferBookingResponseDTO cargoTransferBookingResponseDTO) {
        return CargoTransferBooking.builder()
                .departureTime(cargoTransferBookingResponseDTO.getDepartureTime())
                .arrivalTime(cargoTransferBookingResponseDTO.getArrivalTime())
                .dateOfFlying(cargoTransferBookingResponseDTO.getDateOfFlying())
                .hoursFlying(cargoTransferBookingResponseDTO.getHoursFlying())
                .cargoTransferFlight(
                        cargoTransferFlightModelMapper.apply(cargoTransferBookingResponseDTO.getCargoTransferFlight())
                )
                .cargo(cargoModelMapper.apply(cargoTransferBookingResponseDTO.getCargo()))
                .build();
    }
}
