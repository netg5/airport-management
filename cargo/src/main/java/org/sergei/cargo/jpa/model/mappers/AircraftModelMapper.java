package org.sergei.cargo.jpa.model.mappers;

import org.sergei.cargo.jpa.model.Aircraft;
import org.sergei.cargo.rest.dto.AircraftDTO;
import org.sergei.cargo.utils.IMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Sergei Visotsky
 */
@Component
public class AircraftModelMapper implements IMapper<AircraftDTO, Aircraft> {

    private final HangarModelMapper hangarModelMapper;
    private final ManufacturerModelMapper manufacturerModelMapper;

    @Autowired
    public AircraftModelMapper(HangarModelMapper hangarModelMapper,
                               ManufacturerModelMapper manufacturerModelMapper) {
        this.hangarModelMapper = hangarModelMapper;
        this.manufacturerModelMapper = manufacturerModelMapper;
    }

    @Override
    public Aircraft apply(AircraftDTO aircraftDTO) {
        return Aircraft.builder()
                .id(aircraftDTO.getAircraftId())
                .registrationNumber(aircraftDTO.getRegistrationNumber())
                .modelNumber(aircraftDTO.getModelNumber())
                .aircraftName(aircraftDTO.getAircraftName())
                .capacity(aircraftDTO.getCapacity())
                .weight(aircraftDTO.getWeight())
                .exploitationPeriod(aircraftDTO.getExploitationPeriod())
                .hangar(hangarModelMapper.apply(aircraftDTO.getHangar()))
                .manufacturer(manufacturerModelMapper.apply(aircraftDTO.getManufacturer()))
                .build();
    }
}
