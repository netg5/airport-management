package org.sergei.manager.rest.dto.mappers;

import org.sergei.manager.jpa.model.Hangar;
import org.sergei.manager.rest.dto.HangarDTO;
import org.sergei.manager.utils.IMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Sergei Visotsky
 */
@Component
public class HangarDTOListMapper implements IMapper<List<Hangar>, List<HangarDTO>> {

    private final HangarDTOMapper hangarDTOMapper;

    @Autowired
    public HangarDTOListMapper(HangarDTOMapper hangarDTOMapper) {
        this.hangarDTOMapper = hangarDTOMapper;
    }

    @Override
    public List<HangarDTO> apply(List<Hangar> hangars) {
        return hangarDTOMapper.applyList(hangars);
    }
}
