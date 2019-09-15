package org.sergei.cargo.rest.dto.mappers;

import org.sergei.cargo.jpa.model.Aircraft;
import org.sergei.cargo.rest.dto.AircraftDTO;
import org.sergei.cargo.utils.IMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Sergei Visotsky
 */
@Component
public class AircraftDTOMapper implements IMapper<Aircraft, AircraftDTO> {

    private final HangarDTOMapper hangarDTOMapper;
    private final ManufacturerDTOMapper manufacturerDTOMapper;

    @Autowired
    public AircraftDTOMapper(HangarDTOMapper hangarDTOMapper,
                             ManufacturerDTOMapper manufacturerDTOMapper) {
        this.hangarDTOMapper = hangarDTOMapper;
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
                .hangar(hangarDTOMapper.apply(aircraft.getHangar()))
                .manufacturer(manufacturerDTOMapper.apply(aircraft.getManufacturer()))
                .build();
    }
}
