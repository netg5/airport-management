package org.sergei.manager.rest.dto.mappers;

import org.sergei.manager.jpa.model.Aircraft;
import org.sergei.manager.rest.dto.AircraftDTO;
import org.sergei.manager.utils.IMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Sergei Visotsky
 */
@Component
public class AircraftDTOMapper implements IMapper<Aircraft, AircraftDTO> {

    private final ManufacturerDTOMapper manufacturerDTOMapper;

    @Autowired
    public AircraftDTOMapper(ManufacturerDTOMapper manufacturerDTOMapper) {
        this.manufacturerDTOMapper = manufacturerDTOMapper;
    }

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
                .hangarId(aircraft.getHangar().getId())
                .manufacturer(manufacturerDTOMapper.apply(aircraft.getManufacturer()))
                .build();
    }
}
