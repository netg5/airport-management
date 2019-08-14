package org.sergei.processor.jdbc.model.mappers;

import org.sergei.processor.jdbc.model.Pilot;
import org.sergei.processor.rest.dto.PilotDTO;
import org.sergei.processor.utils.IMapper;
import org.springframework.stereotype.Component;

/**
 * @author Sergei Visotsky
 */
@Component
public class PilotModelMapper implements IMapper<PilotDTO, Pilot> {
    @Override
    public Pilot apply(PilotDTO pilotDTO) {
        int available;
        if (pilotDTO.getAvailable()) {
            available = 1;
        } else {
            available = 0;
        }
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
                .available(available)
                .build();
    }
}
