package org.sergei.processor.rest.dto.mappers;

import org.sergei.processor.jpa.model.Hangar;
import org.sergei.processor.rest.dto.HangarDTO;
import org.sergei.processor.utils.IMapper;
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
