package org.sergei.processor.rest.dto.mappers;

import org.sergei.processor.jdbc.model.Pilot;
import org.sergei.processor.rest.dto.PilotDTO;
import org.sergei.processor.utils.IMapper;
import org.springframework.stereotype.Component;

/**
 * @author Sergei Visotsky
 */
@Component
public class PilotDTOMapper implements IMapper<Pilot, PilotDTO> {

    @Override
    public PilotDTO apply(Pilot pilot) {
        boolean available = pilot.getAvailable() == 1;
        return PilotDTO.builder()
                .id(pilot.getId())
                .licenseNumber(pilot.getLicenseNumber())
                .ssn(pilot.getSsn())
                .firstName(pilot.getFirstName())
                .lastName(pilot.getLastName())
                .gender(pilot.getGender())
                .weight(pilot.getWeight())
                .dateOfBirth(pilot.getDateOfBirth())
                .address(pilot.getAddress())
                .country(pilot.getCountry())
                .email(pilot.getEmail())
                .phone(pilot.getPhone())
                .available(available)
                .build();
    }
}
