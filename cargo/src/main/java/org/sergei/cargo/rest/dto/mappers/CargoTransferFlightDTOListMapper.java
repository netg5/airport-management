package org.sergei.cargo.rest.dto.mappers;

import org.sergei.cargo.jpa.model.CargoTransferFlight;
import org.sergei.cargo.rest.dto.CargoTransferFlightDTO;
import org.sergei.cargo.utils.IMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Sergei Visotsky
 */
@Component
public class CargoTransferFlightDTOListMapper implements IMapper<List<CargoTransferFlight>, List<CargoTransferFlightDTO>> {

    private final CargoTransferFlightDTOMapper cargoTransferFlightDTOMapper;

    @Autowired
    public CargoTransferFlightDTOListMapper(CargoTransferFlightDTOMapper cargoTransferFlightDTOMapper) {
        this.cargoTransferFlightDTOMapper = cargoTransferFlightDTOMapper;
    }

    @Override
    public List<CargoTransferFlightDTO> apply(List<CargoTransferFlight> cargoTransferFlights) {
        return cargoTransferFlightDTOMapper.applyList(cargoTransferFlights);
    }
}
