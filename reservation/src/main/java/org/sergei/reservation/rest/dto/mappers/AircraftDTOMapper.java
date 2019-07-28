package org.sergei.reservation.rest.dto.mappers;

import org.sergei.reservation.jpa.model.Aircraft;
import org.sergei.reservation.rest.dto.AircraftResponseDTO;
import org.springframework.stereotype.Component;

/**
 * @author Sergei Visotsky
 */
@Component
public class AircraftDTOMapper implements IMapper<Aircraft, AircraftResponseDTO> {

    @Override
    public AircraftResponseDTO apply(Aircraft aircraft) {
        AircraftResponseDTO aircraftDTO = new AircraftResponseDTO();

        aircraftDTO.setAircraftId(aircraft.getId());
        aircraftDTO.setManufacturerCode(aircraft.getManufacturerCode());
        aircraftDTO.setRegistrationNumber(aircraft.getRegistrationNumber());
        aircraftDTO.setModelNumber(aircraft.getModelNumber());
        aircraftDTO.setAircraftName(aircraft.getAircraftName());
        aircraftDTO.setCapacity(aircraft.getCapacity());
        aircraftDTO.setWeight(aircraft.getWeight());
        aircraftDTO.setExploitationPeriod(aircraft.getExploitationPeriod());

        return aircraftDTO;
    }
}
