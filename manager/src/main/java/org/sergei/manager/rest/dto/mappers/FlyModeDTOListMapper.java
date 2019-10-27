package org.sergei.manager.rest.dto.mappers;

import org.sergei.manager.jpa.model.FlyMode;
import org.sergei.manager.rest.dto.FlyModeDTO;
import org.sergei.manager.utils.IMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Sergei Visotsky
 */
@Component
public class FlyModeDTOListMapper implements IMapper<List<FlyMode>, List<FlyModeDTO>> {

    private final FlyModeDTOMapper flyModeDTOMapper;

    @Autowired
    public FlyModeDTOListMapper(FlyModeDTOMapper flyModeDTOMapper) {
        this.flyModeDTOMapper = flyModeDTOMapper;
    }


    @Override
    public List<FlyModeDTO> apply(List<FlyMode> flyModes) {
        return flyModeDTOMapper.applyList(flyModes);
    }
}
