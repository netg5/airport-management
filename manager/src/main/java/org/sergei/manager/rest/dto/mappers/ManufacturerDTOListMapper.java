package org.sergei.manager.rest.dto.mappers;

import org.sergei.manager.jpa.model.Manufacturer;
import org.sergei.manager.rest.dto.ManufacturerDTO;
import org.sergei.manager.utils.IMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Sergei Visotsky
 */
@Component
public class ManufacturerDTOListMapper implements IMapper<List<Manufacturer>, List<ManufacturerDTO>> {

    private final ManufacturerDTOMapper manufacturerDTOMapper;

    @Autowired
    public ManufacturerDTOListMapper(ManufacturerDTOMapper manufacturerDTOMapper) {
        this.manufacturerDTOMapper = manufacturerDTOMapper;
    }

    @Override
    public List<ManufacturerDTO> apply(List<Manufacturer> manufacturers) {
        return manufacturerDTOMapper.applyList(manufacturers);
    }
}
