package org.sergei.manager.rest.dto.mappers;

import org.sergei.manager.jpa.model.Aircraft;
import org.sergei.manager.rest.dto.AircraftDTO;
import org.springframework.stereotype.Component;

/**
 * @author Sergei Visotsky
 */
@Component
public class AircraftDTOMapper implements IMapper<Aircraft, AircraftDTO> {

    @Override
    public AircraftDTO apply(Aircraft aircraft) {
        return AircraftDTO.builder()
                .aircraftId(aircraft.getId())
                .manufacturerCode(aircraft.getManufacturerCode())
                .registrationNumber(aircraft.getRegistrationNumber())
                .modelNumber(aircraft.getModelNumber())
                .aircraftName(aircraft.getAircraftName())
                .capacity(aircraft.getCapacity())
                .weight(aircraft.getWeight())
                .exploitationPeriod(aircraft.getExploitationPeriod())
                .build();
    }
}
