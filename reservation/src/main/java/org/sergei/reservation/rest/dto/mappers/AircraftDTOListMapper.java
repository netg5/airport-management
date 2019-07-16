package org.sergei.reservation.rest.dto.mappers;

import org.sergei.reservation.jpa.model.Aircraft;
import org.sergei.reservation.rest.dto.AircraftDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Sergei Visotsky
 */
@Component
public class AircraftDTOListMapper implements IMapper<List<Aircraft>, List<AircraftDTO>> {

    private final AircraftDTOMapper aircraftDTOMapper;

    @Autowired
    public AircraftDTOListMapper(AircraftDTOMapper aircraftDTOMapper) {
        this.aircraftDTOMapper = aircraftDTOMapper;
    }

    @Override
    public List<AircraftDTO> apply(List<Aircraft> aircrafts) {
        return aircraftDTOMapper.applyList(aircrafts);
    }
}
