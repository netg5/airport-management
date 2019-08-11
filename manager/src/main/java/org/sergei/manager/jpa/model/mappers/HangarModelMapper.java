package org.sergei.manager.jpa.model.mappers;

import org.sergei.manager.jpa.model.Hangar;
import org.sergei.manager.rest.dto.HangarDTO;
import org.sergei.manager.utils.IMapper;
import org.springframework.stereotype.Component;

/**
 * @author Sergei Visotsky
 */
@Component
public class HangarModelMapper implements IMapper<HangarDTO, Hangar> {
    @Override
    public Hangar apply(HangarDTO hangarDTO) {
        return Hangar.builder()
                .id(hangarDTO.getId())
                .capacity(hangarDTO.getCapacity())
                .hangarNumber(hangarDTO.getHangarNumber())
                .hangarLocation(hangarDTO.getHangarLocation())
                .build();
    }
}
