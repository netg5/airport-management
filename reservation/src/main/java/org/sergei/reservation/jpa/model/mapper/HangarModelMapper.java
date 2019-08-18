package org.sergei.reservation.jpa.model.mapper;

import org.sergei.reservation.jpa.model.Hangar;
import org.sergei.reservation.rest.dto.HangarDTO;
import org.sergei.reservation.utils.IMapper;
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
