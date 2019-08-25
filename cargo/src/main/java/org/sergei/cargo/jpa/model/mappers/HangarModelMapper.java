package org.sergei.cargo.jpa.model.mappers;

import org.sergei.cargo.jpa.model.Hangar;
import org.sergei.cargo.rest.dto.HangarDTO;
import org.sergei.cargo.utils.IMapper;
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
