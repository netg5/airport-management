package org.sergei.manager.rest.dto.mappers;

import org.sergei.manager.jpa.model.Flight;
import org.sergei.manager.rest.dto.FlightDTO;
import org.sergei.manager.utils.IMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Sergei Visotsky
 */
@Component
public class FlightListDTOMapper implements IMapper<List<Flight>, List<FlightDTO>> {

    private final FlightDTOMapper flightDTOMapper;

    @Autowired
    public FlightListDTOMapper(FlightDTOMapper flightDTOMapper) {
        this.flightDTOMapper = flightDTOMapper;
    }

    @Override
    public List<FlightDTO> apply(List<Flight> flights) {
        return flightDTOMapper.applyList(flights);
    }
}
