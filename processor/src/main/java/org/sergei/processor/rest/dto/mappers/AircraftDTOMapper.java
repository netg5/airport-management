package org.sergei.processor.rest.dto.mappers;

import org.sergei.processor.jpa.model.Aircraft;
import org.sergei.processor.rest.dto.AircraftDTO;
import org.sergei.processor.utils.IMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Sergei Visotsky
 */
@Component
public class AircraftDTOMapper implements IMapper<Aircraft, AircraftDTO> {

    private final ManufacturerDTOMapper manufacturerDTOMapper;
    private final HangarDTOMapper hangarDTOMapper;

    @Autowired
    public AircraftDTOMapper(ManufacturerDTOMapper manufacturerDTOMapper,
                             HangarDTOMapper hangarDTOMapper) {
        this.manufacturerDTOMapper = manufacturerDTOMapper;
        this.hangarDTOMapper = hangarDTOMapper;
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
