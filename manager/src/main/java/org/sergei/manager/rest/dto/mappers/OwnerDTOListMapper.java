package org.sergei.manager.rest.dto.mappers;

import org.sergei.manager.jpa.model.Owner;
import org.sergei.manager.rest.dto.OwnerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Sergei Visotsky
 */
@Component
public class OwnerDTOListMapper implements IMapper<List<Owner>, List<OwnerDTO>> {

    private final OwnerDTOMapper ownerDTOMapper;

    @Autowired
    public OwnerDTOListMapper(OwnerDTOMapper ownerDTOMapper) {
        this.ownerDTOMapper = ownerDTOMapper;
    }

    @Override
    public List<OwnerDTO> apply(List<Owner> owners) {
        return ownerDTOMapper.applyList(owners);
    }
}
