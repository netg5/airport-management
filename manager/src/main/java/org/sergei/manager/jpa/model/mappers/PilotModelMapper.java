package org.sergei.manager.jpa.model.mappers;

import org.sergei.manager.jpa.model.Pilot;
import org.sergei.manager.rest.dto.PilotDTO;
import org.sergei.manager.utils.IMapper;
import org.springframework.stereotype.Component;

/**
 * @author Sergei Visotsky
 */
@Component
public class PilotModelMapper implements IMapper<PilotDTO, Pilot> {
    @Override
    public Pilot apply(PilotDTO pilotDTO) {
        return Pilot.builder()
                .licenseNumber(pilotDTO.getLicenseNumber())
                .ssn(pilotDTO.getSsn())
                .firstName(pilotDTO.getFirstName())
                .lastName(pilotDTO.getLastName())
                .gender(pilotDTO.getGender())
                .weight(pilotDTO.getWeight())
                .dateOfBirth(pilotDTO.getDateOfBirth())
                .address(pilotDTO.getAddress())
                .country(pilotDTO.getCountry())
                .email(pilotDTO.getEmail())
                .phone(pilotDTO.getPhone())
                .build();
    }
}
