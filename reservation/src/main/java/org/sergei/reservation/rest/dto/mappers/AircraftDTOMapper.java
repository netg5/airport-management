package org.sergei.reservation.rest.dto.mappers;

import org.sergei.reservation.jpa.model.Aircraft;
import org.sergei.reservation.rest.dto.AircraftDTO;
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
                .registrationNumber(aircraft.getRegistrationNumber())
                .modelNumber(aircraft.getModelNumber())
                .aircraftName(aircraft.getAircraftName())
                .capacity(aircraft.getCapacity())
                .weight(aircraft.getWeight())
                .exploitationPeriod(aircraft.getExploitationPeriod())
                .build();
    }
}
