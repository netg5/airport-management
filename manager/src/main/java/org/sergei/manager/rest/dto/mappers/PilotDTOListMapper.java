package org.sergei.manager.rest.dto.mappers;

import org.sergei.manager.jpa.model.Pilot;
import org.sergei.manager.rest.dto.PilotDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Sergei Visotsky
 */
@Component
public class PilotDTOListMapper implements IMapper<List<Pilot>, List<PilotDTO>> {

    private final PilotDTOMapper pilotDTOMapper;

    @Autowired
    public PilotDTOListMapper(PilotDTOMapper pilotDTOMapper) {
        this.pilotDTOMapper = pilotDTOMapper;
    }

    @Override
    public List<PilotDTO> apply(List<Pilot> pilots) {
        return pilotDTOMapper.applyList(pilots);
    }
}
