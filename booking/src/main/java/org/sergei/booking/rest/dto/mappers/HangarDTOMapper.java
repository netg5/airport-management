package org.sergei.booking.rest.dto.mappers;

import org.sergei.booking.jpa.model.Hangar;
import org.sergei.booking.rest.dto.HangarDTO;
import org.sergei.booking.utils.IMapper;
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
