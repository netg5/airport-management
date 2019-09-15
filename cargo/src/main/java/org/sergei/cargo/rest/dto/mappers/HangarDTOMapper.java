package org.sergei.cargo.rest.dto.mappers;

import org.sergei.cargo.jpa.model.Hangar;
import org.sergei.cargo.rest.dto.HangarDTO;
import org.sergei.cargo.utils.IMapper;
import org.springframework.stereotype.Component;

/**
 * @author Sergei Visotsky
 */
@Component
public class HangarDTOMapper implements IMapper<Hangar, HangarDTO> {
    @Override
    public HangarDTO apply(Hangar hangar) {
        return HangarDTO.builder()
                .id(hangar.getId())
                .capacity(hangar.getCapacity())
                .hangarLocation(hangar.getHangarLocation())
                .hangarNumber(hangar.getHangarNumber())
                .build();
    }
}
