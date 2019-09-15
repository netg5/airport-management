package org.sergei.cargo.rest.dto.mappers;

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
public class CargoTransferBookingDTOMapper implements IMapper<CargoTransferBooking, CargoTransferBookingResponseDTO> {

    private final CargoTransferFlightDTOMapper cargoTransferFlightDTOMapper;
    private final CargoDTOMapper cargoDTOMapper;

    @Autowired
    public CargoTransferBookingDTOMapper(CargoTransferFlightDTOMapper cargoTransferFlightDTOMapper,
                                         CargoDTOMapper cargoDTOMapper) {
        this.cargoTransferFlightDTOMapper = cargoTransferFlightDTOMapper;
        this.cargoDTOMapper = cargoDTOMapper;
    }

    @Override
    public CargoTransferBookingResponseDTO apply(CargoTransferBooking cargoTransferBooking) {
        return CargoTransferBookingResponseDTO.builder()
                .arrivalTime(cargoTransferBooking.getArrivalTime())
                .departureTime(cargoTransferBooking.getDepartureTime())
                .dateOfFlying(cargoTransferBooking.getDateOfFlying())
                .hoursFlying(cargoTransferBooking.getHoursFlying())
                .cargo(cargoDTOMapper.apply(cargoTransferBooking.getCargo()))
                .cargoTransferFlight(cargoTransferFlightDTOMapper.apply(cargoTransferBooking.getCargoTransferFlight()))
                .build();
    }
}
