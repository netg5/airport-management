package org.sergei.manager.jpa.model.mappers;

import org.sergei.manager.jpa.model.Aircraft;
import org.sergei.manager.rest.dto.AircraftDTO;
import org.sergei.manager.utils.IMapper;
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
        int available;
        if (aircraftDTO.getAvailable()) {
            available = 1;
        } else {
            available = 0;
        }
        return Aircraft.builder()
                .id(aircraftDTO.getAircraftId())
                .registrationNumber(aircraftDTO.getRegistrationNumber())
                .modelNumber(aircraftDTO.getModelNumber())
                .aircraftName(aircraftDTO.getAircraftName())
                .capacity(aircraftDTO.getCapacity())
                .weight(aircraftDTO.getWeight())
                .exploitationPeriod(aircraftDTO.getExploitationPeriod())
                .available(available)
                .hangar(hangarModelMapper.apply(aircraftDTO.getHangar()))
                .manufacturer(manufacturerModelMapper.apply(aircraftDTO.getManufacturer()))
                .build();
    }
}
